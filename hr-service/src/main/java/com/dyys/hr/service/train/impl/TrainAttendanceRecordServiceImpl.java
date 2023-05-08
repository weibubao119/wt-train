package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAttendanceRecordMapper;
import com.dyys.hr.dao.train.TrainAttendanceRulesMapper;
import com.dyys.hr.dto.train.EmploySignInAttendanceDTO;
import com.dyys.hr.entity.train.TrainAttendanceRecord;
import com.dyys.hr.entity.train.TrainAttendanceRules;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainAttendanceRecordService;
import com.dyys.hr.service.train.TrainAttendanceRulesService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}