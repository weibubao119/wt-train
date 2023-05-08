package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainInstitutionAssessmentStaff;
import com.dyys.hr.vo.common.CourseChoiceVO;
import com.dyys.hr.vo.common.ProgramsChoiceVO;
import com.dyys.hr.vo.train.EmplInstitutionAssessVO;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentStaffVO;
import com.dyys.hr.vo.train.TrainInstitutionStaffVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构评估参与人员 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Repository
public interface TrainInstitutionAssessmentStaffMapper extends ICrudMapper<TrainInstitutionAssessmentStaff> {
    /**
     * 根据评估记录ID查询该评估记录的参评人员列表数据
     * @param assessmentId
     * @return
     */
    List<TrainInstitutionAssessmentStaffVO> assessmentStaffListByAssessmentId(Long assessmentId);

    /**
     * 员工端-评估中心-机构评估列表
     * @param params
     * @return
     */
    List<EmplInstitutionAssessVO> institutionAssessList(Map<String, Object> params);

    /**
     * 机构评估-人员选择列表
     * @param params
     * @param programsIds
     * @return
     */
    List<TrainInstitutionStaffVO> staffList(@Param("params") Map<String, Object> params, @Param("programsIds") List<Long> programsIds);

    /**
     * 机构评估-人员选择列表-课程名称搜索项
     * @param params
     * @param courseIds
     * @return
     */
    List<CourseChoiceVO> courseChoiceList(@Param("params") Map<String, Object> params, @Param("courseIds") List<Long> courseIds);

    /**
     * 机构评估-人员选择列表-培训项目搜索项
     * @param params
     * @param programsIds
     * @return
     */
    List<ProgramsChoiceVO> programsChoiceList(@Param("params") Map<String, Object> params, @Param("programsIds") List<Long> programsIds);
}
