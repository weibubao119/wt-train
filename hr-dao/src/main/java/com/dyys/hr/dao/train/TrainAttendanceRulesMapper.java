package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAttendanceRules;
import com.dyys.hr.entity.train.excel.TrainAttendanceRulesExcel;
import com.dyys.hr.vo.train.TrainAttendanceRulesDetailVO;
import com.dyys.hr.vo.train.TrainAttendanceRulesVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainAttendanceRulesMapper extends ICrudMapper<TrainAttendanceRules> {
    List<TrainAttendanceRulesVO> pageList(Map<String, Object> params);

    TrainAttendanceRulesDetailVO getDetail(@Param("id") Long rulesId);

    List<TrainAttendanceRulesExcel> ruleListExport(Map<String, Object> params);
}