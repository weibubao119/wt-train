package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.entity.train.TrainMaterials;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.github.pagehelper.PageInfo;
import java.util.Map;

public interface TrainMaterialsService extends ICrudService<TrainMaterials, Long> {
    PageInfo<TrainMaterialsVO> pageList(Map<String, Object> params);

    Long save(TrainMaterialsDTO dto, String loginUserId);
}