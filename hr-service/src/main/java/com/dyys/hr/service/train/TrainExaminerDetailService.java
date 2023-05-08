package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.vo.exam.ExamResultDetailsVO;
import com.dyys.hr.vo.train.TrainExaminerDetailVO;

import java.util.List;
import java.util.Map;

public interface TrainExaminerDetailService extends ICrudService<TrainExaminerDetail, Long> {
    List<TrainExaminerDetailVO> getListByQuery(Map<String,Object> params);

    /**
     * 查找考试结果详情
     * @param detailsId 考试记录id
     * @return 考试结果详情
     */
    ExamResultDetailsVO getExamDetails(String detailsId);

    void restExamDeleteByQuery(Map<String,Object> params);
}