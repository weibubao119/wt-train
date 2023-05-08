package com.dyys.hr.service.train.impl;

import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAppraisePersonMapper;
import com.dyys.hr.entity.train.TrainAppraisePerson;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.service.train.TrainAppraisePersonService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.train.TrainAppraisePersonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainAppraisePersonServiceImpl extends AbstractCrudService<TrainAppraisePerson, Long> implements TrainAppraisePersonService {
    @Autowired
    private TrainAppraisePersonMapper trainAppraisePersonMapper;
    @Autowired
    private TrainNoticeService trainNoticeService;

    public List<TrainAppraisePersonVO> getListByQuery(Map<String,Object> params){
        return trainAppraisePersonMapper.getListByQuery(params);
    }

    @Override
    public void deleteByParams(Map<String,Object> params){
        trainAppraisePersonMapper.deleteByParams(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer remove(Long id){
        int result = 0;
        TrainAppraisePerson appraisePerson = this.selectById(id);
        if(appraisePerson != null){
            if(appraisePerson.getStatus() == 1){
                throw new BusinessException(ResultCode.ERROR,"参考人员已完成评估,无法移除");
            }
            result = this.deleteById(id);
            Map<String, Object> params = new HashMap<>();
            params.put("type_id",appraisePerson.getAppraiseId());
            params.put("user_id",appraisePerson.getUserId());
            params.put("type",3);
            //清空通知表
            trainNoticeService.deleteByParams(params);
        }
        return result;
    }

    @Override
    public TrainAppraisePerson getByQuery(Map<String, Object> params){
        return trainAppraisePersonMapper.getByQuery(params);
    }

    @Override
    public List<String> getPersonIdsByQuery(Map<String,Object> params){
        return trainAppraisePersonMapper.getPersonIdsByQuery(params);
    }
}