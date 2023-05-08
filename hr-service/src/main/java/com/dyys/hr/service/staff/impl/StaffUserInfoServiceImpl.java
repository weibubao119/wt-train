package com.dyys.hr.service.staff.impl;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.constants.TrainConstantTypeData;
import com.dyys.hr.dao.staff.StaffUserInfoMapper;
import com.dyys.hr.dao.train.TrainCostMapper;
import com.dyys.hr.dao.train.TrainProgramsPlanMapper;
import com.dyys.hr.entity.staff.StaffUserInfo;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.service.staff.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.common.PsPosnGradeVO;
import com.dyys.hr.vo.common.StaffDeptTerseVO;
import com.dyys.hr.vo.statis.*;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 员工基本信息表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
@Service
public class StaffUserInfoServiceImpl extends AbstractCrudService<StaffUserInfo, Long> implements IStaffUserInfoService {
    @Autowired
    private StaffUserInfoMapper staffUserInfoMapper;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;
    @Autowired
    private TrainCostMapper trainCostMapper;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainProgramsPlanMapper trainProgramsPlanMapper;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;
    @Autowired
    private IStaffPosnGradeService staffPosnGradeService;
    @Autowired
    private TrainDataService trainDataService;

    /**
     * 员工筛选列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> selectList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = staffUserInfoMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 获取用户信息
     * @param loginUserId
     * @return
     */
    @Override
    public PsPersionVO getUserInfoById(String loginUserId) {
        PsPersionVO userInfo = staffUserInfoMapper.getUserInfoById(loginUserId);
        if(userInfo != null && userInfo.getAvatarFileData() != null){
            userInfo.setAvatar(Base64.encode(userInfo.getAvatarFileData()));
        }
        return userInfo;
    }

