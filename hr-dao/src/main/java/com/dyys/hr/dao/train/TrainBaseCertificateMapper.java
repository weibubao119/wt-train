package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainBaseCertificateDTO;
import com.dyys.hr.entity.train.TrainBaseCertificate;
import com.dyys.hr.vo.train.TrainBaseCertificateVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 证书表接口
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Repository
public interface TrainBaseCertificateMapper extends ICrudMapper<TrainBaseCertificate> {
    List<TrainBaseCertificateVO> pageList(Map<String, Object> params);

    TrainBaseCertificateVO selectByCateId(Map<String, Object> params);

    TrainBaseCertificateVO selectByCertificateNo(@Param("certificateNo") String certificateNo);
}