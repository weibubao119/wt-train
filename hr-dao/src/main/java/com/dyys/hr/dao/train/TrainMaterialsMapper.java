package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainMaterials;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainMaterialsMapper extends ICrudMapper<TrainMaterials> {
    List<TrainMaterialsVO> pageList(Map<String, Object> params);
}