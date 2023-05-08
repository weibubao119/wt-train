package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;

import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffJobMapper;
import com.dyys.hr.dao.train.TrainDemandFeedbackDetailMapper;
import com.dyys.hr.dao.train.TrainDemandFeedbackMapper;
import com.dyys.hr.dto.train.TrainDemandFeedbackDetailDTO;
import com.dyys.hr.dto.train.TrainDemandRelateDTO;
import com.dyys.hr.entity.train.TrainBaseTeacher;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.TrainDemandFeedback;
import com.dyys.hr.entity.train.TrainDemandFeedbackDetail;
import com.dyys.hr.entity.train.excel.DemandFeedbackExcel;
import com.dyys.hr.entity.train.excel.DemandFeedbackImportExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TrainDemandFeedbackDetailServiceImpl extends AbstractCrudService<TrainDemandFeedbackDetail, Long> implements TrainDemandFeedbackDetailService {
    @Autowired
    private TrainDemandFeedbackDetailMapper trainDemandFeedbackDetailMapper;
    @Autowired
    private TrainDemandFeedbackMapper trainDemandFeedbackMapper;
    @Autowired
    private TrainDemandFeedbackService trainDemandFeedbackService;
    @Autowired
    private TrainDemandCollectService trainDemandCollectService;
    @Autowired
    private StaffJobMapper staffJobMapper;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    /**
     * 需求汇总页面数据列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<TrainDemandFeedbackDetailVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainDemandFeedbackDetailVO> voList = trainDemandFeedbackDetailMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainDemandFeedbackDetailDTO dto,String loginUserId){
        //校验反馈时间是否符合
//        TrainDemandCollect demandCollectEntity = trainDemandCollectService.selectById(dto.getDemandId());
//        if(demandCollectEntity.getFeedbackStartTime().after(today)){
//            throw new BusinessException(ResultCode.EXCEPTION,"反馈时间未开始!");
//        }
        Date today = new Date();

        //校验反馈名称不重复
        TrainDemandFeedbackDetail query = new TrainDemandFeedbackDetail();
        query.setTrainName(dto.getTrainName());
        query.setDemandId(dto.getDemandId());
        TrainDemandFeedbackDetail selectOne = this.selectOne(query);
        if(selectOne != null){
            throw new BusinessException(ResultCode.EXCEPTION,"培训名称不能重复，请检查");
        }
        //校验拟完成日期大于当日
        if(dto.getTrainCompleteTime().before(today)){
            throw new BusinessException(ResultCode.EXCEPTION,"拟完成日期必须大于今日");
        }

        TrainDemandFeedbackDetail entity = new TrainDemandFeedbackDetail();
        BeanUtils.copyProperties(dto,entity);
        entity.setCreateTime(System.currentTimeMillis()/1000);
        entity.setCreateUser(loginUserId);
        Long res = this.insertSelective(entity);
        //判断是否存在下发反馈人记录,无则增,有则更新反馈人状态
        Map<String, Object> params = new HashMap<>();
        params.put("demand_id",dto.getDemandId());
        params.put("feedback_user_id",dto.getFeedbackUserId());
        TrainDemandFeedbackVO one = trainDemandFeedbackMapper.findOneByQuery(params);
        if(one == null){
            TrainDemandFeedback fEntity = new TrainDemandFeedback();
            fEntity.setDemandId(dto.getDemandId());
            fEntity.setFeedbackUserId(dto.getFeedbackUserId());
            fEntity.setStatus(1);
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(dto.getFeedbackUserId());
            fEntity.setCompanyCode(userInfo.getCompanyCode());
            fEntity.setDepartmentCode(userInfo.getDepartmentCode());
            fEntity.setCreateTime(System.currentTimeMillis()/1000);
            fEntity.setCreateUser(loginUserId);
            trainDemandFeedbackService.insertSelective(fEntity);
        }
        else {
            TrainDemandFeedback fEntity = new TrainDemandFeedback();
            fEntity.setId(one.getId());
            fEntity.setStatus(1);
            fEntity.setUpdateTime(System.currentTimeMillis()/1000);
            fEntity.setUpdateUser(loginUserId);
            trainDemandFeedbackService.updateSelective(fEntity);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean feedbackUserSave(List<TrainDemandFeedbackDetailDTO> dtoList,String loginUserId){
        boolean res = false;
        if(dtoList != null && !dtoList.isEmpty()){
//            //校验反馈时间是否符合
//            TrainDemandCollect demandCollectEntity = trainDemandCollectService.selectById(dtoList.get(0).getDemandId());
//            if(demandCollectEntity.getFeedbackStartTime().after(today)){
//                throw new BusinessException(ResultCode.EXCEPTION,"反馈时间未开始!");
//            }

            Date today = new Date();
            //清除反馈人旧反馈数据
            Map<String,Object> param = new HashMap<>();
            param.put("demand_id",dtoList.get(0).getDemandId());
            param.put("feedback_user_id",loginUserId);
            trainDemandFeedbackDetailMapper.deleteByQuery(param);
            for (TrainDemandFeedbackDetailDTO dto : dtoList){
                //校验反馈名称不重复
//                TrainDemandFeedbackDetail selectOne = this.getInfoByTrainName(dto.getDemandId(), dto.getTrainName());
//                if(selectOne != null){
//                    throw new BusinessException(ResultCode.EXCEPTION,"培训名称为:" + dto.getTrainName() +"的存在重复名称数据，请检查");
//                }
                //校验拟完成日期大于当日
                if (dto.getTrainCompleteTime() == null) {
                    throw new BusinessException(ResultCode.EXCEPTION,"培训名称为:" + dto.getTrainName() +"的拟完成日期不能为空");
                }
                if(dto.getTrainCompleteTime().before(today)){
                    throw new BusinessException(ResultCode.EXCEPTION,"培训名称为:" + dto.getTrainName() +"的拟完成日期小于今日");
                }
                TrainDemandFeedbackDetail entity = new TrainDemandFeedbackDetail();
                BeanUtils.copyProperties(dto,entity);
                entity.setCreateTime(System.currentTimeMillis()/1000);
                entity.setCreateUser(loginUserId);
                this.insertSelective(entity);
            }
            trainDemandFeedbackMapper.finishFeedBackByQuery(param);
            res = true;
        }
        return res;
    }

    @Override
    public TrainDemandFeedbackDetailVO selectByDetailId(Long detailId){
        return trainDemandFeedbackDetailMapper.selectByDetailId(detailId);
    }

    @Override
    public Integer update(TrainDemandFeedbackDetailDTO dto,String loginUserId){
        TrainDemandFeedbackDetail entity = new TrainDemandFeedbackDetail();
        BeanUtils.copyProperties(dto,entity);
        entity.setUpdateTime(System.currentTimeMillis()/1000);
        entity.setCreateUser(loginUserId);
        return this.updateSelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByDetailId(Long detailId){
        TrainDemandFeedbackDetail entity = this.selectById(detailId);
        Integer res = trainDemandFeedbackDetailMapper.deleteByDetailId(detailId);

        //检查反馈所属用户是否存在其他反馈，没有则删除反馈人员信息
        Map<String,Object> params = new HashMap<>();
        params.put("demand_id",entity.getDemandId());
        params.put("feedback_user_id",entity.getFeedbackUserId());
        List<TrainDemandFeedbackDetail> list =  trainDemandFeedbackDetailMapper.selectByQuery(params);
        if(list.isEmpty()){
            trainDemandFeedbackService.deleteFeedBackByQuery(params);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean relateDemand(List<TrainDemandRelateDTO> dtoList, String loginUserId){
        //循环插入子需求下的反馈详情
        boolean res = false;
        if(dtoList != null && !dtoList.isEmpty()){
            for (TrainDemandRelateDTO dto :dtoList){
                Long relateDemandId = dto.getRelateDemandId();
                Map<String,Object> param = new HashMap<>();
                param.put("demand_id",relateDemandId);
                List<TrainDemandFeedbackDetail> details = trainDemandFeedbackDetailMapper.selectByQuery(param);
                if(details != null && !details.isEmpty()){
                    //copy子需求反馈详情
                    Long demandId = dto.getDemandId();
                    HashSet<String> feedBackUserIds = new HashSet<>();
                    List<TrainDemandFeedbackDetail> detailList = new ArrayList<>();
                    for (TrainDemandFeedbackDetail detail : details){
                        detail.setId(null);
                        detail.setDemandId(demandId);
                        detail.setFeedbackUserId(loginUserId);
                        detail.setCreateUser(loginUserId);
                        detail.setCreateTime(System.currentTimeMillis()/1000);
                        detail.setUpdateUser(null);
                        detail.setUpdateTime(null);
                        detailList.add(detail);
                        feedBackUserIds.add(detail.getFeedbackUserId());
                    }
                    res = this.insertBatchSelective(detailList);

                    //查找反馈人记录,不存在增,或修改反馈状态
                    Map<String,Object> query = new HashMap<>();
                    query.put("demand_id",demandId);
                    query.put("feedback_user_id",loginUserId);
                    TrainDemandFeedbackVO user = trainDemandFeedbackMapper.findOneByQuery(query);
                    if(user == null){
                        Map<String,Object> map = new HashMap<>();
                        map.put("emplId",loginUserId);
                        EmplInfoVO emplInfoVO = staffJobMapper.emplInfo(map);
                        TrainDemandFeedback feedback = new TrainDemandFeedback();
                        feedback.setDemandId(demandId);
                        feedback.setFeedbackUserId(loginUserId);
                        feedback.setStatus(1);
                        feedback.setCompanyCode(emplInfoVO.getCompany());
                        feedback.setDepartmentCode(emplInfoVO.getDeptId());
                        feedback.setCreateTime(System.currentTimeMillis()/1000);
                        feedback.setCreateUser(loginUserId);
                    }
                    else{
                        if(user.getStatus() == 0){
                            TrainDemandFeedback feedback = new TrainDemandFeedback();
                            feedback.setId(user.getId());
                            feedback.setStatus(1);
                            feedback.setUpdateTime(System.currentTimeMillis()/1000);
                            feedback.setUpdateUser(loginUserId);
                            trainDemandFeedbackService.updateSelective(feedback);
                        }
                    }


//                    //copy子需求反馈人员数据
//                    List<TrainDemandFeedback> feedbackList = new ArrayList<>();
//                    for (String feedBackUserId: feedBackUserIds){
//                        Map<String,Object> query = new HashMap<>();
//                        query.put("demand_id",demandId);
//                        query.put("feedback_user_id",feedBackUserId);
//                        TrainDemandFeedbackVO one = trainDemandFeedbackMapper.findOneByQuery(query);
//                        if(one == null){
//                            PsPersionVO info = psPersionMapper.getUserInfoById(feedBackUserId);
//                            TrainDemandFeedback feedback = new TrainDemandFeedback();
//                            feedback.setDemandId(demandId);
//                            feedback.setFeedbackUserId(feedBackUserId);
//                            feedback.setStatus(1);
//                            feedback.setCompanyCode(info.getCompanyCode());
//                            feedback.setDepartmentCode(info.getDepartmentCode());
//                            feedback.setCreateTime(System.currentTimeMillis()/1000);
//                            feedback.setCreateUser(loginUserId);
//                            feedbackList.add(feedback);
//                        }
//                    }
//                    if(!feedbackList.isEmpty()){
//                        res = trainDemandFeedbackService.insertBatchSelective(feedbackList);
//                    }
                }
            }
        }
        return res;
    }

    @Override
    public List<TrainDemandFeedbackDetailVO> getRelateDemandDetailList(Map<String, Object> params){
        return trainDemandFeedbackDetailMapper.pageList(params);
    }

    /**
     * 需求汇总-反馈列表导出
     * @param demandId
     * @return
     */
    @Override
    public  List<DemandFeedbackExcel> exportList(String demandId){
        return trainDemandFeedbackDetailMapper.exportList(demandId);
    }

    /**
     * excel模板中下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 培训方式下拉项
        List<String> trainShapeList = new ArrayList<>();
        trainShapeList.add("1-内部培训");
        trainShapeList.add("2-外部培训");
        selectMap.put(3, trainShapeList);

        // 课程类别下拉项
        List<String> courseTypeList = trainConstantService.selectBoxList(1, "0");
        selectMap.put(4, courseTypeList);

        // 培训需求依据下拉项
        List<String> trainRequirementsList = trainConstantService.selectBoxList(2, "0");
        selectMap.put(7, trainRequirementsList);

        // 考核方法下拉项
        List<String> assessmentMethodList = trainConstantService.selectBoxList(3, "0");
        selectMap.put(11, assessmentMethodList);

        return selectMap;
    }

    /**
     * 校验单人反馈批量导入数据
     * @param excelList
     * @param demandId
     * @param loginEmplId
     * @return
     */
    @Override
    public TrainDemandFeedbackDetailExcelVO checkOwnImportDetail(List<DemandFeedbackImportExcel> excelList, Long demandId, String loginEmplId) {
        PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(loginEmplId);
        List<DemandFeedbackImportExcel> errorList = new ArrayList<>();
        List<TrainDemandFeedbackDetailVO> dataList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        Date now = new Date();
        for (DemandFeedbackImportExcel excel : excelList) {
            i++;
            DemandFeedbackImportExcel errVO = new DemandFeedbackImportExcel();
            // 判断反馈人工号是否为空
            if (excel.getFeedbackUserId() == null || excel.getFeedbackUserId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断反馈人是否与当前登录用户一致
            if (!excel.getFeedbackUserId().equals(loginEmplId)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号与当前登录用户不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否为空
            if (excel.getTrainName() == null || excel.getTrainName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否为空
            if (excel.getTrainShapeName() == null || excel.getTrainShapeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择培训形式");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否正确
            String[] trainShapeName = excel.getTrainShapeName().split("-"); // 分出培训形式编号和名称
            int trainShape = Integer.parseInt(trainShapeName[0]);
            if (trainShape != 1 && trainShape != 2) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选培训形式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否为空
            if (excel.getCourseTypeName() == null || excel.getCourseTypeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择课程类别");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否正确
            String[] courseTypeName = excel.getCourseTypeName().split("-"); // 分出课程类别ID和名称
            int courseType = Integer.parseInt(courseTypeName[0]);
            TrainConstant courseTypeInfo = trainConstantService.getInfoBySblId(courseType, 1, "0");
            if (courseTypeInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选课程类别不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断受训对象是否为空
            if (excel.getTrainSubject() == null || excel.getTrainSubject().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，受训对象为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间是否为空
            if (excel.getTrainCompleteTime() == null || excel.getTrainCompleteTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间要不能小于今天
            Date completeTime;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                Date dateTmp = sdf.parse(excel.getTrainCompleteTime());
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(dateTmp);
                completeTime = java.sql.Date.valueOf(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，拟完成时间格式不正确");
            }
            if (completeTime.before(now)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间小于今天日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训需求依据是否为空
            if (excel.getTrainRequirementsName() == null || excel.getTrainRequirementsName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择培训需求依据");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训需求依据是否正确
            String[] requirementsName = excel.getTrainRequirementsName().split("-"); // 分出培训需求依据ID和名称
            int trainRequirements = Integer.parseInt(requirementsName[0]);
            TrainConstant requirementsInfo = trainConstantService.getInfoBySblId(trainRequirements, 2, "0");
            if (requirementsInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选培训需求依据不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断参训人数是否为空
            if (excel.getParticipantsNum() == null || excel.getParticipantsNum() <= 0) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参训人数必须大于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断讲师编号是否正确
            Long teacherId = null;
            String teacherName = null;
            if (excel.getTeacherNum() != null && !excel.getTeacherNum().trim().equals("")) {
                TrainBaseTeacher teacherInfo = trainBaseTeacherService.queryInfoByNumber(excel.getTeacherNum());
                if (teacherInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，培训讲师编号不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                teacherId = teacherInfo.getId();
                teacherName = teacherInfo.getName();
            }
            // 判断考核方法是否正确
            Integer assessmentMethod = null;
            String assessmentMethodName = null;
            if (excel.getAssessmentMethodName() != null && !excel.getAssessmentMethodName().trim().equals("")) {
                String[] methodName = excel.getAssessmentMethodName().split("-"); // 分出考核方法ID和名称
                assessmentMethod = Integer.parseInt(methodName[0]);
                TrainConstant methodInfo = trainConstantService.getInfoBySblId(assessmentMethod, 3, "0");
                if (methodInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，所选考核方法不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                assessmentMethodName = methodInfo.getName();
            }
            // 判断经费预算是否为空
            if (excel.getOutlay() == null || excel.getOutlay().compareTo(BigDecimal.ZERO) == -1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，经费预算不能小于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否有重复
            String mapKey = excel.getTrainName();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的培训名称重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);

            if (errNum == 0) {
                TrainDemandFeedbackDetailVO detailVO = new TrainDemandFeedbackDetailVO();
                BeanUtils.copyProperties(excel,detailVO);
                detailVO.setId(null);
                detailVO.setDemandId(demandId);
                detailVO.setCompanyCode(userInfo.getCompanyCode());
                detailVO.setCompanyName(userInfo.getCompanyName());
                detailVO.setDepartmentCode(userInfo.getDepartmentCode());
                detailVO.setDepartmentName(userInfo.getDepartmentName());
                detailVO.setCourseType(courseType);
                detailVO.setCourseTypeName(courseTypeInfo.getName());
                detailVO.setTrainShape(trainShape);
                detailVO.setTrainShapeName(trainShapeName[1]);
                detailVO.setTrainCompleteTime(completeTime);
                detailVO.setTrainRequirements(trainRequirements);
                detailVO.setTrainRequirementsName(requirementsInfo.getName());
                detailVO.setTeacherId(teacherId);
                detailVO.setTeacherName(teacherName);
                detailVO.setAssessmentMethod(assessmentMethod);
                detailVO.setAssessmentMethodName(assessmentMethodName);
                dataList.add(detailVO);
            }
        }
        TrainDemandFeedbackDetailExcelVO excelVO = new TrainDemandFeedbackDetailExcelVO();
        excelVO.setErrorList(errorList);
        excelVO.setDataList(dataList);
        return excelVO;
    }

    /**
     * 汇总需求-批量导入
     * @param excelList
     * @param demandId
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DemandFeedbackImportExcel> importExl(List<DemandFeedbackImportExcel> excelList, Long demandId, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<DemandFeedbackImportExcel> errorList = new ArrayList<>();
        List<TrainDemandFeedbackDetail> detailEntityList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, Integer> feedbackUserMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        Date now = new Date();
        for (DemandFeedbackImportExcel excel : excelList) {
            i++;
            DemandFeedbackImportExcel errVO = new DemandFeedbackImportExcel();
            // 判断反馈人工号是否为空
            if (excel.getFeedbackUserId() == null || excel.getFeedbackUserId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getFeedbackUserId());
            // 判断反馈人工号是否正确
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断反馈人工号和姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getFeedbackUserName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号和反馈人姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否为空
            if (excel.getTrainName() == null || excel.getTrainName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否有重复
            String mapKey = excel.getTrainName();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的培训名称重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断培训名称是不已存在系统中
            /*TrainDemandFeedbackDetail detailInfo = this.getInfoByTrainName(demandId, excel.getTrainName());
            if (detailInfo != null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的培训名称系统中已存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }*/
            // 判断培训形式是否为空
            if (excel.getTrainShapeName() == null || excel.getTrainShapeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择培训形式");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否正确
            String[] trainShapeName = excel.getTrainShapeName().split("-"); // 分出培训形式编号和名称
            int trainShape = Integer.parseInt(trainShapeName[0]);
            if (trainShape != 1 && trainShape != 2) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选培训形式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否为空
            if (excel.getCourseTypeName() == null || excel.getCourseTypeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择课程类别");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否正确
            String[] courseTypeName = excel.getCourseTypeName().split("-"); // 分出课程类别ID和名称
            int courseType = Integer.parseInt(courseTypeName[0]);
            TrainConstant courseTypeInfo = trainConstantService.getInfoBySblId(courseType, 1, "0");
            if (courseTypeInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选课程类别不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断受训对象是否为空
            if (excel.getTrainSubject() == null || excel.getTrainSubject().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，受训对象为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间是否为空
            if (excel.getTrainCompleteTime() == null || excel.getTrainCompleteTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间要不能小于今天
            Date completeTime;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                Date dateTmp = sdf.parse(excel.getTrainCompleteTime());
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(dateTmp);
                completeTime = java.sql.Date.valueOf(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，拟完成时间格式不正确");
            }
            if (completeTime.before(now)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间小于今天日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训需求依据是否为空
            if (excel.getTrainRequirementsName() == null || excel.getTrainRequirementsName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择培训需求依据");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训需求依据是否正确
            String[] requirementsName = excel.getTrainRequirementsName().split("-"); // 分出培训需求依据ID和名称
            int trainRequirements = Integer.parseInt(requirementsName[0]);
            TrainConstant requirementsInfo = trainConstantService.getInfoBySblId(trainRequirements, 2, "0");
            if (requirementsInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选培训需求依据不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断参训人数是否为空
            if (excel.getParticipantsNum() == null || excel.getParticipantsNum() <= 0) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参训人数必须大于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断讲师编号是否正确
            Long teacherId = null;
            if (excel.getTeacherNum() != null && !excel.getTeacherNum().trim().equals("")) {
                TrainBaseTeacher teacherInfo = trainBaseTeacherService.queryInfoByNumber(excel.getTeacherNum());
                if (teacherInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，培训讲师编号不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                teacherId = teacherInfo.getId();
            }
            // 判断考核方法是否正确
            Integer assessmentMethod = null;
            if (excel.getAssessmentMethodName() != null && !excel.getAssessmentMethodName().trim().equals("")) {
                String[] methodName = excel.getAssessmentMethodName().split("-"); // 分出考核方法ID和名称
                assessmentMethod = Integer.parseInt(methodName[0]);
                TrainConstant methodInfo = trainConstantService.getInfoBySblId(assessmentMethod, 3, "0");
                if (methodInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，所选考核方法不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断经费预算是否为空
            if (excel.getOutlay() == null || excel.getOutlay().compareTo(BigDecimal.ZERO) == -1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，经费预算不能小于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }

            String userMapKey = excel.getFeedbackUserId();
            if (!feedbackUserMap.containsKey(userMapKey)) {
                feedbackUserMap.put(userMapKey, i);
            }
            if (errNum == 0) {
                TrainDemandFeedbackDetail detailEntity = new TrainDemandFeedbackDetail();
                BeanUtils.copyProperties(excel, detailEntity);
                detailEntity.setDemandId(demandId);
                detailEntity.setCourseType(courseType);
                detailEntity.setTrainShape(trainShape);
                detailEntity.setTrainCompleteTime(completeTime);
                detailEntity.setTrainRequirements(trainRequirements);
                detailEntity.setAssessmentMethod(assessmentMethod);
                detailEntity.setTeacherId(teacherId);
                detailEntity.setCreateUser(loginEmplId);
                detailEntity.setCreateTime(stamp);
                detailEntity.setUpdateUser(loginEmplId);
                detailEntity.setUpdateTime(stamp);
                detailEntityList.add(detailEntity);
            }
        }
        if (errNum == 0) {
            for (Map.Entry<String, Integer> entry : feedbackUserMap.entrySet()) {
                String feekbackUserId = entry.getKey();
                PsPersionVO feedbackUserInfo = iStaffUserInfoService.getUserInfoById(feekbackUserId);
                TrainDemandFeedback feedback = new TrainDemandFeedback();
                feedback.setDemandId(demandId);
                feedback.setFeedbackUserId(feekbackUserId);
                TrainDemandFeedback existUser = trainDemandFeedbackService.selectOne(feedback);
                if (existUser != null) {
                    //更新反馈状态
                    if (existUser.getStatus() == 0) {
                        existUser.setStatus(1);
                        existUser.setUpdateUser(loginEmplId);
                        existUser.setUpdateTime(stamp);
                        trainDemandFeedbackService.updateSelective(existUser);
                    }
                } else {
                    feedback.setCompanyCode(feedbackUserInfo.getCompanyCode());
                    feedback.setDepartmentCode(feedbackUserInfo.getDepartmentCode());
                    feedback.setStatus(1);
                    feedback.setCreateTime(stamp);
                    feedback.setCreateUser(loginEmplId);
                    feedback.setUpdateUser(loginEmplId);
                    feedback.setUpdateTime(stamp);
                    trainDemandFeedbackService.insertSelective(feedback);
                }
            }
            this.insertBatchSelective(detailEntityList);
        }
        return errorList;
    }

    /**
     * 根据需求ID和培训名称查询
     * @param demandId
     * @param trainName
     * @return
     */
    private TrainDemandFeedbackDetail getInfoByTrainName(Long demandId, String trainName) {
        //校验反馈名称不重复
        TrainDemandFeedbackDetail query = new TrainDemandFeedbackDetail();
        query.setTrainName(trainName);
        query.setDemandId(demandId);
        return this.selectOne(query);
    }
}