package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainBaseCertificateDTO;
import com.dyys.hr.entity.train.TrainBaseCertificate;
import com.dyys.hr.vo.train.TrainBaseCertificateVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 
 * 证书表接口实现
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
public interface TrainBaseCertificateService extends ICrudService<TrainBaseCertificate, Integer> {
    PageInfo<TrainBaseCertificateVO> pageList(Map<String, Object> params);

    TrainBaseCertificateDTO selectByCateId(Map<String, Object> params);

    TrainBaseCertificateVO selectByCertificateNo(String certificateNo);

//
//    /**
//     * 保存课程管理和附件信息
//     * @param trainBaseCertificateVO
//     * @throws Exception
//     */
//    void saveByTrainBaseCertificateVO(TrainBaseCertificateVO trainBaseCertificateVO, UserInfoVo user) throws Exception;
//
//    /**
//     * 更新培训课程的相关信息
//     * @param trainBaseCertificateVO
//     * @throws Exception
//     */
//    void updateByTrainBaseCertificateVO(Long id,TrainBaseCertificateVO trainBaseCertificateVO,UserInfoVo user) throws Exception;



}