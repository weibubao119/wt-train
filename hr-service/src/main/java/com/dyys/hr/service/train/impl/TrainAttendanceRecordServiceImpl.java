package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAttendanceRecordMapper;
import com.dyys.hr.dao.train.TrainAttendanceRulesMapper;
import com.dyys.hr.dto.train.EmploySignInAttendanceDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordImportExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainAttendanceRecordService;
import com.dyys.hr.service.train.TrainAttendanceRulesService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class TrainAttendanceRecordServiceImpl extends AbstractCrudService<TrainAttendanceRecord, Long> implements TrainAttendanceRecordService {
    @Autowired
    private TrainAttendanceRecordMapper trainAttendanceRecordMapper;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainAttendanceRulesService trainAttendanceRulesService;
    @Resource
    private TrainAttendanceRulesMapper trainAttendanceRulesMapper;

    @Override
    public PageInfo<TrainAttendanceRecordVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainAttendanceRecordVO> voList = trainAttendanceRecordMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<TrainAttendanceStudentSignInDataVO> studentSignInPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        Long attendanceRulesId = Convert.toLong(params.get("attendanceRulesId"));

        PageMethod.startPage(page, limit);
        List<TrainAttendanceStudentSignInDataVO> voList = trainAttendanceRecordMapper.studentSignInPageList(params);


        if(voList != null && !voList.isEmpty()){
            for (TrainAttendanceStudentSignInDataVO vo : voList){

                TrainAttendanceRecordVO recordVO
                        = trainAttendanceRecordMapper.selectByRuleAndUser(attendanceRulesId, vo.getUserId());
                PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(vo.getUserId());

                if(!ObjectUtil.isEmpty(recordVO)){
                    vo.setSignInTime(recordVO.getSignedInTime());
//                    vo.setStatus(recordVO.getStatus());
                }
                if(userInfo != null){
                    vo.setUserName(userInfo.getEmplName());
                    vo.setCompanyCode(userInfo.getCompanyCode());
                    vo.setCompanyName(userInfo.getCompanyName());
                    vo.setDepartmentCode(userInfo.getDepartmentCode());
                    vo.setDepartmentName(userInfo.getDepartmentName());
                }
            }
        }
        assert voList != null;
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<EmployeeAttendanceRecordPageVO> myAttendanceRecordList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeAttendanceRecordPageVO> voList = trainAttendanceRecordMapper.myAttendanceRecordList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer signInAttendance(EmploySignInAttendanceDTO dto) throws ParseException {
        Map<String, Object> query = new HashMap<>();
        query.put("attendanceRulesId",dto.getAttendanceRulesId());
        query.put("emplId",dto.getEmplId());
        EmployeeSignInAttendanceVO info = trainAttendanceRecordMapper.getRuleInfoByQuery(query);
        //判断签到记录是否存在
        if(info == null){
            throw new BusinessException(ResultCode.EXCEPTION, "非本次考勤参与人员，不可签到！");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(StringUtils.substring(info.getStartTime(), 0, 16) + ":00"); // 签到开始时间
        Date endDate = simpleDateFormat.parse(StringUtils.substring(info.getEndTime(), 0, 16) + ":00"); // 签到结束时间
        Date courseEndTime = simpleDateFormat.parse(StringUtils.substring(info.getCourseEndTime(), 0, 16) + ":00"); // 本节课结束时间
        long timeNow = System.currentTimeMillis();
        //判断是否开始签到
        if(timeNow < startDate.getTime()){
            throw new BusinessException(ResultCode.EXCEPTION, "签到时间未开始!");
        }
        //判断本节课程是否已结束
        if(timeNow > courseEndTime.getTime()){
            throw new BusinessException(ResultCode.EXCEPTION, "本节课程已结束，不可签到!");
        }
        //判断是否重复签到
        if(info.getStatus() > 0 || !Objects.equals(info.getSignedInTime(), "")){
            throw new BusinessException(ResultCode.EXCEPTION, "不能重复签到!");
        }



        //开始进行签到操作,如果大于结束时间则为迟到签到
        int status = 1;
        if(timeNow >  endDate.getTime()){
            status = 2;
        }
        String signInTime = simpleDateFormat.format(new Date()).substring(11);
        TrainAttendanceRecord recordEntity = new TrainAttendanceRecord();
        recordEntity.setId(info.getId());
        recordEntity.setStatus(status);
        recordEntity.setSignedInTime(signInTime);
        recordEntity.setUpdateUser(dto.getEmplId());
        recordEntity.setUpdateTime(System.currentTimeMillis()/1000);

        //更新签到规则的考勤统计总人数
        TrainAttendanceRules trainAttendanceRules = trainAttendanceRulesService.selectById(dto.getAttendanceRulesId());
        TrainAttendanceRules rulesEntity = new TrainAttendanceRules();
        rulesEntity.setId(dto.getAttendanceRulesId());
        if(status == 1){
            rulesEntity.setAttendanceNum(trainAttendanceRules.getAttendanceNum() + 1);
        }else{
            rulesEntity.setLateNum(trainAttendanceRules.getLateNum() + 1);
        }
        rulesEntity.setUpdateTime(System.currentTimeMillis()/1000);
        trainAttendanceRulesService.updateSelective(rulesEntity);

        return this.updateSelective(recordEntity);
    }

    @Override
    public PageInfo<TrainAttendanceRecordPageVO> recordPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainAttendanceRecordPageVO> voList = trainAttendanceRecordMapper.recordPageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public List<TrainAttendanceRecordExcel> recordExportList(Map<String, Object> params){
        return trainAttendanceRecordMapper.recordExportList(params);
    }


    /**
     * 考勤导入模板下拉框数据
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();

        // 培训形式下拉项
        List<String> signStatusList = new ArrayList<>();
        signStatusList.add("0-未签到");
        signStatusList.add("1-已签到");
        signStatusList.add("2-迟到签到");
        selectMap.put(2, signStatusList);
        return selectMap;
    }


    /**
     * 导入数据处理
     * @param excelList
     * @param attendanceRulesId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrainAttendanceRecordImportExcelVO handleAttendanceImportExcel(List<TrainAttendanceRecordImportExcel> excelList,Long attendanceRulesId,String loginUserId) {
        List<TrainAttendanceRecordImportExcel> errorList = new ArrayList<>();
        List<TrainAttendanceRecordVO> dataList = new ArrayList<>();
        int i = 0;
        int errNum = 0;
        for (TrainAttendanceRecordImportExcel excel : excelList) {
            i++;
            TrainAttendanceRecordImportExcel errVO = new TrainAttendanceRecordImportExcel();
            // 判断学员人工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getEmplId());
            // 判断学员工号是否正确
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学员工号与姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getEmplName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号与学员姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            TrainAttendanceRecord recordQuery = new TrainAttendanceRecord();
            recordQuery.setAttendanceRulesId(attendanceRulesId);
            recordQuery.setEmplId(excel.getEmplId());
            TrainAttendanceRecord selectOne = this.selectOne(recordQuery);
            // 判断考勤学员是否存在
            if (selectOne == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，当前考勤不存在此学员");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考勤状态是否为空
            if (excel.getStatus() == null || excel.getStatus().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择考勤状态");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考勤状态是否正确
            String[] statusTitle = excel.getStatus().split("-"); // 分出培训形式编号和名称
            int status = Integer.parseInt(statusTitle[0]);
            if (status != 0 && status != 1 && status != 2) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选考勤状态不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }

            if (errNum == 0) {
                TrainAttendanceRecordVO vo = new TrainAttendanceRecordVO();
                BeanUtils.copyProperties(excel,vo);
                vo.setUserId(userInfo.getEmplId());
                vo.setUserName(userInfo.getEmplName());
                vo.setCompanyCode(userInfo.getCompanyCode());
                vo.setCompanyName(userInfo.getCompanyName());
                vo.setDepartmentCode(userInfo.getDepartmentCode());
                vo.setDepartmentName(userInfo.getDepartmentName());
                vo.setJobCode(userInfo.getJobCode());
                vo.setJobName(userInfo.getJobName());
                vo.setStatus(statusTitle[0]);
                vo.setStatusName(statusTitle[1]);
                dataList.add(vo);
            }
        }
        //判断导入数据正常则批量处理入库
        if(errNum == 0){
            for (TrainAttendanceRecordImportExcel excel : excelList) {
                TrainAttendanceRecord updateRecord = new TrainAttendanceRecord();
                String[] statusTitle = excel.getStatus().split("-"); // 分出培训形式编号和名称
                int status = Integer.parseInt(statusTitle[0]);
                updateRecord.setStatus(status);
                updateRecord.setSignedInTime(excel.getSignedInTime());
                updateRecord.setUpdateUser(loginUserId);
                updateRecord.setUpdateTime(System.currentTimeMillis()/1000);
                Condition condition = new Condition(TrainAttendanceRecord.class);
                condition.createCriteria().andCondition("attendance_rules_id = " + attendanceRulesId  + " and empl_id = '" + excel.getEmplId() + "'");
                this.updateByConditionSelective(updateRecord,condition);
            }
            //更新规则表统计数据
            TrainAttendanceRecord recordUpdate = new TrainAttendanceRecord();
            recordUpdate.setAttendanceRulesId(attendanceRulesId);
            recordUpdate.setStatus(0);
            //未签到人数
            int absentNum = trainAttendanceRecordMapper.selectCount(recordUpdate);
            //出勤人数
            recordUpdate.setStatus(1);
            int attendanceNum = trainAttendanceRecordMapper.selectCount(recordUpdate);
            //迟到人数
            recordUpdate.setStatus(2);
            int lateNum = trainAttendanceRecordMapper.selectCount(recordUpdate);

            TrainAttendanceRules attendanceRules = new TrainAttendanceRules();
            attendanceRules.setId(attendanceRulesId);
            attendanceRules.setAbsentNum(absentNum);
            attendanceRules.setAttendanceNum(attendanceNum);
            attendanceRules.setLateNum(lateNum);
            attendanceRules.setUpdateUser(loginUserId);
            attendanceRules.setUpdateTime(System.currentTimeMillis()/1000);
            trainAttendanceRulesService.updateSelective(attendanceRules);
        }

        TrainAttendanceRecordImportExcelVO excelVO = new TrainAttendanceRecordImportExcelVO();
        excelVO.setErrorList(errorList);
        excelVO.setDataList(dataList);
        return excelVO;
    }

}