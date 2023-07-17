package com.dyys.hr.service.train.impl;

import cn.hutool.core.date.DateTime;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.train.TrainExaminerMapper;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.entity.exam.ExamBack;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.OfflineExamResultsExcel;
import com.dyys.hr.entity.train.excel.TrainExamResultImportExcel;
import com.dyys.hr.service.exam.IExamBackService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainExaminerDetailService;
import com.dyys.hr.service.train.TrainExaminerService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.exam.ExamCenterVO;
import com.dyys.hr.vo.train.TrainExamResultImportExcelVO;
import com.dyys.hr.vo.train.TrainExaminerVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class TrainExaminerServiceImpl extends AbstractCrudService<TrainExaminer, Long> implements TrainExaminerService {
    @Autowired
    private TrainExaminerMapper trainExaminerMapper;
    @Autowired
    private TrainExaminerDetailService trainExaminerDetailService;
    @Autowired
    private IExamBackService iExamBackService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainExamService trainExamService;
    @Value("${portal-config.domain}")
    private String jumpDomain;

    @Override
    public List<TrainExaminerVO> getListByQuery(Map<String, Object> params) {
        return trainExaminerMapper.getListByQuery(params);
    }

    @Override
    public void deleteByParams(Map<String, Object> params) {
        trainExaminerMapper.deleteByParams(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer restExam(Map<String,Object> params) {
        TrainExaminer trainExaminer = this.selectById(Long.valueOf(params.get("examinerId").toString()));
        if(trainExaminer == null){
            throw new BusinessException(ResultCode.EXCEPTION,"操作记录不存在");
        }
        //重置项目考试参考人员表数据
        Map<String, Object> query = new HashMap<>();
        query.put("examinerId", params.get("examinerId"));
        query.put("update_user", params.get("userId"));
        query.put("update_time", System.currentTimeMillis() / 1000);
        trainExaminerMapper.restExam(query);

        //清除考试明细记录及答案表
        trainExaminerDetailService.restExamDeleteByQuery(query);

        //插入回退原因记录
        ExamBack examBack = new ExamBack();
        examBack.setUserId(trainExaminer.getUserId());
        examBack.setExamId(trainExaminer.getExamId());
        examBack.setReason(params.get("reason").toString());
        examBack.setCreator(params.get("userId").toString());
        examBack.setCreateTime(new Date());
        iExamBackService.insertSelective(examBack);

        //重发代办
        TrainNotice trainNotice = new TrainNotice();
        trainNotice.setTypeId(trainExaminer.getExamId());
        trainNotice.setUserId(trainExaminer.getUserId());
        trainNotice.setType(2);
        trainNotice.setStatus(0);
        trainNotice.setCreateUser(params.get("userId").toString());
        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
        trainNoticeService.insertSelective(trainNotice);
        return 1;
    }

    /**
     * 分页获取考试中心列表
     *
     * @return
     */
    @Override
    public PageView<ExamCenterVO> pageExamCenter(Map<String, Object> params) {
        String status = (String) params.get("status");
        Page page = PageHelper.startPage(Integer.valueOf(params.get(Constant.PAGE).toString()), Integer.valueOf(params.get(Constant.LIMIT).toString()));
        try {
            if (status.equals("0")) {
                trainExaminerMapper.listExamCenter(params);
            } else {
                trainExaminerMapper.listExamCommit(params);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            page.close();
        }

        return PageView.build(page);
    }

    /**
     * 根据考试id和用户id得到考试人员主键
     *
     * @param examId 考试id
     * @param userId 用户id
     * @return 考试人员表主键
     */
    @Override
    public TrainExaminer getExaminer(String examId, String userId) {
        TrainExaminer trainExaminer = new TrainExaminer();
        trainExaminer.setExamId(Long.valueOf(examId));
        trainExaminer.setUserId(userId);

        return selectOne(trainExaminer);
    }

    /**
     * 获取所有参考人员id
     * @param examId
     * @return
     */
    @Override
    public List<String> allExamUserIds(Long examId){
        return trainExaminerMapper.allExamUserIds(examId);
    }

    /**
     * 导入成绩模板-获取某个考试的参考人员
     * @param examId
     * @return
     */
    @Override
    public List<OfflineExamResultsExcel> getExaminerListTml(Long examId) {
        return trainExaminerMapper.getExaminerListTml(examId);
    }

    /**
     * 导入线下考试成绩
     * @param excelList
     * @param examId
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OfflineExamResultsExcel> importExl(List<OfflineExamResultsExcel> excelList, Long examId, String loginEmplId) {
        List<String> examUserIds = this.allExamUserIds(examId); // 获取该考试参考人员工号集
        Long stamp = System.currentTimeMillis() / 1000;
        List<OfflineExamResultsExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        List<TrainExaminer> examinerList = new ArrayList<>();
        int i = 0;
        int errNum = 0;
        for (OfflineExamResultsExcel excel : excelList) {
            i++;
            OfflineExamResultsExcel errVO = new OfflineExamResultsExcel();
            // 判断工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参考人工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断工号是否为参考人员
            if (!examUserIds.contains(excel.getEmplId())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，该参考人工号不是本次考试参考人员");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断最高分是否为空
            if (excel.getHighestScore() == null || excel.getHighestScore().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，最高分为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断是否选择通过标识
            if (excel.getStatus() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择是否通过标识");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断通过标识是否正确
            if (!excel.getStatus().equals(1) && !excel.getStatus().equals(0)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，是否通过标识不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断是否有重复数据
            String mapKey = excel.getEmplId();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的参考人工号重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 处理数据
            if (errNum == 0) {
                TrainExaminer examiner = new TrainExaminer();
                examiner.setExamId(examId);
                examiner.setUserId(excel.getEmplId());
                TrainExaminer selectOne = this.selectOne(examiner);
                examiner.setId(selectOne.getId());
                examiner.setStatus(excel.getStatus());
                examiner.setHighestScore(excel.getHighestScore());
                examiner.setUpdateUser(loginEmplId);
                examiner.setUpdateTime(stamp);
                examinerList.add(examiner);
            }
        }
        // 处理数据入库
        if (errNum == 0) {
            for (TrainExaminer entity : examinerList) {
                updateSelective(entity);
            }
        }
        return errorList;
    }

    /**
     * 取员工在某个培训项目某个课程的最后一次考试成绩
     * @param userId
     * @param programsId
     * @param courseId
     * @return
     */
    @Override
    public TrainExaminer getFinalExamResults(String userId, Long programsId, Long courseId) {
        return trainExaminerMapper.getFinalExamResults(userId, programsId, courseId);
    }


    /**
     * 批量通知考试
     * @param dtoList
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchExamNotice(List<IdDTO> dtoList, String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            //获取登陆人和考试名称
            String loginUserName = iStaffUserInfoService.getUserInfoById(loginUserId).getEmplName();

            for (IdDTO dto : dtoList){
                //获取所有参考人员
                List<String> allExamUserIds = this.allExamUserIds(dto.getId());
                String examTitle = trainExamService.selectById(dto.getId()).getTitle();
                for (String userId : allExamUserIds){
                    //自助平台插入代办
                    trainNoticeService.insertHcmPortalMessage("培训系统","线上考试",userId, 1,jumpDomain + "/kn-front/emp/center", DateTime.now(), examTitle + "线上考试",
                            0,loginUserId,loginUserName);
                }
            }
            result = true;
        }
        return result;
    }


    /**
     * 导入数据处理
     * @param excelList
     * @param examId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrainExamResultImportExcelVO handleResultImportExcel(List<TrainExamResultImportExcel> excelList, Long examId, String loginUserId) throws ParseException {
        List<TrainExamResultImportExcel> errorList = new ArrayList<>();
        List<TrainExaminerVO> dataList = new ArrayList<>();
        int i = 0;
        int errNum = 0;
        for (TrainExamResultImportExcel excel : excelList) {
            i++;
            TrainExamResultImportExcel errVO = new TrainExamResultImportExcel();
            // 判断参考人工号是否为空
            if (excel.getUserId() == null || excel.getUserId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参考人工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getUserId());
            // 判断参考人工号是否正确
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参考人工号不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断参考人工号与姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getUserName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参考人工号与参考人姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            TrainExaminer userQuery = new TrainExaminer();
            userQuery.setExamId(examId);
            userQuery.setUserId(excel.getUserId());
            TrainExaminer selectOne = this.selectOne(userQuery);
            // 判断考试参考人是否存在
            if (selectOne == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，当前考试不存在此参考人");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考试分数是否为空
            if (excel.getScore() == null || excel.getScore().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考试分数不能为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            if (excel.getExamTime() == null || excel.getExamTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考试时间不能为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考试时间是否为空
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                sdf.parse(excel.getExamTime());
            } catch (ParseException e) {
                e.printStackTrace();
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考试时间格式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }

            // 判断考试耗时是否为空
            if (excel.getUseTime() == null || excel.getUseTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考试耗时不能为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }

            if (errNum == 0) {
                TrainExaminerVO vo = new TrainExaminerVO();
                BeanUtils.copyProperties(excel,vo);
                vo.setUserId(userInfo.getEmplId());
                vo.setUserName(userInfo.getEmplName());
                vo.setCompanyCode(userInfo.getCompanyCode());
                vo.setCompanyName(userInfo.getCompanyName());
                vo.setDepartmentCode(userInfo.getDepartmentCode());
                vo.setDepartmentName(userInfo.getDepartmentName());
                vo.setJobCode(userInfo.getJobCode());
                vo.setJobName(userInfo.getJobName());
                dataList.add(vo);
            }
        }
        //判断导入数据正常则批量处理入库
        if(errNum == 0){
            TrainExam trainExam = trainExamService.selectById(examId);
            for (TrainExamResultImportExcel excel : excelList){
                TrainExaminer userQuery = new TrainExaminer();
                userQuery.setUserId(excel.getUserId());
                userQuery.setExamId(examId);
                TrainExaminer userOne = this.selectOne(userQuery);
                //更新参考人员表
                TrainExaminer userUpdate = new TrainExaminer();
                userUpdate.setId(userOne.getId());
                //处理考试状态
                if(userOne.getStatus() != 1){
                    userUpdate.setStatus(0);
                    if(Float.parseFloat(excel.getScore()) > Float.parseFloat(trainExam.getPassScore())){
                        userUpdate.setStatus(1);
                    }
                }
                //判断最高分
                if(userOne.getHighestScore() == null ||(Float.parseFloat(excel.getScore()) > Float.parseFloat(userOne.getHighestScore())))
                {
                    userUpdate.setHighestScore(excel.getScore());
                }

                //考试次数累加
                userUpdate.setExamNum(userOne.getExamNum() + 1);
                //最后考试时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                userUpdate.setFinalTime(sdf.parse(excel.getExamTime()));
                userUpdate.setUpdateUser(loginUserId);
                userUpdate.setUpdateTime(System.currentTimeMillis() / 1000);
                this.updateSelective(userUpdate);
                //考试详情插入记录
                TrainExaminerDetail insertDetail = new TrainExaminerDetail();
                insertDetail.setExaminerId(userOne.getId());
                insertDetail.setExamTime(sdf.parse(excel.getExamTime()));
                insertDetail.setUseTime(excel.getUseTime());
                insertDetail.setScore(excel.getScore());
                int isPass = 0;
                if(Float.parseFloat(excel.getScore()) > Float.parseFloat(trainExam.getPassScore())){
                    isPass = 1;
                }
                insertDetail.setIsPass(isPass);
                insertDetail.setStatus(1);
                insertDetail.setCreateUser(loginUserId);
                insertDetail.setCreateTime(System.currentTimeMillis() / 1000);
                trainExaminerDetailService.insertSelective(insertDetail);
            }
        }

        TrainExamResultImportExcelVO excelVO = new TrainExamResultImportExcelVO();
        excelVO.setErrorList(errorList);
        excelVO.setDataList(dataList);
        return excelVO;
    }

}