package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainMaterialsMapper;
import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.entity.train.TrainMaterials;
import com.dyys.hr.service.train.TrainMaterialsService;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainMaterialsServiceImpl extends AbstractCrudService<TrainMaterials, Long> implements TrainMaterialsService {
    @Autowired
    private TrainMaterialsMapper trainMaterialsMapper;

    @Override
    public PageInfo<TrainMaterialsVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainMaterialsVO> voList = trainMaterialsMapper.pageList(params);
        for (TrainMaterialsVO vo : voList){
            JSONObject object = JSONUtil.parseObj(vo.getFileString());
            vo.setFile(JSONUtil.toBean(object,FileDTO.class));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public Long save(TrainMaterialsDTO dto, String loginUserId){
        TrainMaterials entity = new TrainMaterials();
        BeanUtils.copyProperties(dto,entity);
        entity.setCreateUser(loginUserId);
        entity.setCreateTime(System.currentTimeMillis()/1000);
        FileDTO file = dto.getFile();
        entity.setFile(JSONUtil.toJsonStr(file));
        return this.insertSelective(entity);
    }
}