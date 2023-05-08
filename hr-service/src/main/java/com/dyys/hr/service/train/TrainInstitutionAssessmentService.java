package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainInstitutionAssessmentDTO;
import com.dyys.hr.entity.train.TrainInstitutionAssessment;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构评估记录 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
public interface TrainInstitutionAssessmentService extends ICrudService<TrainInstitutionAssessment, Long> {
    /**
     * 机构评估记录分页列表
     * @param params
     * @return
     */
    PageView<TrainInstitutionAssessmentVO> assessmentPageList(Map<String, Object> params);

    /**
     * 机构评估创建保存
     * @param assessmentDTO
     * @param loginEmplId
     * @return
     */
    Long assessmentAdd(TrainInstitutionAssessmentDTO assessmentDTO, String loginEmplId);

    /**
     * 机构评估记录详情
     * @param id
     * @return
     */
    TrainInstitutionAssessmentVO assessmentInfo(Long id);

    /**
     * 机构评估取消
     * @param id
     * @param loginEmplId
     * @return
     */
    Integer assessmentCancel(Long id, String loginEmplId);

    /**
     * 获取即将更新结束的评估数据列表
     * @return
     */
    List<TrainInstitutionAssessment> finishedList();

    /**
     * 校验评估数据唯一
     * @param assessmentDTO
     * @return
     */
    Boolean checkUniqueData(TrainInstitutionAssessmentDTO assessmentDTO);

    /**
     * 评估更新保存
     * @param assessmentDTO
     * @param loginEmplId
     * @return
     */
    Integer assessmentUpdate(TrainInstitutionAssessmentDTO assessmentDTO, String loginEmplId);
}
