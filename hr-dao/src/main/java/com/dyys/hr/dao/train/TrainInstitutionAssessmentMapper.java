package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainInstitutionAssessment;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 培训机构评估记录 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Repository
public interface TrainInstitutionAssessmentMapper extends ICrudMapper<TrainInstitutionAssessment> {
    /**
     * 某个机构的评估记录列表
     * @param institutionId
     * @return
     */
    List<TrainInstitutionAssessmentVO> assessmentListByInstitutionId(Long institutionId);

    /**
     * 机构评估记录详情
     * @param id
     * @return
     */
    TrainInstitutionAssessmentVO queryInfoById(Long id);

    /**
     * 获取即将更新结束的评估数据列表
     * @return
     */
    List<TrainInstitutionAssessment> finishedList();
}
