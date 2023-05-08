package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainInstitutionGradeDTO;
import com.dyys.hr.entity.train.TrainInstitutionGrade;
import com.dyys.hr.vo.train.TrainInstitutionGradeVO;

import java.util.Map;

/**
 * <p>
 * 培训机构等级 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-05-23
 */
public interface TrainInstitutionGradeService extends ICrudService<TrainInstitutionGrade, Long> {
    /**
     * 机构等级分页列表
     * @param params
     * @return
     */
    PageView<TrainInstitutionGradeVO> institutionGradePageList(Map<String, Object> params);

    /**
     * 校验同一个机构设置等级的年度
     * @param gradeDTO
     * @param checkType
     * @return
     */
    String checkYearly(TrainInstitutionGradeDTO gradeDTO, String checkType);

    /**
     * 机构等级添加保存
     * @param gradeDTO
     * @param loginEmplId
     * @return
     */
    Long institutionGradeAdd(TrainInstitutionGradeDTO gradeDTO, String loginEmplId);

    /**
     * 机构等级更新保存
     * @param gradeDTO
     * @param loginEmplId
     * @return
     */
    Integer institutionGradeUpdate(TrainInstitutionGradeDTO gradeDTO, String loginEmplId);
}
