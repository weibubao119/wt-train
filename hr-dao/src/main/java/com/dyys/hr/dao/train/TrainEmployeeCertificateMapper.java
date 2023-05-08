package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainEmployeeCertificate;
import com.dyys.hr.entity.train.excel.CertificateListExcel;
import com.dyys.hr.vo.train.AdminCertificateExpirationReminderListVO;
import com.dyys.hr.vo.train.EmplCertificateListVO;
import com.dyys.hr.vo.train.TrainEmployeeCertificatePageVO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainEmployeeCertificateMapper extends ICrudMapper<TrainEmployeeCertificate> {
    List<TrainEmployeeCertificatePageVO> pageList(Map<String, Object> params);

    List<AdminCertificateExpirationReminderListVO> certificateExpirationReminderList(Map<String, Object> params);

    Integer getCertificateCountByQuery(Map<String, Object> params);

    List<EmplCertificateListVO> emplCertificateList(@ApiIgnore @RequestParam Map<String, Object> params);

    List<CertificateListExcel> exportList(Map<String, Object> params);
}