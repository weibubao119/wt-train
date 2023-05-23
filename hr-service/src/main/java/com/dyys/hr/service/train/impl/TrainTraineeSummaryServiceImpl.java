package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainEmployeeCertificateMapper;
import com.dyys.hr.dao.train.TrainTraineeSummaryMapper;
import com.dyys.hr.entity.train.TrainEmployeeCertificate;
import com.dyys.hr.entity.train.TrainPrograms;
import com.dyys.hr.entity.train.TrainProgramsParticipants;
import com.dyys.hr.entity.train.TrainTraineeSummary;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainEmployeeCertificateService;
import com.dyys.hr.service.train.TrainProgramsParticipantsService;
import com.dyys.hr.service.train.TrainProgramsService;
import com.dyys.hr.service.train.TrainTraineeSummaryService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.TrainTraineeSummaryVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class TrainTraineeSummaryServiceImpl extends AbstractCrudService<TrainTraineeSummary, Long> implements TrainTraineeSummaryService {
    @Autowired
    private TrainTraineeSummaryMapper trainTraineeSummaryMapper;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;
    @Autowired
    private TrainEmployeeCertificateMapper trainEmployeeCertificateMapper;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    @Override
    public PageInfo<TrainTraineeSummaryVO> traineePageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainTraineeSummaryVO> voList = trainTraineeSummaryMapper.traineePageList(params);
        return new PageInfo<>(voList);
    }



    @Override
    public String getProgramsAverageResult(Map<String, Object> params){
        return trainTraineeSummaryMapper.getProgramsAverageResult(params);
    }

    /**
     * Excel培训总结模板下拉选项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();

        // 是否获证下拉项
        List<String> flagList = new ArrayList<>();
        flagList.add("0");
        flagList.add("1");
        selectMap.put(3, flagList);

        return selectMap;
    }

    /**
     * 导入培训总结数据
     * @param excelList
     * @param programsId
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SummaryExcel> importExl(List<SummaryExcel> excelList, Long programsId, String loginEmplId) {
        List<SummaryExcel> errorList = new ArrayList<>(); // 错误数据
        List<TrainTraineeSummary> tsEntityList = new ArrayList<>();
        List<TrainEmployeeCertificate> certificateAddList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<Integer, TrainEmployeeCertificate> upMap = new HashMap<>();
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        TrainPrograms programsInfo = trainProgramsService.selectById(programsId);
        int i = 0;
        int errNum = 0;
        for (SummaryExcel excel : excelList) {
            i++;
            SummaryExcel errVO = new SummaryExcel();
            // 判断反馈人工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，员工工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断员工是否报名参训该项目
            TrainProgramsParticipants participantsInfo = trainProgramsParticipantsService.getInfo(programsId, excel.getEmplId());
            if (participantsInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，该员工未报名参加该项目培训");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            String mapKey = excel.getEmplId();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录的员工工号重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断培训总成绩是否为空
            if (excel.getTotalScore() == null || excel.getTotalScore().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训总成绩为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断是否选择获证标识
            if (excel.getIsObtain() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择是否获证");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断获证标识是否正确
            if (excel.getIsObtain() != 0 && excel.getIsObtain() != 1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，请选择正确的获证标识");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断证书信息
            if (excel.getIsObtain() == 1) {
                if (excel.getCertificateName() == null || excel.getCertificateName().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，证书名称为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (excel.getIssuingAgencyName() == null || excel.getIssuingAgencyName().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，发证机构名称为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
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
                // 判断该员工是否已存在相同的证书信息
                TrainEmployeeCertificate certificateQuery = new TrainEmployeeCertificate();
                certificateQuery.setEmplId(excel.getEmplId());
                certificateQuery.setCertificateName(excel.getCertificateName());
                certificateQuery.setIssuingAgencyName(excel.getIssuingAgencyName());
                certificateQuery.setStartDate(startDate);
                certificateQuery.setIsImport(0);
                TrainEmployeeCertificate certificateInfo = trainEmployeeCertificateService.selectOne(certificateQuery);
                PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getEmplId());
                TrainEmployeeCertificate certificate = new TrainEmployeeCertificate();
                certificate.setEmplId(excel.getEmplId());
                certificate.setEmplName(userInfo.getEmplName());
                certificate.setCertificateName(excel.getCertificateName());
                certificate.setCertificateNo(excel.getCertificateNo());
                certificate.setCertificateLevel(excel.getCertificateLevel());
                certificate.setIssuingAgencyName(excel.getIssuingAgencyName());
                certificate.setStartDate(startDate);
                certificate.setEndDate(endDate);
                certificate.setTrainName(programsInfo.getTrainName());
                certificate.setTrainNumber(programsInfo.getNumber());
                certificate.setIsImport(1);
                certificate.setUpdateUser(loginEmplId);
                certificate.setUpdateTime(stamp);
                if (certificateInfo == null) {
                    // 需要新增证书数据
                    certificate.setCreateUser(loginEmplId);
                    certificate.setCreateTime(stamp);
                    certificateAddList.add(certificate);
                } else {
                    // 需要更新证书数据
                    certificate.setId(certificateInfo.getId());
                    upMap.put(i, certificate);
                }
            }
            if (errNum == 0) {
                TrainTraineeSummary summary = new TrainTraineeSummary();
                summary.setProgramsId(programsId);
                summary.setUserId(excel.getEmplId());
                summary.setTotalScore(excel.getTotalScore());
                summary.setIsObtain(excel.getIsObtain());
                summary.setSourceType(10);
                summary.setCreateUser(loginEmplId);
                summary.setCreateTime(stamp);
                summary.setUpdateUser(loginEmplId);
                summary.setUpdateTime(stamp);
                tsEntityList.add(summary);
            }
        }
        if (errNum == 0) {
            // 删除原来导入的培训总结
            Condition summaryCondition = new Condition(TrainTraineeSummary.class);
            summaryCondition.createCriteria().andEqualTo("programsId", programsId);
            trainTraineeSummaryMapper.deleteByCondition(summaryCondition);

            // 删除导入与此项目相关的证书信息
            Condition certificateCondition = new Condition(TrainEmployeeCertificate.class);
            certificateCondition.createCriteria().andEqualTo("trainNumber", programsInfo.getNumber()).andEqualTo("isImport", 1);
            trainEmployeeCertificateMapper.deleteByCondition(certificateCondition);

            // 生成培训总结数据
            if(!tsEntityList.isEmpty()){
                this.insertBatchSelective(tsEntityList);
            }

            // 生成证书数据
            if(!certificateAddList.isEmpty()){
                trainEmployeeCertificateService.insertBatchSelective(certificateAddList);
            }

            // 更新证书数据
            if (!upMap.isEmpty()) {
                for (Map.Entry<Integer, TrainEmployeeCertificate> entry : upMap.entrySet()) {
                    TrainEmployeeCertificate certificateEntity = entry.getValue();
                    trainEmployeeCertificateService.updateSelective(certificateEntity);
                }
            }
        }
        return errorList;
    }

}