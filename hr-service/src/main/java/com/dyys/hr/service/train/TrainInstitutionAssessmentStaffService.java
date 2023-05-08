package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainInstitutionAssessmentStaff;
import com.dyys.hr.vo.common.CourseChoiceVO;
import com.dyys.hr.vo.common.ProgramsChoiceVO;
import com.dyys.hr.vo.train.EmplInstitutionAssessVO;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentStaffVO;
import com.dyys.hr.vo.train.TrainInstitutionStaffVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 培训机构评估参与人员 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
public interface TrainInstitutionAssessmentStaffService extends ICrudService<TrainInstitutionAssessmentStaff, Long> {
    /**
     * 根据评估记录ID查询该评估记录的参评人员分页列表数据
     * @param params
     * @return
     */
    PageView<TrainInstitutionAssessmentStaffVO> assessmentStaffPageList(Map<String, Object> params);

    /**
     * 评估详情-参评人员移除
     * @param id
     * @param loginEmplId
     * @return
     */
    Integer assessmentStaffDelete(Long id, String loginEmplId);

    /**
     * 员工端-评估中心-机构评估列表
     * @param params
     * @return
     */
    PageInfo<EmplInstitutionAssessVO> institutionAssessPageList(Map<String, Object> params);

    /**
     * 删除评估记录的参评人员
     * @param assessmentId
     * @return
     */
    Integer delByCondition(Long assessmentId);

    /**
     * 机构评估-人员选择列表
     * @param params
     * @return
     */
    PageView<TrainInstitutionStaffVO> staffList(Map<String, Object> params);

    /**
     * 机构评估-人员选择列表-课程名称搜索项
     * @param params
     * @return
     */
    PageView<CourseChoiceVO> courseChoiceList(Map<String, Object> params);

    /**
     * 机构评估-人员选择列表-培训项目搜索项
     * @param params
     * @return
     */
    PageView<ProgramsChoiceVO> programsChoiceList(Map<String, Object> params);
}
