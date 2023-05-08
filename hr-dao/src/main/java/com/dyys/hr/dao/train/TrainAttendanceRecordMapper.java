package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAttendanceRecord;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainAttendanceRecordMapper extends ICrudMapper<TrainAttendanceRecord> {
    List<TrainAttendanceRecordVO> pageList(Map<String, Object> params);

    List<TrainAttendanceStudentSignInDataVO> studentSignInPageList(Map<String, Object> params);

    List<EmployeeAttendanceRecordPageVO> myAttendanceRecordList(Map<String, Object> params);

    EmployeeSignInAttendanceVO getRuleInfoByQuery(Map<String, Object> params);

    TrainAttendanceRecordVO selectByRuleAndUser(@Param("attendanceRulesId") Long attendanceRulesId,
                                                      @Param("userId") String userId);

    List<TrainAttendanceRecordPageVO> recordPageList(Map<String, Object> params);

    List<TrainAttendanceRecordExcel> recordExportList(Map<String, Object> params);

    Boolean deleteByAttendanceRulesId(Long rulesId);
}