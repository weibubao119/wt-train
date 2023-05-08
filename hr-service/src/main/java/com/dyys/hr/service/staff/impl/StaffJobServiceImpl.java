package com.dyys.hr.service.staff.impl;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffJobMapper;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.vo.common.PsDictVO;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.EmplInfoVO;
import com.dyys.hr.vo.train.StudentFilesPageVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职务数据表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffJobServiceImpl extends AbstractCrudService<StaffJob, String> implements IStaffJobService {
    @Autowired
    private StaffJobMapper staffJobMapper;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    /**
     * 员工字典列表
     * @param params
     * @return
     */
    @Override
    public List<PsDictVO> employeeDictList(Map<String, Object> params) {
        return staffJobMapper.employeeDictList(params);
    }

    /**
     * 学员档案分页列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<StudentFilesPageVO> studentsList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<StudentFilesPageVO> voList = staffJobMapper.studentsList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 学员基本信息
     * @param params
     * @return
     */
    @Override
    public EmplInfoVO emplInfo(Map<String, Object> params){
        EmplInfoVO userInfo = staffJobMapper.emplInfo(params);
        if(userInfo != null && userInfo.getAvatarFileData() != null){
            userInfo.setAvatar(Base64.encode(userInfo.getAvatarFileData()));
        }
        return userInfo;
    }

    /**
     * 员工职务信息
     * @param emplId
     * @return
     */
    @Override
    public StaffJob getInfoByEmplId(String emplId) {
        StaffJob staffJob = new StaffJob();
        staffJob.setEmplId(emplId);
        return selectOne(staffJob);
    }

    /**
     * excel导入数据处理
     * @param excelList
     * @return
     */
    @Override
    public Map<String, List<ParticipantsExcel>> importExl(List<ParticipantsExcel> excelList) {
        List<ParticipantsExcel> errorList = new ArrayList<>();
        List<ParticipantsExcel> dataList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (ParticipantsExcel excel : excelList) {
            i++;
            ParticipantsExcel errVO = new ParticipantsExcel();
            // 判断反馈人工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getEmplId());
            // 判断反馈人工号是否正确
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
            // 判断反馈人工号和姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getEmplName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号和员工姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断导入数据是否有重复
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
     * 基础字典下拉选项
     * @param typeName
     * @return
     */
    @Override
    public List<String> selectDictBoxList(String typeName) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("typeName", typeName);
        List<PsDictVO> dictList = staffJobMapper.employeeDictList(queryMap);
        List<String> choiceList = new ArrayList<>();
        if (!dictList.isEmpty()) {
            for (PsDictVO vo : dictList) {
                choiceList.add(vo.getDictCode() + "-" + vo.getDictName());
            }
        }
        return choiceList;
    }
}
