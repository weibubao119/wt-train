package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainCostMapper;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.TrainCostService;
import com.dyys.hr.vo.train.TrainCostVO;
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
public class TrainCostServiceImpl extends AbstractCrudService<TrainCost, Long> implements TrainCostService {
    @Autowired
    private TrainCostMapper trainCostMapper;

    @Override
    public PageInfo<TrainCostVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainCostVO> voList = trainCostMapper.pageList(params);
        for (TrainCostVO vo : voList){
            JSONArray objects = JSONUtil.parseArray(vo.getFileListString());
            vo.setFileList(JSONUtil.toList(objects,FileDTO.class));
        }
        return new PageInfo<>(voList);
    }

    @Override
    public Long save(TrainCostDTO dto, String loginUserId){
        TrainCost costEntity = new TrainCost();
        BeanUtils.copyProperties(dto,costEntity);
        costEntity.setCreateUser(loginUserId);
        costEntity.setCreateTime(System.currentTimeMillis()/1000);
        List<FileDTO> fileList = dto.getFileList();
        costEntity.setFileList(JSONUtil.toJsonStr(fileList));
        return this.insertSelective(costEntity);
    }

    @Override
    public Integer deleteByCostId(Long costId){
        return this.deleteById(costId);
    }

    /**
     * 获取项目培训总费用
     * @param programsId
     * @return
     */
    @Override
    public Float getTotalFeeByProgramsId(Long programsId) {
        return trainCostMapper.getTotalFeeByProgramsId(programsId);
    }

    /**
     * 获取项目培训各科目费用列表
     * @param programsId
     * @return
     */
    @Override
    public List<Map<String,Object>> subjectAmountList(Long programsId){
        return trainCostMapper.subjectAmountList(programsId);
    }

    /**
     * 获取某科目的培训费用
     * @param params
     * @return
     */
    @Override
    public Float getSubjectAmount(Map<String, Object> params){
        return trainCostMapper.getSubjectAmount(params);
    }
}