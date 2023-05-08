package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffUserInfo;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.statis.*;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工基本信息表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
public interface IStaffUserInfoService extends ICrudService<StaffUserInfo, Long> {
    /**
     * 员工筛选列表
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 获取用户信息
     * @param loginUserId
     * @return
     */
    PsPersionVO getUserInfoById(String loginUserId);

    /**
     * 员工详细信息筛选列表
     * @param params
     * @return
     */
    PageInfo<PsPersionVO> detailsSelectList(Map<String, Object> params);

    /**
     * 校验批量导入参加人员信息
     * @param excelList
     * @return
     */
    Map<String, List<ParticipantsExcel>> checkUserData(List<ParticipantsExcel> excelList);

    /**
     * 员工工作台数据
     * @param loginUserId
     * @return
     */
    EmployeeRelateDataVO relatedData(String loginUserId);

    /**
     * 员工-首页-培训课表
     * @param params
     * @return
     */
    List<List<EmployeeTrainingScheduleVO>> trainingSchedule(Map<String, Object> params);

    /**
     * 管理员工作台数据
     * @param params
     * @return
     */
    AdminRelateDataVO adminRelatedData(Map<String, Object> params);

    /**
     * 管理员-首页-已授课类别分布
     * @param params
     * @return
     */
    List<StatisticalProportionVo> taughtTypeDistribution(Map<String, Object> params);

    /**
     * 管理员-首页-培训费用培训形式分布
     * @param params
     * @return
     */
    List<StatisticalProportionVo> trainingCostShapeDistribution(Map<String, Object> params);

    /**
     * 管理员-首页-培训费用职等分布
     * @param params
     * @return
     */
    List<StatisticalProportionVo> trainingCostLevelDistribution(Map<String, Object> params);

    /**
     * 培训项目总数分布趋势(管理员-首页、BI培训分析-图数据)
     * @param params
     * @return
     */
    List<MonthlyVO> trainMonthlyTrend(Map<String, Object> params);

    /**
     * BI培训分析-培训项目总数分布趋势-表数据
     * @param params
     * @return
     */
    List<MonthlyDeptVO> trainMonthlyTrendList(Map<String, Object> params);

    /**
     * BI培训分析-已授课程类别分布-图数据
     * @param params
     * @return
     */
    List<CourseCateVO> courseCateStatis(Map<String, Object> params);

    /**
     * BI培训分析-已授课程类别分布-表数据
     * @param params
     * @return
     */
    List<CourseCateDeptVO> courseCateDeptStatis(Map<String, Object> params);

    /**
     * BI培训分析-培训费用占比(按培训形式)-图数据
     * @param params
     * @return
     */
    List<TrainShapeCostVO> costByTrainShape(Map<String, Object> params);

    /**
     * BI培训分析-培训费用占比(按培训形式)-表数据
     * @param params
     * @return
     */
    List<TrainShapeCostDeptVO> costDeptByTrainShape(Map<String, Object> params);

    /**
     * 培训费用占比(按职级)
     * @param params
     * @return
     */
    List<PosnGradeCostVO> costByPosnGrade(Map<String, Object> params);

    /**
     * BI系统培训相关类型字典
     * @return
     */
    List<TrainTypeBiDictVO> trainTypeBiDictList();
}
