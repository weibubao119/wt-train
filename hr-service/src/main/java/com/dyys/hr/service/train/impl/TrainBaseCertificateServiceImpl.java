package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCertificateMapper;
import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TrainBaseCertificateDTO;
import com.dyys.hr.entity.train.TrainBaseCertificate;
import com.dyys.hr.service.train.TrainBaseCertificateService;
import com.dyys.hr.vo.train.TrainBaseCertificateVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 证书表接口
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Service
@Slf4j
public class TrainBaseCertificateServiceImpl extends AbstractCrudService<TrainBaseCertificate, Integer> implements TrainBaseCertificateService {
    @Autowired
    private TrainBaseCertificateMapper trainBaseCertificateMapper;

    @Override
    public PageInfo<TrainBaseCertificateVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainBaseCertificateVO> voList = trainBaseCertificateMapper.pageList(params);
        return new PageInfo<>(voList);
    }
    @Override
    public TrainBaseCertificateDTO selectByCateId(Map<String, Object> params){
        TrainBaseCertificateVO vo = trainBaseCertificateMapper.selectByCateId(params);
        TrainBaseCertificateDTO dto = new TrainBaseCertificateDTO();
        BeanUtils.copyProperties(vo,dto);
        JSONArray objects = JSONUtil.parseArray(vo.getFileList());
        dto.setFileList(JSONUtil.toList(objects,FileDTO.class));
        return dto;
    }

    @Override
    public TrainBaseCertificateVO selectByCertificateNo(String certificateNo){
        return trainBaseCertificateMapper.selectByCertificateNo(certificateNo);
    }




















//
//    /**
//     * 保存课程管理和附件信息
//     * @param trainBaseCertificateVO
//     * @throws Exception
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void saveByTrainBaseCertificateVO(TrainBaseCertificateVO trainBaseCertificateVO, UserInfoVo user) throws Exception{
//        TrainBaseCertificate trainBaseCertificate = new TrainBaseCertificate();
//        BeanUtils.copyProperties(trainBaseCertificateVO,trainBaseCertificate);
//        //TODO 保存创建人和创建时间
//        trainBaseCertificate.setCreateTime(System.currentTimeMillis()/1000);
//        trainBaseCertificate.setCreateUser(user.getUserId());
//        trainBaseCertificate.setCompanyId(user.getOrganizationId());
//
//        /** 设置唯一的证书编号 **/
//        trainBaseCertificate.setCertificateNo(noUtils.createNo(Constant.TRAIN_CERTIFICATE_NO_PREFIX));
//        trainBaseCertificateMapper.insertSelective(trainBaseCertificate);
//
//        /** 保存课程附件信息 **/
//        List<MultipartFile> fileList = trainBaseCertificateVO.getFiles();
//        if(CollectionUtil.isNotEmpty(fileList)){
//            attachmentService.saveByFileList(FileBusTypeEnum.TRAIN_CERTIFICATE, trainBaseCertificate.getId(),
//                    trainBaseCertificateVO.getFiles());
//        }
//    }

//    /**
//     * 更新培训课程的相关信息
//     * @param trainBaseCertificateVO
//     * @throws Exception
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateByTrainBaseCertificateVO(Long id,TrainBaseCertificateVO trainBaseCertificateVO,UserInfoVo user) throws Exception{
//        /** 更新讲师的基本信息表 **/
//        TrainBaseCertificate trainBaseCertificate = new TrainBaseCertificate();
//        BeanUtils.copyProperties(trainBaseCertificateVO,trainBaseCertificate);
//        //TODO 保存更新人和更新日期
//        trainBaseCertificate.setUpdateTime(System.currentTimeMillis()/1000);
//        trainBaseCertificate.setUpdateUser(user.getUserId());
//        trainBaseCertificate.setId(id);
//        this.updateSelective(trainBaseCertificate);
//
//        if(trainBaseCertificateVO.getDeleteFileIds() != null){
//            for(Long deleteId : trainBaseCertificateVO.getDeleteFileIds()){
//                attachmentService.deleteById(deleteId);
//            }
//        }
//
//        /** 判断是否存在，存在是删除掉附件表信息 **/
//        if(trainBaseCertificateVO.getFiles() != null){
////            attachmentService.deleteByTypeAndId(FileBusTypeEnum.TRAIN_CERTIFICATE,trainBaseCertificate.getId());
//            /** 保存文件信息 **/
//            attachmentService.saveByFileList(FileBusTypeEnum.TRAIN_CERTIFICATE, trainBaseCertificate.getId(),
//                    trainBaseCertificateVO.getFiles());
//        }
//    }


}