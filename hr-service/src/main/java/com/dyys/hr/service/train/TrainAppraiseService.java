package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireUserSubmitDTO;
import com.dyys.hr.dto.train.TrainAppraiseDTO;
import com.dyys.hr.entity.train.TrainAppraise;
import com.dyys.hr.vo.train.EmployeeAppraisePageVO;
import com.dyys.hr.vo.train.TrainAppraiseDetailVO;
import com.dyys.hr.vo.train.TrainAppraiseVO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TrainAppraiseService extends ICrudService<TrainAppraise, Long> {
    PageInfo<TrainAppraiseVO> pageList(Map<String, Object> params);

    Long save(TrainAppraiseDTO dto, String loginUserId);

    TrainAppraiseDetailVO getDetail(Long appraiseId);

    Integer update(TrainAppraiseDTO dto, String loginUserId);

    PageInfo<EmployeeAppraisePageVO> myAppraiseList(Map<String, Object> params);

    /**
     * 获取同一项目同一课程的课程评分的平均值
     * @param programsId
     * @param courseId
     * @return
     */
    BigDecimal getAvgCourseScore(Long programsId, Long courseId);

    /**
     * 获取即将更新结束的评估数据列表
     * @return
     */
    List<TrainAppraise> finishedList();

    QuestionnaireUserSubmitDTO mynQuestionnaireSubmitDetail(Map<String, Object> params);

    /**
     * 获取某个项目某个课程某个评分对象最后一次评分
     * @param programsId 培训项目ID
     * @param courseId 课程ID
     * @param scoreObject 1.课程 2.讲师 3.组织方
     * @return
     */
    TrainAppraise getFinalInfo(Long programsId, Long courseId, Integer scoreObject);
}