    /**
     * 员工详细信息筛选列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<PsPersionVO> detailsSelectList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<PsPersionVO> voList = staffUserInfoMapper.detailsSelectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 校验批量导入参加人员信息
     * @param excelList
     * @return
     */
    @Override
    public Map<String, List<ParticipantsExcel>> checkUserData(List<ParticipantsExcel> excelList) {
        List<ParticipantsExcel> dataList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        List<ParticipantsExcel> errorList = new ArrayList<>();
        int i = 0;
        int errNum = 0;
        for (ParticipantsExcel excel : excelList) {
            i++;
            ParticipantsExcel errVO = new ParticipantsExcel();
            // 判断员工工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工工号是否正确
            PsPersionVO userInfo = staffUserInfoMapper.getSingleUserInfoById(excel.getEmplId());
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工姓名是否为空
            if (excel.getEmplName() == null || excel.getEmplName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工姓名为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工姓名与工号是否一致
            if (!userInfo.getEmplName().equals(excel.getEmplName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工姓名与工号不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断是否有重复员工工号
            String mapKey = excel.getEmplId();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的员工工号重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);

            if (errNum == 0) {
                ParticipantsExcel participants = new ParticipantsExcel();
                participants.setEmplId(excel.getEmplId());
                participants.setEmplName(userInfo.getEmplName());
                participants.setCompanyCode(userInfo.getCompanyCode());
                participants.setCompanyName(userInfo.getCompanyName());
                participants.setDepartmentCode(userInfo.getDepartmentCode());
                participants.setDepartmentName(userInfo.getDepartmentName());
                participants.setDeptCode(userInfo.getDepartmentCode());
                participants.setDeptName(userInfo.getDepartmentName());
                participants.setJobCode(userInfo.getJobCode());
                participants.setJobName(userInfo.getJobName());
                participants.setPostCode(userInfo.getJobCode());
                participants.setPostName(userInfo.getJobName());
                dataList.add(participants);
            }
        }
        Map<String, List<ParticipantsExcel>> map = new HashMap<>();
        map.put("errorList", errorList);
        map.put("dataList", dataList);
        return map;
    }

    /**
     * 员工工作台数据
     * @param loginUserId
     * @return
     */
    @Override
    public EmployeeRelateDataVO relatedData(String loginUserId){
        EmployeeRelateDataVO vo = new EmployeeRelateDataVO();
        Map<String,Object> query = new HashMap<>();
        query.put("userId",loginUserId);
        query.put("status",1);
        query.put("userStatus",1);
        vo.setTrainingInProgressNum(trainProgramsParticipantsService.getProgramsCountByQuery(query)); // 员工端-进行中的项目数量
        query.put("status",2);
        vo.setTrainingCompletedNum(trainProgramsParticipantsService.getProgramsCountByQuery(query)); // 员工端-已完成的项目数量
        vo.setObtainedCertificatesNum(trainEmployeeCertificateService.getCertificateCountByQuery(query));
        query.put("isDue",1);
        vo.setDueCertificatesNum(trainEmployeeCertificateService.getCertificateCountByQuery(query));
        return vo;
    }

    @Override
    public List<List<EmployeeTrainingScheduleVO>> trainingSchedule(Map<String, Object> params){
        String fieldTitle = "year";
        if (!params.containsKey(fieldTitle) || StringUtils.isEmpty(params.get(fieldTitle).toString())) {
            String year = new SimpleDateFormat("yyyy").format(new Date());
            params.put("year",year);
        }
        ArrayList<List<EmployeeTrainingScheduleVO>> list = new ArrayList<>();
        //循环查找用户每个月份的项目培训课表
        String month = null;
        for (int i = 1; i <= 12 ; i++) {
            if(i < 10){
                month = "0"+ i;
            }
            else{
                month = i + "";
            }
            params.put("queryMonth",params.get("year")+"-"+month);
            List<EmployeeTrainingScheduleVO> scheduleVOS = trainProgramsParticipantsService.programsMonthSchedule(params);
            list.add(scheduleVOS);
        }
        return list;
    }

    /**
     * 数据板数据(管理员工作台、BI报表培训分析)
     * @param query
     * @return
     */
    @Override
    public AdminRelateDataVO adminRelatedData(Map<String, Object> query){
        AdminRelateDataVO vo = new AdminRelateDataVO();
        Map<String, Object> mapParams = handleStatisParams(query);
        Integer totalTrainingNum = trainProgramsParticipantsService.getProgramsCountByQuery(mapParams); // 总培训数

        mapParams.put("status",1); // 培训进行中状态
        vo.setTrainingInProgressNum(trainProgramsParticipantsService.getProgramsCountByQuery(mapParams)); // 进行中的培训总数

        mapParams.put("status",2); // 培训已完成状态
        Integer completedNum = trainProgramsParticipantsService.getProgramsCountByQuery(mapParams); // 已完成的培训总数
        vo.setTrainingCompletedNum(completedNum);

        vo.setTrainingFinishedRate((int)Math.round((Double.parseDouble(completedNum.toString()) / totalTrainingNum * 100)) + "%"); // 培训完成率

        mapParams.put("status",2); // 培训已完成状态
        mapParams.put("userStatus",1); // 参训人员已报名状态
        Integer totalParticipantsNum = trainProgramsParticipantsService.getProgramsParticipantsCountByQuery(mapParams); // 已完成培训总人次
        vo.setTrainingTotalParticipantsNum(totalParticipantsNum);

        mapParams.put("status",2); // 培训已完成状态
        Float totalHoursNum = trainProgramsPlanService.getProgramsCourseHoursCountByQuery(mapParams); // 已完成培训总时长
        vo.setTrainingTotalHoursNum(totalHoursNum.toString());

        BigDecimal trainAvgHours = BigDecimal.ZERO;
        if (totalParticipantsNum != null && totalParticipantsNum > 0) {
            trainAvgHours = BigDecimal.valueOf(totalHoursNum).divide(BigDecimal.valueOf(totalParticipantsNum), 2, BigDecimal.ROUND_HALF_UP); // 人均培训时长
        }
        vo.setTrainAvghours(trainAvgHours.toString());

        mapParams.put("status", 2); // 培训已完成状态
        Float trainTotalFee = trainCostMapper.getSumCostByQuery(mapParams); // 已完成培训费用统计
        if (trainTotalFee != null) {
            vo.setTrainTotalFee(String.format("%.2f", trainTotalFee));
        }
        return vo;
    }

    /**
     * 管理员-首页-已授课类别分布
     * @param params
     * @return
     */
    @Override
    public List<StatisticalProportionVo> taughtTypeDistribution(Map<String, Object> params){
        //获取课程类别数据
        Map<String, Object> query = new HashMap<>();
        query.put("type",1);
        query.put("status",1);
        List<TrainConstantVO> courseTypeList = trainConstantService.selectList(query);
        LinkedList<StatisticalProportionVo> list = new LinkedList<>();
        Integer total = 0;
        //循环构建数据
        for (TrainConstantVO courseType : courseTypeList){
            params.put("category",courseType.getId());
            Integer count = trainProgramsPlanMapper.getCountByCourseCategory(params);
            StatisticalProportionVo vo = new StatisticalProportionVo();
            vo.setName(courseType.getName());
            vo.setNum(count);
            total += count;
            list.add(vo);
        }
        String rate = "0";
        for (StatisticalProportionVo vo : list){
            if(total != 0){
                rate = String.valueOf(vo.getNum() / total * 100);
            }
            vo.setRate(rate);
        }
        //头部插入总数
        StatisticalProportionVo totalVo = new StatisticalProportionVo();
        totalVo.setName("总计");
        totalVo.setNum(total);
        totalVo.setRate("100");
        list.addFirst(totalVo);
        return list;
    }

    /**
     * 管理员-首页-培训费用培训形式分布
     * @param params
     * @return
     */
    @Override
    public List<StatisticalProportionVo> trainingCostShapeDistribution(Map<String, Object> params){
        //获取各培训形式的费用
        params.put("trainShape",1);
        Float inTotal = trainCostMapper.getSumCostByQuery(params); // 已完成培训费用统计
        if(inTotal == null){
            inTotal = (float) 0;
        }
        params.put("trainShape",2);
        Float outTotal = trainCostMapper.getSumCostByQuery(params); // 已完成培训费用统计
        if(outTotal == null){
            outTotal = (float) 0;
        }
        float sumTotal = inTotal + outTotal;
        LinkedList<StatisticalProportionVo> list = new LinkedList<>();
        StatisticalProportionVo totalVo = new StatisticalProportionVo();
        totalVo.setRate("100.0");
        totalVo.setName("总计");
        totalVo.setNum(Math.round(sumTotal));
        list.add(totalVo);
        StatisticalProportionVo inVo = new StatisticalProportionVo();
        String inRate = "0";
        String outRate = "0";
        if(sumTotal != 0 ){
            inRate = String.valueOf(inTotal / sumTotal * 100);
            outRate = String.valueOf(outTotal / sumTotal * 100);
        }
        inVo.setRate(inRate);
        inVo.setName("内部");
        inVo.setNum(Math.round(inTotal));
        list.add(inVo);
        StatisticalProportionVo outVo = new StatisticalProportionVo();
        outVo.setRate(outRate);
        outVo.setName("外部");
        outVo.setNum(Math.round(outTotal));
        list.add(outVo);
        return list;
    }

    /**
     * 管理员-首页-培训费用职等分布
     * @param params
     * @return
     */
    @Override
    public List<StatisticalProportionVo> trainingCostLevelDistribution(Map<String, Object> params){
        LinkedList<StatisticalProportionVo> list = new LinkedList<>();
        //获取所有参训人员的职等分组数据
        List<Map<String,Object>> levelDataGroupList = trainCostMapper.getParticipantsLevelGroupList(params);
        //构造数据
        Map<String,StatisticalProportionVo> mapList = new LinkedHashMap<>();
        for (Map<String,Object> levelData : levelDataGroupList){
            String levelCode = levelData.get("levelCode").toString();
            String levelDescr = levelData.get("levelDescr").toString();
            float amount = Float.parseFloat(levelData.get("amount").toString());
            int totalParticipantsNum = Integer.parseInt(levelData.get("totalParticipantsNum").toString());
            int levelParticipantsNum = Integer.parseInt(levelData.get("levelParticipantsNum").toString());
            //计算该项目下此职等参训人员的总累计经费
            int cost = Math.round(amount / totalParticipantsNum * levelParticipantsNum);
            if(mapList.get(levelCode) == null){
                StatisticalProportionVo vo = new StatisticalProportionVo();
                vo.setName(levelDescr);
                vo.setNum(cost);
                mapList.put(levelCode,vo);
            }
            else{
                //累加
                int existCost = mapList.get(levelCode).getNum();
                mapList.get(levelCode).setNum(cost+existCost);
            }
        }
        for(Map.Entry<String,StatisticalProportionVo> entry : mapList.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    /**
     * 培训项目总数分布趋势(管理员-首页、BI培训分析-图数据)
     * @param params
     * @return
     */
    @Override
    public List<MonthlyVO> trainMonthlyTrend(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params);
        int yearly = Integer.parseInt(mapParams.get("year").toString());
        mapParams.keySet().removeIf(key -> key.equals("year")); // 去掉搜索条件year，因为月度统计培训项目是按照项目开始日期统计
        return handleMonthlyData(mapParams, yearly);
    }

    /**
     * BI培训分析-培训项目总数分布趋势-表数据
     * @param params
     * @return
     */
    @Override
    public List<MonthlyDeptVO> trainMonthlyTrendList(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params);
        int yearly = Integer.parseInt(mapParams.get("year").toString());
        mapParams.keySet().removeIf(key -> key.equals("year")); // 去掉搜索条件year，因为月度统计培训项目是按照项目开始日期统计

        // 统计当前组织的下一级组织每个月的项目数量
        List<MonthlyDeptVO> monthlyDeptList = new ArrayList<>();
        List<StaffDeptTerseVO> childDeptList = staffDepartmentService.getChildDeptById(mapParams.get("deptId").toString(), 1);
        for (StaffDeptTerseVO deptTerseVO : childDeptList) {
            MonthlyDeptVO monthlyDeptVO = new MonthlyDeptVO();
            monthlyDeptVO.setDeptId(deptTerseVO.getDeptId());
            monthlyDeptVO.setDeptName(deptTerseVO.getDeptName());
            mapParams.put("deptId", deptTerseVO.getDeptId()); // 替换组织编码
            monthlyDeptVO.setDeptMonthlyList(handleMonthlyData(mapParams, yearly));
            monthlyDeptList.add(monthlyDeptVO);
        }
        return monthlyDeptList;
    }

    /**
     * 处理每月培训项目数量数据
     * @param mapParams
     * @param yearly
     * @return
     */
    private List<MonthlyVO> handleMonthlyData(Map<String, Object> mapParams, Integer yearly) {
        int monthNum = Integer.parseInt(mapParams.get("monthNum").toString());
        List<MonthlyVO> monthlyList = new ArrayList<>();
        //循环统计当前组织的每个月的项目数量
        for (int i = 1; i <= monthNum ; i++) {
            MonthlyVO monthlyVO = new MonthlyVO();
            monthlyVO.setMonthly(i + "月");
            String month = (i < 10) ? ("0" + i) : String.valueOf(i); // 月份
            mapParams.put("queryMonth", yearly + "-" + month);
            monthlyVO.setMonthlyNum(trainProgramsParticipantsService.getProgramsCountByQuery(mapParams));  // 按月份统计每月的项目数量
            monthlyList.add(monthlyVO);
        }
        return monthlyList;
    }

    /**
     * BI培训分析-已授课程类别分布-图数据
     * @param params
     * @return
     */
    @Override
    public List<CourseCateVO> courseCateStatis(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params); // 处理参数
        return handleCourseCateData(mapParams);
    }

    /**
     * BI培训分析-已授课程类别分布-表数据
     * @param params
     * @return
     */
    @Override
    public List<CourseCateDeptVO> courseCateDeptStatis(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params);

        // 统计当前组织的下一级组织的已授课程类别数据
        List<CourseCateDeptVO> cateDeptList = new ArrayList<>();
        List<StaffDeptTerseVO> childDeptList = staffDepartmentService.getChildDeptById(mapParams.get("deptId").toString(), 1);
        for (StaffDeptTerseVO deptTerseVO : childDeptList) {
            CourseCateDeptVO courseCateDeptVO = new CourseCateDeptVO();
            courseCateDeptVO.setDeptId(deptTerseVO.getDeptId());
            courseCateDeptVO.setDeptName(deptTerseVO.getDeptName());
            mapParams.put("deptId", deptTerseVO.getDeptId()); // 替换组织编码
            courseCateDeptVO.setCourseCateList(handleCourseCateData(mapParams));
            cateDeptList.add(courseCateDeptVO);
        }
        return cateDeptList;
    }

