package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseSiteMapper;
import com.dyys.hr.dto.train.TrainBaseSiteDTO;
import com.dyys.hr.entity.staff.StaffCompany;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.train.TrainBaseSite;
import com.dyys.hr.entity.train.excel.TrainSiteExcel;
import com.dyys.hr.service.staff.IStaffCompanyService;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.service.train.TrainBaseSiteService;
import com.dyys.hr.service.train.TrainProgramsCourseService;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.dyys.hr.vo.train.TrainBaseSiteVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 场地接口
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Service
@Slf4j
public class TrainBaseSiteServiceImpl extends AbstractCrudService<TrainBaseSite, Integer> implements TrainBaseSiteService {
    @Autowired
    private TrainBaseSiteMapper trainBaseSiteMapper;
    @Autowired
    private TrainProgramsCourseService trainProgramsCourseService;
    @Autowired
    private IStaffCompanyService iStaffCompanyService;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;

    @Override
    public PageInfo<TrainBaseSiteVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainBaseSiteVO> dtoList = trainBaseSiteMapper.pageList(params);
        return new PageInfo<>(dtoList);
    }


    @Override
    public TrainBaseSiteDTO selectBySiteId(Integer siteId){
        return trainBaseSiteMapper.selectBySiteId(siteId);
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainBaseSiteMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 保存校验场地数据
     * @param trainBaseSiteDTO
     * @param checkType
     * @return
     */
    @Override
    public String checkData(TrainBaseSiteDTO trainBaseSiteDTO, String checkType) {
        Integer siteId = null;
        if (checkType.equals("update")) {
            siteId = trainBaseSiteDTO.getId();
        }
        int nameCount = trainBaseSiteMapper.selectCountByName(trainBaseSiteDTO.getSiteName(), siteId);
        if (nameCount > 0) {
            return "场地名称已存在，请更换";
        }
        return "";
    }

    /**
     * 场地详情-场地使用情况
     * @param params
     * @return
     */
    @Override
    public PageInfo<TrainBaseSiteUsageVO> usageList(Map<String, Object> params) {
        return trainProgramsCourseService.siteUsageList(params);
    }

    /**
     * 导入培训场地
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    public List<TrainSiteExcel> importSite(List<TrainSiteExcel> excelList, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 当前时间戳
        List<TrainSiteExcel> errorList = new ArrayList<>();
        List<TrainBaseSite> siteList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (TrainSiteExcel excel : excelList) {
            i++;
            TrainSiteExcel errVO = new TrainSiteExcel();
            // 判断场地名称是否为空
            if (excel.getSiteName() == null || excel.getSiteName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,场地名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断场地名称是否已存在
            int nameCount = trainBaseSiteMapper.selectCountByName(excel.getSiteName(), null);
            if (nameCount > 0) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,场地名称已存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断导入数据是否有重复
            String mapKey = excel.getSiteName();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的场地名称重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断公司编码是否为空
            if (excel.getDeptId() == null || excel.getDeptId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,组织编码为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断公司编码是否正确
            StaffDepartment deptInfo = staffDepartmentService.selectById(excel.getDeptId());
            if (deptInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,组织编码不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断公司名称是否为空
            if (excel.getDeptName() == null || excel.getDeptName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,组织名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断公司编码与公司名称是否匹配
            if (!deptInfo.getDescr().equals(excel.getDeptName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,组织编码与名称不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断场地联系人姓名是否为空
            if (excel.getPrincipal() == null || excel.getPrincipal().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,负责人姓名为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断场地联系电话是否为空
            if (excel.getContactPhone() == null || excel.getContactPhone().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,联系电话为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断可容纳人数是否为空
            if (excel.getMaxCapacity() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,可容纳人数为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断场地地址是否为空
            if (excel.getAddress() == null || excel.getAddress().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录,场地地址为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 若没有错误，则处理数据
            if (errNum == 0) {
                TrainBaseSite baseSite = new TrainBaseSite();
                BeanUtils.copyProperties(excel, baseSite);
                baseSite.setIsImport(1);
                baseSite.setIsDelete(0);
                baseSite.setCreateUser(loginEmplId);
                baseSite.setCreateTime(stamp);
                baseSite.setUpdateUser(loginEmplId);
                baseSite.setUpdateTime(stamp);
                siteList.add(baseSite);
            }
        }
        if (errNum == 0) {
            this.insertBatchSelective(siteList);
        }
        return errorList;
    }

    /**
     * 导出培训场地
     * @param params
     * @return
     */
    @Override
    public List<TrainSiteExcel> exportSite(Map<String, Object> params) {
        return trainBaseSiteMapper.exportSiteList(params);
    }
}