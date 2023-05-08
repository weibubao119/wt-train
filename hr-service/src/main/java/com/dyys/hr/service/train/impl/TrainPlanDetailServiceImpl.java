package com.dyys.hr.service.train.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainPlanDetailMapper;
import com.dyys.hr.dto.train.CompanyDTO;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.TrainBaseTeacher;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.TrainPlanDetail;
import com.dyys.hr.entity.train.excel.PlanDetailExcel;
import com.dyys.hr.entity.train.excel.PlanDetailExportExcel;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainBaseTeacherService;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.service.train.TrainPlanDetailService;
import com.dyys.hr.vo.common.OrgDeptVO;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.TrainPlanDetailExcelVO;
import com.dyys.hr.vo.train.TrainPlanDetailSelectVO;
import com.dyys.hr.vo.train.TrainPlanDetailVO;
import com.dyys.hr.vo.train.TrainPlanParticipantsVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TrainPlanDetailServiceImpl extends AbstractCrudService<TrainPlanDetail, Long> implements TrainPlanDetailService {
    @Autowired
    private TrainPlanDetailMapper trainPlanDetailMapper;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;
    @Autowired
    private IStaffJobService staffJobService;

    @Override
    public PageInfo<TrainPlanDetailSelectVO> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainPlanDetailSelectVO> voList = trainPlanDetailMapper.selectList(params);
        for (TrainPlanDetailSelectVO vo : voList){
            JSONArray objects = JSONUtil.parseArray(vo.getCoOrganizer());
            vo.setCoOrganizerList(JSONUtil.toList(objects, CompanyDTO.class));
        }
        return new PageInfo<>(voList);
    }

    /**
     * 培训计划导入模板下拉框数据
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();

        // 课程类别下拉项
        List<String> courseTypeList = trainConstantService.selectBoxList(1, "0");
        selectMap.put(3, courseTypeList);

        // 培训形式下拉项
        List<String> trainShapeList = new ArrayList<>();
        trainShapeList.add("1-内部培训");
        trainShapeList.add("2-外部培训");
        selectMap.put(4, trainShapeList);

        // 培训需求依据下拉项
        List<String> requirementsList = trainConstantService.selectBoxList(2, "0");
        selectMap.put(7, requirementsList);

        // 考核方法下拉项
        List<String> assessmentMethodList = trainConstantService.selectBoxList(3, "0");
        selectMap.put(12, assessmentMethodList);

        return selectMap;
    }

    /**
     * 培训计划导入数据处理
     * @param excelList
     * @return
     */
    @Override
    public TrainPlanDetailExcelVO handlePlanDetailExcel(List<PlanDetailExcel> excelList) {
        List<PlanDetailExcel> errorList = new ArrayList<>();
        List<TrainPlanDetailVO> dataList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        Date now = new Date();
        for (PlanDetailExcel excel : excelList) {
            i++;
            PlanDetailExcel errVO = new PlanDetailExcel();
            // 判断反馈人工号是否为空
            if (excel.getFeedbackUserId() == null || excel.getFeedbackUserId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getFeedbackUserId());
            // 判断反馈人工号是否正确
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断反馈人工号与姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getFeedbackUserName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，反馈人工号与反馈人姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否为空
            if (excel.getTrainName() == null || excel.getTrainName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训名称是否有重复
            String mapKey = excel.getTrainName();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的培训名称重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断培训形式是否为空
            if (excel.getTrainShapeName() == null || excel.getTrainShapeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择培训形式");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否正确
            String[] trainShapeName = excel.getTrainShapeName().split("-"); // 分出培训形式编号和名称
            int trainShape = Integer.parseInt(trainShapeName[0]);
            if (trainShape != 1 && trainShape != 2) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选培训形式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否为空
            if (excel.getCourseTypeName() == null || excel.getCourseTypeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择课程类别");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否正确
            String[] courseTypeName = excel.getCourseTypeName().split("-"); // 分出课程类别ID和名称
            int courseType = Integer.parseInt(courseTypeName[0]);
            TrainConstant courseTypeInfo = trainConstantService.getInfoBySblId(courseType, 1, "0");
            if (courseTypeInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，所选课程类别不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断受训对象是否为空
            if (excel.getTrainSubject() == null || excel.getTrainSubject().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，受训对象为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间是否为空
            if (excel.getTrainCompleteTime() == null || excel.getTrainCompleteTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断拟完成时间要不能小于今天
            Date completeTime;
            /*if (excel.getTrainCompleteTime().contains("00:00:00")) {
                completeTime = java.sql.Date.valueOf(excel.getTrainCompleteTime().replace(" 00:00:00", ""));
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                    Date dateTmp = sdf.parse(excel.getTrainCompleteTime());
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateStr = sdf.format(dateTmp);
                    completeTime = java.sql.Date.valueOf(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，拟完成时间格式不正确");
                }
            }*/
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                Date dateTmp = sdf.parse(excel.getTrainCompleteTime());
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(dateTmp);
                completeTime = java.sql.Date.valueOf(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间格式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            /*if (completeTime.before(now)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，拟完成时间小于今天日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }*/
            // 判断培训需求依据是否正确
            Integer trainRequirements = null;
            String requirementsName = null;
            if (excel.getTrainRequirementsName() != null && !excel.getTrainRequirementsName().trim().equals("")) {
                String[] trainRequirementsName = excel.getTrainRequirementsName().split("-"); // 分出培训需求依据ID和名称
                trainRequirements = Integer.parseInt(trainRequirementsName[0]);
                TrainConstant requirementsInfo = trainConstantService.getInfoBySblId(trainRequirements, 2, "0");
                if (requirementsInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，所选培训需求依据不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                requirementsName = requirementsInfo.getName();
            }
            // 判断参训人数是否为空
            if (excel.getParticipantsNum() == null || excel.getParticipantsNum() <= 0) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，参训人数必须大于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断讲师编号是否正确
            Long teacherId = null;
            String teacherName = null;
            if (excel.getTeacherNumber() != null && !excel.getTeacherNumber().trim().equals("")) {
                TrainBaseTeacher teacherInfo = trainBaseTeacherService.queryInfoByNumber(excel.getTeacherNumber());
                if (teacherInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，培训讲师编号不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                teacherId = teacherInfo.getId();
                teacherName = teacherInfo.getName();
            }
            // 判断考核方法是否正确
            Integer assessmentMethod = null;
            String assessmentMethodName = null;
            if (excel.getAssessmentMethodName() != null && !excel.getAssessmentMethodName().trim().equals("")) {
                String[] methodName = excel.getAssessmentMethodName().split("-"); // 分出考核方法ID和名称
                assessmentMethod = Integer.parseInt(methodName[0]);
                TrainConstant methodInfo = trainConstantService.getInfoBySblId(assessmentMethod, 3, "0");
                if (methodInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，所选考核方法不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                assessmentMethodName = methodInfo.getName();
            }
            // 判断经费预算是否为空
            if (excel.getOutlay() == null || excel.getOutlay().compareTo(BigDecimal.ZERO) == -1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，经费预算不能小于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 若协办单位编码不为空，判断协办单位编码是否正确
            List<OrgDeptVO> coOrganizerList = new ArrayList<>();
            if (excel.getOrgDeptIds() != null && !excel.getOrgDeptIds().trim().equals("")) {
                String[] deptIds = excel.getOrgDeptIds().split(",");
                StringBuilder deptBld = new StringBuilder();
                for (int j = 0; j < deptIds.length; j++) {
                    int jNum = j + 1;
                    String deptId = deptIds[j]; // 组织单位编码
                    StaffDepartment deptInfo = staffDepartmentService.selectById(deptId);
                    if (deptInfo == null) {
                        deptBld.append("第" + jNum + "个协办单位编码不正确\n");
                        continue;
                    }
                    OrgDeptVO orgDeptVO = new OrgDeptVO();
                    orgDeptVO.setDeptId(deptId);
                    orgDeptVO.setDeptPathName(deptInfo.getDescr());
                    coOrganizerList.add(orgDeptVO);
                }
                String deptIdErrMsg = deptBld.toString();
                if (!deptIdErrMsg.equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录协办单位编码有误：" + deptIdErrMsg);
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }

            // 若参训人员编码不为空，判断参训人员编码是否正确
            List<TrainPlanParticipantsVO> participantsList = new ArrayList<>();
            if (excel.getParticipants() != null && !excel.getParticipants().trim().equals("")) {
                String[] parts = excel.getParticipants().split(",");
                StringBuilder emplBld = new StringBuilder();
                for (int ii = 0; ii < parts.length; ii++) {
                    int iiNum = ii + 1;
                    String userId = parts[ii]; // 参训人员工号
                    StaffJob staffInfo = staffJobService.selectById(userId);
                    if (staffInfo == null) {
                        emplBld.append("第" + iiNum + "个参训人员工号不正确；");
                        continue;
                    }
                    TrainPlanParticipantsVO partVO = new TrainPlanParticipantsVO();
                    partVO.setUserId(userId);
                    partVO.setUserName(staffInfo.getEmplName());
                    partVO.setCompanyCode(staffInfo.getCompany());
                    partVO.setCompanyName(staffInfo.getCompanyDescr());
                    partVO.setDepartmentCode(staffInfo.getDeptId());
                    partVO.setDepartmentName(staffInfo.getDeptDescr());
                    partVO.setJobCode(staffInfo.getPostCode());
                    partVO.setJobName(staffInfo.getPostDescr());
                    participantsList.add(partVO);
                }
                String emplIdErrMsg = emplBld.toString();
                if (!emplIdErrMsg.equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录参训人员工号有误：" + emplIdErrMsg);
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }

            if (errNum == 0) {
                TrainPlanDetailVO vo = new TrainPlanDetailVO();
                BeanUtils.copyProperties(excel,vo);
                vo.setId(null);
                vo.setPlanId(null);
                vo.setCompanyCode(userInfo.getCompanyCode());
                vo.setCompanyName(userInfo.getCompanyName());
                vo.setDepartmentCode(userInfo.getDepartmentCode());
                vo.setDepartmentName(userInfo.getDepartmentName());
                vo.setDemandType(0);
                vo.setDemandTypeName("计划培训");
                vo.setCourseType(courseType);
                vo.setCourseTypeName(courseTypeInfo.getName());
                vo.setTrainShape(trainShape);
                vo.setTrainShapeName(trainShape == 1 ? "内部培训" : "外部培训");
                vo.setTrainCompleteTime(completeTime);
                vo.setTrainRequirements(trainRequirements);
                vo.setTrainRequirementsName(requirementsName);
                vo.setTeacherId(teacherId);
                vo.setTeacherName(teacherName);
                vo.setAssessmentMethod(assessmentMethod);
                vo.setAssessmentMethodName(assessmentMethodName);
                if (!coOrganizerList.isEmpty()) {
                    vo.setCoOrganizerList(coOrganizerList);
                }
                if (!participantsList.isEmpty()) {
                    vo.setParticipantsList(participantsList);
                }
                dataList.add(vo);
            }
        }
        TrainPlanDetailExcelVO excelVO = new TrainPlanDetailExcelVO();
        excelVO.setErrorList(errorList);
        excelVO.setDataList(dataList);
        return excelVO;
    }

    /**
     * 需求汇总导出培训计划数据
     * @param planId
     * @return
     */
    @Override
    public List<PlanDetailExportExcel> exportListByPlanId(Long planId) {
        return trainPlanDetailMapper.exportListByPlanId(planId);
    }
}