    /**
     * 处理已授课程类别数据统计
     * @param mapParams
     * @return
     */
    private List<CourseCateVO> handleCourseCateData(Map<String, Object> mapParams) {
        // 获取系统中所有启用的课程类别
        Map<String, Object> query = new HashMap<>();
        query.put("type", 1);
        query.put("status", 1);
        List<TrainConstantVO> cateList = trainConstantService.selectList(query);

        // 统计所有已结束项目培训计划表中课程记录数
        mapParams.put("status", 2); // 项目状态为已结束
        Integer total = trainProgramsPlanMapper.getCountByCourseCategory(mapParams);

        List<CourseCateVO> list = new ArrayList<>();
        if (!cateList.isEmpty()) {
            for (TrainConstantVO cateVO : cateList) {
                mapParams.put("category", cateVO.getId());
                CourseCateVO statisVO = new CourseCateVO();
                statisVO.setCourseCateId(cateVO.getId().toString()); // 课程类别ID
                statisVO.setCourseCateName(cateVO.getName()); // 课程类别名称
                Integer statisNum = trainProgramsPlanMapper.getCountByCourseCategory(mapParams);
                statisVO.setStatisNum(statisNum.toString()); // 课程类别统计数量
                BigDecimal percent = BigDecimal.ZERO;
                if (!total.equals(0)) {
                    percent = BigDecimal.valueOf(statisNum * 100).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
                }
                statisVO.setStatisRatio(percent + "%"); // 课程类别占比
                list.add(statisVO);
            }
            // 插入合计数据
            CourseCateVO totalVO = new CourseCateVO();
            totalVO.setCourseCateName("合计");
            totalVO.setStatisNum(total.toString());
            list.add(totalVO);
        }
        return list;
    }

