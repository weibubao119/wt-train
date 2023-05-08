package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainAppraisePerson;
import com.dyys.hr.vo.train.TrainAppraisePersonVO;

import java.util.List;
import java.util.Map;

public interface TrainAppraisePersonService extends ICrudService<TrainAppraisePerson, Long> {
    List<TrainAppraisePersonVO> getListByQuery(Map<String,Object> params);

    void deleteByParams(Map<String,Object> params);

    Integer remove(Long id);

    TrainAppraisePerson getByQuery(Map<String,Object> params);

    List<String> getPersonIdsByQuery(Map<String,Object> params);
}