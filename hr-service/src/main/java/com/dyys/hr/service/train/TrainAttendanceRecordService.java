package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.EmploySignInAttendanceDTO;
import com.dyys.hr.entity.train.TrainAttendanceRecord;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordImportExcel;
import com.dyys.hr.vo.train.*;
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

    /**
     * 考勤导入模板下拉框数据
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 考勤导入数据处理
     * @param excelList
     * @param attendanceRulesId
     * @return
     */
    TrainAttendanceRecordImportExcelVO handleAttendanceImportExcel(List<TrainAttendanceRecordImportExcel> excelList,Long attendanceRulesId, String loginUserId);
}