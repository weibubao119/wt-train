package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAppraise;
import com.dyys.hr.vo.train.EmployeeAppraisePageVO;
import com.dyys.hr.vo.train.TrainAppraiseDetailVO;
import com.dyys.hr.vo.train.TrainAppraiseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainAppraiseMapper extends ICrudMapper<TrainAppraise> {
    List<TrainAppraiseVO> pageList(Map<String, Object> params);

    TrainAppraiseDetailVO getDetail(@Param("id") Long appraiseId);

    List<EmployeeAppraisePageVO> myAppraiseList(Map<String, Object> params);

    List<TrainAppraise> finishedList();

    /**
     * 获取同一项目同一课程的课程评分的平均值
     * @param programsId
     * @param courseId
     * @return
     */
    BigDecimal getAvgCourseScore(@Param("programsId") Long programsId, @Param("courseId") Long courseId);

    /**
     * 获取某个项目某个课程某个评分对象最后一次评分
     * @param programsId 培训项目ID
     * @param courseId 课程ID
     * @param scoreObject 1.课程 2.讲师 3.组织方
     * @return
     */
    TrainAppraise getFinalInfo(@Param("programsId") Long programsId, @Param("courseId") Long courseId, @Param("scoreObject") Integer scoreObject);
}