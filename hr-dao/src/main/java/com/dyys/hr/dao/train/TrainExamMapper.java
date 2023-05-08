package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.excel.ExamDetailListExcel;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainExamMapper extends ICrudMapper<TrainExam> {
    List<TrainExamVO> pageList(Map<String, Object> params);

    List<EmployeeExamPageVO> myExamList(Map<String, Object> params);

    TrainExamDetailVO getDetail(@Param("id") Long examId);

    List<TrainExamCheckDetailListVO> checkExamDetailsList(Map<String, Object> params);

    List<ExamDetailListExcel> exportExamDetailsList(Map<String, Object> params);

    List<ExamDetailListExcel> exportThisExamDetailsList(Map<String, Object> params);
}