    /**
     * BI培训分析-培训费用占比(按培训形式)-图数据
     * @param params
     * @return
     */
    @Override
    public List<TrainShapeCostVO> costByTrainShape(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params); // 处理接收到的参数
        return handleCostTrainShapeData(mapParams);
    }

    /**
     * BI培训分析-培训费用占比(按培训形式)-表数据
     * @param params
     * @return
     */
    @Override
    public List<TrainShapeCostDeptVO> costDeptByTrainShape(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params);

        // 统计当前组织的下一级组织的培训费用占比(按培训形式)
        List<TrainShapeCostDeptVO> costDeptList = new ArrayList<>();
        List<StaffDeptTerseVO> childDeptList = staffDepartmentService.getChildDeptById(mapParams.get("deptId").toString(), 1);
        for (StaffDeptTerseVO deptTerseVO : childDeptList) {
            TrainShapeCostDeptVO costDeptVO = new TrainShapeCostDeptVO();
            costDeptVO.setDeptId(deptTerseVO.getDeptId());
            costDeptVO.setDeptName(deptTerseVO.getDeptName());
            mapParams.put("deptId", deptTerseVO.getDeptId()); // 替换组织编码
            costDeptVO.setTrainShapeCostList(handleCostTrainShapeData(mapParams));
            costDeptList.add(costDeptVO);
        }
        return costDeptList;
    }

    /**
     * 处理培训费用占比(按培训形式)数据
     * @param mapParams
     * @return
     */
    private List<TrainShapeCostVO> handleCostTrainShapeData(Map<String, Object> mapParams) {
        mapParams.put("status", 2); // 项目状态为已结束
        Float totalCost = trainCostMapper.getSumCostByQuery(mapParams); // 已结束项目总费用

        Map<Integer, String> trainShapeMap = TrainConstantTypeData.trainShapeMap(); // 获取培训形式数据

        List<TrainShapeCostVO> trainShapeCostList = new ArrayList<>();
        if (trainShapeMap.size() > 0) {
            for (int i = 1; i <= trainShapeMap.size(); i++) {
                mapParams.put("trainShape", i); // 培训方式
                TrainShapeCostVO costVO = new TrainShapeCostVO();
                costVO.setTrainShapeId(String.valueOf(i));
                costVO.setTrainShapeName(trainShapeMap.get(i));
                Float countCost = trainCostMapper.getSumCostByQuery(mapParams);
                costVO.setTotalFee(String.valueOf(countCost));
                BigDecimal percent = BigDecimal.ZERO;
                if (countCost != null && totalCost >= 0.000001) {
                    percent = BigDecimal.valueOf(countCost * 100).divide(BigDecimal.valueOf(totalCost), 2, BigDecimal.ROUND_HALF_UP);
                }
                costVO.setStatisRatio(percent + "%");
                trainShapeCostList.add(costVO);
            }
            // 插入合计数据
            TrainShapeCostVO totalVO = new TrainShapeCostVO();
            totalVO.setTrainShapeName("合计");
            totalVO.setTotalFee(totalCost.toString());
            trainShapeCostList.add(totalVO);
        }
        return trainShapeCostList;
    }

    /**
     * 培训费用占比(按职级)
     * @param params
     * @return
     */
    @Override
    public List<PosnGradeCostVO> costByPosnGrade(Map<String, Object> params) {
        Map<String, Object> mapParams = handleStatisParams(params); // 处理接收到的参数
        return handleCostByPosnGradeData(mapParams);
    }

    /**
     * 处理培训费用占比(按职级)数据
     * @param mapParams
     * @return
     */
    private List<PosnGradeCostVO> handleCostByPosnGradeData(Map<String, Object> mapParams) {
        mapParams.put("status", 2); // 项目状态为已结束
        Float totalCost = trainCostMapper.getSumCostByQuery(mapParams); // 已结束项目总费用

        List<PsPosnGradeVO> gradeList = staffPosnGradeService.selectList(null); // 职级数据
        List<PosnGradeCostVO> posnGradeCostList = new ArrayList<>();
        if (!gradeList.isEmpty()) {
            for (PsPosnGradeVO gradeVO : gradeList) {
                mapParams.put("gradeCode", gradeVO.getPosnGradeCode()); // 职级编码
                PosnGradeCostVO costVO = new PosnGradeCostVO();
                costVO.setPosnGradeCode(gradeVO.getPosnGradeCode());
                costVO.setPosnGradeName(gradeVO.getPosnGradeName());
                PosnGradeCostVO cntRes = trainDataService.countNumByGradeCode(mapParams); // 当前职级参训人员总人次和所用费用之和
                costVO.setPersonTime(cntRes.getPersonTime());
                costVO.setTotalFee(cntRes.getTotalFee());
                int countNum = Integer.parseInt(cntRes.getPersonTime()); // 当前职级参训人员参训人次
                float countCost = Float.parseFloat(cntRes.getTotalFee());  // 当前职级参训人员所用费用之和
                // 人均费用
                BigDecimal avgCost = BigDecimal.ZERO;
                if (countNum > 0) {
                    avgCost = BigDecimal.valueOf(countCost).divide(BigDecimal.valueOf(countNum), 2, BigDecimal.ROUND_HALF_UP);
                }
                costVO.setAvgFee(String.valueOf(avgCost));
                // 费用占比
                BigDecimal percent = BigDecimal.ZERO;
                if (countCost >= 0.000001 && totalCost != null && totalCost >= 0.000001) {
                    percent = BigDecimal.valueOf(countCost * 100).divide(BigDecimal.valueOf(totalCost), 2, BigDecimal.ROUND_HALF_UP);
                }
                costVO.setStatisRatio(percent + "%");
                posnGradeCostList.add(costVO);
            }
            // 插入合计数据
            PosnGradeCostVO totalVO = new PosnGradeCostVO();
            totalVO.setPosnGradeName("合计");
            totalVO.setTotalFee(String.valueOf(totalCost));
            posnGradeCostList.add(totalVO);
        }
        return posnGradeCostList;
    }

    /**
     * 处理统计方法传参
     * @param params
     * @return
     */
    private Map<String, Object> handleStatisParams(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        String fieldDeptId = "deptId";
        String deptId = "1000001"; // 默认组织-康尼机电
        if (params.containsKey(fieldDeptId) && !StringUtils.isEmpty(params.get(fieldDeptId).toString())) {
            deptId = params.get(fieldDeptId).toString();
        }
        map.put("deptId", deptId);

        Calendar ca = Calendar.getInstance();
        int currYear = ca.get(Calendar.YEAR); // 当前年份
        int monthNum = ca.get(Calendar.MONTH) + 1; // 当前月份值
        String fieldYear = "year";
        int yearly;
        if (!params.containsKey(fieldYear) || StringUtils.isEmpty(params.get(fieldYear).toString())) {
            yearly = currYear;
        } else {
            yearly = Integer.parseInt(params.get(fieldYear).toString()); // 传值年份
            if (yearly < currYear) {
                monthNum = 12;
            }
        }
        map.put("year", yearly);
        map.put("monthNum", monthNum);
        map.put("startTime", yearly + "-01-01");
        map.put("endTime", yearly + "-12-31");
        return map;
    }

    /**
     * BI系统培训相关类型字典
     * @return
     */
    @Override
    public List<TrainTypeBiDictVO> trainTypeBiDictList() {
        List<TrainTypeBiDictVO> trainTypeList = new ArrayList<>();

        // 获取系统中所有启用的课程类别
        Map<String, Object> query = new HashMap<>();
        query.put("type", 1);
        query.put("status", 1);
        List<TrainConstantVO> cateList = trainConstantService.selectList(query);
        for (TrainConstantVO constantVO : cateList) {
            TrainTypeBiDictVO trainTypeVO = new TrainTypeBiDictVO();
            trainTypeVO.setTypeName("TRAIN_COURSE_CATE");
            trainTypeVO.setTypeVal(String.valueOf(constantVO.getId()));
            trainTypeVO.setValName(constantVO.getName());
            trainTypeVO.setMemo("培训课程类别");
            trainTypeList.add(trainTypeVO);
        }

        Map<Integer, String> trainShapeMap = TrainConstantTypeData.trainShapeMap(); // 获取培训形式数据
        for (int i = 1; i <= trainShapeMap.size(); i++) {
            TrainTypeBiDictVO trainTypeVO = new TrainTypeBiDictVO();
            trainTypeVO.setTypeName("TRAIN_SHAPE");
            trainTypeVO.setTypeVal(String.valueOf(i));
            trainTypeVO.setValName(trainShapeMap.get(i));
            trainTypeVO.setMemo("培训形式");
            trainTypeList.add(trainTypeVO);
        }
        return trainTypeList;
    }
}
