package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.vo.exam.ExamResultDetailsVO;
import com.dyys.hr.vo.train.TrainExaminerDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainExaminerDetailMapper extends ICrudMapper<TrainExaminerDetail> {
    List<TrainExaminerDetailVO> getListByQuery(Map<String,Object> params);

    /**
     * 获取考试结果详情
     * @param detailsId 详情id
     * @return 结果详情
     */
    ExamResultDetailsVO getExamDetails(@Param("detailsId") String detailsId);

    List<String> getIdsByQuery(Map<String,Object> params);

    void deleteByQuery(Map<String,Object> query);
}