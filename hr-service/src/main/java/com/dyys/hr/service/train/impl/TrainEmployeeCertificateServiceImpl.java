package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainEmployeeCertificateMapper;
import com.dyys.hr.dto.train.TrainEmployeeCertificateDTO;
import com.dyys.hr.entity.train.TrainEmployeeCertificate;
import com.dyys.hr.entity.train.excel.CertificateExcel;
import com.dyys.hr.entity.train.excel.CertificateListExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainEmployeeCertificateService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.AdminCertificateExpirationReminderListVO;
import com.dyys.hr.vo.train.EmplCertificateListVO;
import com.dyys.hr.vo.train.TrainEmployeeCertificatePageVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.entity.Condition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 员工证书表接口
 * @author WSJ
 */
@Service
@Slf4j
public class TrainEmployeeCertificateServiceImpl extends AbstractCrudService<TrainEmployeeCertificate, Long> implements TrainEmployeeCertificateService {
    @Autowired
    private TrainEmployeeCertificateMapper trainEmployeeCertificateMapper;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    @Override
    public PageInfo<TrainEmployeeCertificatePageVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainEmployeeCertificatePageVO> voList = trainEmployeeCertificateMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<AdminCertificateExpirationReminderListVO> certificateExpirationReminderList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<AdminCertificateExpirationReminderListVO> voList = trainEmployeeCertificateMapper.certificateExpirationReminderList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public Integer getCertificateCountByQuery(Map<String, Object> params){
        return trainEmployeeCertificateMapper.getCertificateCountByQuery(params);
    }

    /**
     * 员工获证记录
     * @param params
     * @return
     */
    @Override
    public List<EmplCertificateListVO> emplCertificateList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainEmployeeCertificateMapper.emplCertificateList(params);
    }

    /**
     * 证书数据导出列表
     * @param params
     * @return
     */
    @Override
    public List<CertificateListExcel> exportList(Map<String, Object> params){
        return trainEmployeeCertificateMapper.exportList(params);
    }

    /**
     * 创建证书
     * @param dto
     * @param loginUserId
     * @return
     */
    @Override
    public Long save(TrainEmployeeCertificateDTO dto, String loginUserId){
        /*TrainEmployeeCertificate queryEntity = new TrainEmployeeCertificate();
        queryEntity.setCertificateNo(dto.getCertificateNo());
        queryEntity.setEmplId(dto.getEmplId());
        TrainEmployeeCertificate selectOne = this.selectOne(queryEntity);
        if(selectOne != null){
            throw new BusinessException(ResultCode.ERROR,"相同编号证书已存在");
        }*/
        TrainEmployeeCertificate entity = new TrainEmployeeCertificate();
        BeanUtils.copyProperties(dto,entity);
        entity.setIsImport(0);
        entity.setCreateUser(loginUserId);
        entity.setCreateTime(System.currentTimeMillis()/1000);
        return this.insertSelective(entity);
    }


    /**
     * 编辑证书
     * @param dto
     * @param loginUserId
     * @return
     */
    @Override
    public Integer edit(TrainEmployeeCertificateDTO dto, String loginUserId){
        TrainEmployeeCertificate entity = new TrainEmployeeCertificate();
        BeanUtils.copyProperties(dto,entity);
        entity.setUpdateUser(loginUserId);
        entity.setUpdateTime(System.currentTimeMillis()/1000);
        return this.updateSelective(entity);
    }

    /**
     * 导入证书
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    public List<CertificateExcel> importCertificate(List<CertificateExcel> excelList, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 当前时间戳
        List<CertificateExcel> errorList = new ArrayList<>();
        List<TrainEmployeeCertificate> entityList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (CertificateExcel excel : excelList) {
            i++;
            CertificateExcel errVO = new CertificateExcel();
            // 判断员工工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工工号是否正确
            PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getEmplId());
            if (userInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号不存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工工号与员工姓名是否匹配
            if (!userInfo.getEmplName().equals(excel.getEmplName().trim())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号与员工姓名不匹配");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断证书名称是否为空
            if (excel.getCertificateName() == null || excel.getCertificateName().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，证书名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断发证机构名称是否为空
            if (excel.getIssuingAgencyName() == null || excel.getIssuingAgencyName().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，发证机构名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断获证日期是否为空
            if (excel.getStartDate() == null || excel.getStartDate().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，获证日期为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            Date startDate;
            try {
                SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy/M/d");
                Date startDateTmp = sdfStart.parse(excel.getStartDate());
                sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                String startDateStr = sdfStart.format(startDateTmp);
                startDate = java.sql.Date.valueOf(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，获证日期格式不正确");
            }
            Date endDate = null;
            if (excel.getEndDate() != null && !excel.getEndDate().trim().equals("")) {
                try {
                    SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy/M/d");
                    Date endDateTmp = sdfEnd.parse(excel.getEndDate());
                    sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
                    String endDateStr = sdfEnd.format(endDateTmp);
                    endDate = java.sql.Date.valueOf(endDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，到期日期格式不正确");
                }
            }
            // 判断员工证书信息是否有重复
            String mapKey = excel.getEmplId() + excel.getCertificateName() + excel.getIssuingAgencyName() + excel.getStartDate();
            if (checkMap.containsKey(mapKey) ) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的员工证书重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断是否系统中已存在
            TrainEmployeeCertificate certificateQuery = new TrainEmployeeCertificate();
            certificateQuery.setEmplId(excel.getEmplId());
            certificateQuery.setCertificateName(excel.getCertificateName());
            certificateQuery.setIssuingAgencyName(excel.getIssuingAgencyName());
            certificateQuery.setStartDate(startDate);
            TrainEmployeeCertificate certificateInfo = this.selectOne(certificateQuery);
            if (certificateInfo != null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录已存在系统中");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 若没有错误，则处理数据
            if (errNum == 0) {
                TrainEmployeeCertificate certificate = new TrainEmployeeCertificate();
                BeanUtils.copyProperties(excel, certificate);
                certificate.setEmplName(userInfo.getEmplName());
                certificate.setStartDate(startDate);
                certificate.setEndDate(endDate);
                certificate.setIsImport(1);
                certificate.setCreateUser(loginEmplId);
                certificate.setCreateTime(stamp);
                certificate.setUpdateUser(loginEmplId);
                certificate.setUpdateTime(stamp);
                entityList.add(certificate);
            }
        }
        if (errNum == 0) {
            this.insertBatchSelective(entityList);
        }
        return errorList;
    }

    /**
     * 校验证书数据的唯一性
     * @param dto
     * @return
     */
    @Override
    public String checkUniqueData(TrainEmployeeCertificateDTO dto) {
        Condition condition = new Condition(TrainEmployeeCertificate.class);
        if (dto.getId() == null) {
            condition.createCriteria().andEqualTo("emplId", dto.getEmplId())
                    .andEqualTo("certificateName", dto.getCertificateName())
                    .andEqualTo("issuingAgencyName", dto.getIssuingAgencyName())
                    .andEqualTo("startDate", dto.getStartDate());
        } else {
            condition.createCriteria().andNotEqualTo("id", dto.getId())
                    .andEqualTo("emplId", dto.getEmplId())
                    .andEqualTo("certificateName", dto.getCertificateName())
                    .andEqualTo("issuingAgencyName", dto.getIssuingAgencyName())
                    .andEqualTo("startDate", dto.getStartDate());
        }
        int res = trainEmployeeCertificateMapper.selectCountByCondition(condition);
        if (res > 0) {
            return "当前员工已拥有该证书，不可重复添加!";
        }
        return "";
    }
}