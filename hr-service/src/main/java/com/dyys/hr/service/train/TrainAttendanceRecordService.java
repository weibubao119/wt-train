package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.EmploySignInAttendanceDTO;
import com.dyys.hr.entity.train.TrainAttendanceRecord;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.vo.train.EmployeeAttendanceRecordPageVO;
import com.dyys.hr.vo.train.TrainAttendanceRecordPageVO;
import com.dyys.hr.vo.train.TrainAttendanceRecordVO;
import com.dyys.hr.vo.train.TrainAttendanceStudentSignInDataVO;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TrainAttendanceRecordService extends ICrudService<TrainAttendanceRecord, Long> {
    PageInfo<TrainAttendanceRecordVO> pageList(Map<String, Object> params);

    PageInfo<TrainAttendanceStudentSignInDataVO> studentSignInPageList(Map<String, Object> params);

    PageInfo<EmployeeAttendanceRecordPageVO> myAttendanceRecordList(Map<String, Object> params);

    Integer signInAttendance(EmploySignInAttendanceDTO dto) throws ParseException;

    /**
     * 考勤明细列表
     * @param params
     * @return
     */
    PageInfo<TrainAttendanceRecordPageVO> recordPageList(Map<String, Object> params);

    List<TrainAttendanceRecordExcel> recordExportList(Map<String, Object> params);
}