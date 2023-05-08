package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAttendancePersonMapper;
import com.dyys.hr.dao.train.TrainAttendanceRecordMapper;
import com.dyys.hr.dao.train.TrainAttendanceRulesMapper;
import com.dyys.hr.dto.train.TrainAttendancePersonDTO;
import com.dyys.hr.dto.train.TrainAttendanceRulesDTO;
import com.dyys.hr.entity.train.TrainAttendancePerson;
import com.dyys.hr.entity.train.TrainAttendanceRecord;
import com.dyys.hr.entity.train.TrainAttendanceRules;
import com.dyys.hr.entity.train.TrainProgramsCourse;
import com.dyys.hr.entity.train.excel.TrainAttendanceRulesExcel;
import com.dyys.hr.service.train.TrainAttendancePersonService;
import com.dyys.hr.service.train.TrainAttendanceRecordService;
import com.dyys.hr.service.train.TrainAttendanceRulesService;
import com.dyys.hr.service.train.TrainProgramsCourseService;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TrainAttendanceRulesServiceImpl extends AbstractCrudService<TrainAttendanceRules, Long> implements TrainAttendanceRulesService {
    @Autowired
    private TrainAttendanceRulesMapper trainAttendanceRulesMapper;
    @Autowired
    private TrainAttendancePersonService trainAttendancePersonService;
    @Autowired
    private TrainAttendanceRecordService trainAttendanceRecordService;
    @Autowired
    private TrainAttendanceRecordMapper trainAttendanceRecordMapper;
    @Autowired
    private TrainAttendancePersonMapper trainAttendancePersonMapper;
    @Autowired
    private TrainProgramsCourseService trainProgramsCourseService;

    @Override
    public PageInfo<TrainAttendanceRulesVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainAttendanceRulesVO> voList = trainAttendanceRulesMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainAttendanceRulesDTO dto, String loginUserId){
        TrainAttendanceRules rulesEntity = new TrainAttendanceRules();
        BeanUtils.copyProperties(dto,rulesEntity);
        rulesEntity.setCreateUser(loginUserId);
        rulesEntity.setCreateTime(System.currentTimeMillis()/1000);
        Long rulesId = this.insertSelective(rulesEntity);
        //批量插入考勤学员和默认待考勤记录数据
        List<TrainAttendancePersonDTO> personDTOS = dto.getAttendancePersonList();
        if(personDTOS != null && !personDTOS.isEmpty()){
            List<TrainAttendancePerson> personList = new ArrayList<>();
            List<TrainAttendanceRecord> recordList = new ArrayList<>();
            for (TrainAttendancePersonDTO personDTO : personDTOS){
                //学员数据
                TrainAttendancePerson personEntity = new TrainAttendancePerson();
                BeanUtils.copyProperties(personDTO,personEntity);
                personEntity.setAttendanceRulesId(rulesId);
                personEntity.setCreateUser(loginUserId);
                personEntity.setCreateTime(System.currentTimeMillis()/1000);
                personList.add(personEntity);
                //默认考勤数据
                TrainAttendanceRecord recordEntity = new TrainAttendanceRecord();
                recordEntity.setAttendanceRulesId(rulesId);
                recordEntity.setEmplId(personDTO.getEmplId());
                recordEntity.setSignedInTime("");
                recordEntity.setStatus(0);
                recordEntity.setCreateUser(loginUserId);
                recordEntity.setCreateTime(System.currentTimeMillis()/1000);
                recordList.add(recordEntity);
            }
            trainAttendancePersonService.insertBatchSelective(personList);
            trainAttendanceRecordService.insertBatchSelective(recordList);
        }
        return rulesId;
    }

    @Override
    public TrainAttendanceRulesDetailVO getDetail(Long rulesId){
        TrainAttendanceRulesDetailVO detail = trainAttendanceRulesMapper.getDetail(rulesId);
        if(detail != null){
            Map<String, Object> params = new HashMap<>();
            params.put("attendance_rules_id",rulesId);
            detail.setAttendancePersonList(trainAttendancePersonService.getListByQuery(params));
        }
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainAttendanceRulesDTO dto, String loginUserId){
        TrainAttendanceRules rulesEntity = new TrainAttendanceRules();
        BeanUtils.copyProperties(dto,rulesEntity);
        rulesEntity.setCreateUser(loginUserId);
        rulesEntity.setCreateTime(System.currentTimeMillis()/1000);

        Long rulesId = dto.getId();
        //批量删除旧考勤人员数据和考勤记录数据
        trainAttendancePersonMapper.deleteByAttendanceRulesId(rulesId);
        trainAttendanceRecordMapper.deleteByAttendanceRulesId(rulesId);

        //批量插入考勤学员和默认待考勤记录数据
        List<TrainAttendancePersonDTO> personDTOS = dto.getAttendancePersonList();
        if(personDTOS != null && !personDTOS.isEmpty()){
            List<TrainAttendancePerson> personList = new ArrayList<>();
            List<TrainAttendanceRecord> recordList = new ArrayList<>();
            for (TrainAttendancePersonDTO personDTO : personDTOS){
                //学员数据
                TrainAttendancePerson personEntity = new TrainAttendancePerson();
                BeanUtils.copyProperties(personDTO,personEntity);
                personEntity.setAttendanceRulesId(rulesId);
                personEntity.setCreateUser(loginUserId);
                personEntity.setCreateTime(System.currentTimeMillis()/1000);
                personList.add(personEntity);
                //默认考勤数据
                TrainAttendanceRecord recordEntity = new TrainAttendanceRecord();
                recordEntity.setAttendanceRulesId(rulesId);
                recordEntity.setEmplId(personDTO.getEmplId());
                recordEntity.setSignedInTime("");
                recordEntity.setStatus(0);
                recordEntity.setCreateUser(loginUserId);
                recordEntity.setCreateTime(System.currentTimeMillis()/1000);
                recordList.add(recordEntity);
            }
            trainAttendancePersonService.insertBatchSelective(personList);
            trainAttendanceRecordService.insertBatchSelective(recordList);
        }
        return this.updateSelective(rulesEntity);
    }

    @Override
    public List<TrainAttendanceRulesExcel> ruleListExport(Map<String, Object> params){
        return trainAttendanceRulesMapper.ruleListExport(params);
    }

    /**
     * 校验考勤规则数据
     * @param dto
     * @return
     */
    @Override
    public String checkData(TrainAttendanceRulesDTO dto) {
        Condition condition = new Condition(TrainAttendanceRules.class);
        if (dto.getId() == null) {
            condition.createCriteria().andEqualTo("programsCourseId", dto.getProgramsCourseId())
                    .andEqualTo("courseId", dto.getCourseId());
        } else {
            condition.createCriteria().andNotEqualTo("id", dto.getId())
                    .andEqualTo("programsCourseId", dto.getProgramsCourseId())
                    .andEqualTo("courseId", dto.getCourseId());
        }
        int res = trainAttendanceRulesMapper.selectCountByCondition(condition);
        if (res > 0) {
            return "同一课表课程只能安排一次考勤";
        }
        TrainProgramsCourse programsCourse = trainProgramsCourseService.selectById(dto.getProgramsCourseId());
        if (!dto.getDate().equals(programsCourse.getSchooltime())) {
            return "考勤日期与课程上课日期不一致";
        }
        String attendStartTime = DateUtil.format(dto.getDate(), "yyyy-MM-dd") + " " + StringUtils.substring(dto.getStartTime(), 0, 5); // 签到开始时间
        String attendEndTime = DateUtil.format(dto.getDate(), "yyyy-MM-dd") + " " + StringUtils.substring(dto.getEndTime(), 0, 5); // 签到结束时间
        String courseStartTime = DateUtil.format(programsCourse.getSchooltime(), "yyyy-MM-dd") + " " + StringUtils.substring(programsCourse.getStartTime(), 0, 5); // 上课开始时间
        String courseEndTime = DateUtil.format(programsCourse.getSchooltime(), "yyyy-MM-dd") + " " + StringUtils.substring(programsCourse.getEndTime(), 0, 5); // 上课结束时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date ast = df.parse(attendStartTime);
            Date aet = df.parse(attendEndTime);
            if (ast.getTime() >= aet.getTime()) {
                return "签到开始时间必须小于签到结束时间";
            }
            Date cst = df.parse(courseStartTime);
            Date cet = df.parse(courseEndTime);
            if (ast.getTime() >= cst.getTime()) {
                return "签到开始时间必须小于课程上课开始时间";
            }
            if (aet.getTime() >= cet.getTime()) {
                return "签到结束时间必须小于课程上课结束时间";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.EXCEPTION, "时间格式异常");
        }
        return "";
    }
}