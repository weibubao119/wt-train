package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAppraisePerson;
import com.dyys.hr.vo.train.TrainAppraisePersonVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainAppraisePersonMapper extends ICrudMapper<TrainAppraisePerson> {
    List<TrainAppraisePersonVO> getListByQuery(Map<String,Object> params);

    void deleteByParams(Map<String,Object> params);

    TrainAppraisePerson getByQuery(Map<String,Object> params);

    List<String> getPersonIdsByQuery(Map<String,Object> params);
}