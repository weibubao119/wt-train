package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainInstitutionAssessmentStaffMapper;
import com.dyys.hr.entity.train.TrainInstitutionAssessmentStaff;
import com.dyys.hr.service.train.TrainInstitutionAssessmentStaffService;
import com.dyys.hr.service.train.TrainProgramsPlanService;
import com.dyys.hr.vo.common.CourseChoiceVO;
import com.dyys.hr.vo.common.ProgramsChoiceVO;
import com.dyys.hr.vo.train.EmplInstitutionAssessVO;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentStaffVO;
import com.dyys.hr.vo.train.TrainInstitutionStaffVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构评估参与人员 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Service
@Slf4j
public class TrainInstitutionAssessmentStaffServiceImpl extends AbstractCrudService<TrainInstitutionAssessmentStaff, Long> implements TrainInstitutionAssessmentStaffService {
    @Autowired
    private TrainInstitutionAssessmentStaffMapper trainInstitutionAssessmentStaffMapper;
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;

    /**
     * 根据评估记录ID查询该评估记录的参评人员分页列表数据
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionAssessmentStaffVO> assessmentStaffPageList(Map<String, Object> params) {
        PageMethod.startPage(Convert.toInt(params.get("page")), Convert.toInt(params.get("limit")));
        List<TrainInstitutionAssessmentStaffVO> list = trainInstitutionAssessmentStaffMapper.assessmentStaffListByAssessmentId(Convert.toLong(params.get("assessmentId")));
        return PageView.build(list);
    }

    /**
     * 评估详情-参评人员移除
     * @param id
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer assessmentStaffDelete(Long id, String loginEmplId) {
        TrainInstitutionAssessmentStaff staffEntity = new TrainInstitutionAssessmentStaff();
        staffEntity.setId(id);
        staffEntity.setIsDelete(1);
        staffEntity.setUpdateUser(loginEmplId);
        staffEntity.setUpdateTime(System.currentTimeMillis()/1000);
        return updateSelective(staffEntity);
    }

    /**
     * 员工端-评估中心-机构评估列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<EmplInstitutionAssessVO> institutionAssessPageList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmplInstitutionAssessVO> voList = trainInstitutionAssessmentStaffMapper.institutionAssessList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 删除评估记录的参评人员
     * @param assessmentId
     * @return
     */
    @Override
    public Integer delByCondition(Long assessmentId) {
        Condition condition = new Condition(TrainInstitutionAssessmentStaff.class);
        condition.createCriteria().andEqualTo("assessmentId", assessmentId);
        return trainInstitutionAssessmentStaffMapper.deleteByCondition(condition);
    }

    /**
     * 机构评估-人员选择列表
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionStaffVO> staffList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        List<Long> programsIds = trainProgramsPlanService.selectProgramsIds(params);
        PageMethod.startPage(page, limit);
        List<TrainInstitutionStaffVO> list;
        if (!programsIds.isEmpty()) {
            list = trainInstitutionAssessmentStaffMapper.staffList(params, programsIds);
        } else {
            list = new ArrayList<>();
        }
        return PageView.build(list);
    }

    /**
     * 机构评估-人员选择列表-课程名称搜索项
     * @param params
     * @return
     */
    @Override
    public PageView<CourseChoiceVO> courseChoiceList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        List<Long> courseIds = trainProgramsPlanService.selectCourseIds(params);
        List<CourseChoiceVO> list;
        PageMethod.startPage(page, limit);
        if (!courseIds.isEmpty()) {
            list = trainInstitutionAssessmentStaffMapper.courseChoiceList(params, courseIds);
        } else {
            list = new ArrayList<>();
        }
        return PageView.build(list);
    }

    /**
     * 机构评估-人员选择列表-培训项目搜索项
     * @param params
     * @return
     */
    @Override
    public PageView<ProgramsChoiceVO> programsChoiceList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        List<Long> programsIds = trainProgramsPlanService.selectProgramsIds(params);
        List<ProgramsChoiceVO> list;
        PageMethod.startPage(page, limit);
        if (!programsIds.isEmpty()) {
            list = trainInstitutionAssessmentStaffMapper.programsChoiceList(params, programsIds);
        } else {
            list = new ArrayList<>();
        }
        return PageView.build(list);
    }
}
