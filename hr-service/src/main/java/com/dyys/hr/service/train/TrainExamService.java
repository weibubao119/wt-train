package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.excel.ExamDetailListExcel;
import com.dyys.hr.vo.train.EmployeeExamPageVO;
import com.dyys.hr.vo.train.TrainExamCheckDetailListVO;
import com.dyys.hr.vo.train.TrainExamDetailVO;
import com.dyys.hr.vo.train.TrainExamVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainExamService extends ICrudService<TrainExam, Long> {
    PageInfo<TrainExamVO> pageList(Map<String, Object> params);

    Long save(TrainExamDTO dto, String loginUserId);

    TrainExamDetailVO getDetail(Long examId);

    Integer update(TrainExamDTO dto, String loginUserId);

    PageInfo<EmployeeExamPageVO> myExamList(Map<String, Object> params);

    PageInfo<TrainExamCheckDetailListVO> checkExamDetailsList(Map<String, Object> params);

    /**
     * 导出项目所有考试明细列表
     * @param params
     * @return
     */
    List<ExamDetailListExcel> exportExamDetailsList(Map<String, Object> params);

    /**
     * 导出当前考试明细列表
     * @param params
     * @return
     */
    List<ExamDetailListExcel> exportThisExamDetailsList(Map<String, Object> params);

    /**
     * 考试成绩excel模板下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();
}