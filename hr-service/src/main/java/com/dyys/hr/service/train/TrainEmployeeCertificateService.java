package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainEmployeeCertificateDTO;
import com.dyys.hr.entity.train.TrainEmployeeCertificate;
import com.dyys.hr.entity.train.excel.CertificateExcel;
import com.dyys.hr.entity.train.excel.CertificateListExcel;
import com.dyys.hr.vo.train.AdminCertificateExpirationReminderListVO;
import com.dyys.hr.vo.train.EmplCertificateListVO;
import com.dyys.hr.vo.train.TrainEmployeeCertificatePageVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainEmployeeCertificateService extends ICrudService<TrainEmployeeCertificate, Long> {
    /**
     * 证书列表
     * @param params
     * @return
     */
    PageInfo<TrainEmployeeCertificatePageVO> pageList(Map<String, Object> params);

    PageInfo<AdminCertificateExpirationReminderListVO> certificateExpirationReminderList(Map<String, Object> params);

    //获取证书数量统计
    Integer getCertificateCountByQuery(Map<String, Object> params);

    /**
     * 员工获证记录
     * @param params
     * @return
     */
    List<EmplCertificateListVO> emplCertificateList(Map<String, Object> params);

    /**
     * 证书数据导出列表
     * @param params
     * @return
     */
    List<CertificateListExcel> exportList(Map<String, Object> params);

    /**
     * 创建证书
     * @param dto
     * @param loginUserId
     * @return
     */
    Long save(TrainEmployeeCertificateDTO dto, String loginUserId);

    /**
     * 编辑证书
     * @param dto
     * @param loginUserId
     * @return
     */
    Integer edit(TrainEmployeeCertificateDTO dto, String loginUserId);

    /**
     * 导入证书
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<CertificateExcel> importCertificate(List<CertificateExcel> excelList, String loginEmplId);

    /**
     * 校验证书数据的唯一性
     * @param dto
     * @return
     */
    String checkUniqueData(TrainEmployeeCertificateDTO dto);
}