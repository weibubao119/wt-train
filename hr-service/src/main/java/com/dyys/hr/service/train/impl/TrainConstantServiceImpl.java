package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainConstantMapper;
import com.dyys.hr.dto.train.TrainConstantDTO;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.excel.ConstantExcel;
import com.dyys.hr.service.staff.IStaffPosnSecService;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.vo.common.PsPosnSecVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TrainConstantServiceImpl extends AbstractCrudService<TrainConstant, Integer> implements TrainConstantService {
    @Autowired
    private TrainConstantMapper trainConstantMapper;
    @Autowired
    private IStaffPosnSecService iStaffPosnSecService;

    @Override
    public List<TrainConstantVO> selectList(Map<String, Object> params){
        return trainConstantMapper.selectList(params);
    }

    @Override
    public PageInfo<TrainConstantPageVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainConstantPageVO> voList = trainConstantMapper.pageList(params);
        for (TrainConstantPageVO vo : voList) {
            // 类型为职序与学习方向
            if (vo.getType().equals(7)) {
                PsPosnSecVO psPosnSecVO = iStaffPosnSecService.getBasicInfoByCode(vo.getPid());
                vo.setParentName(null != psPosnSecVO ? psPosnSecVO.getPosnSecName() : "");
            }
        }
        return new PageInfo<>(voList);
    }

    @Override
    public Integer save(TrainConstantDTO dto, String loginUserId){
        if (!dto.getType().equals(7)) {
            dto.setPid("0");
        }
        //校验同类型不重复插入相同名称档案
        Map<String, Object> map = new HashMap<>();
        map.put("name",dto.getName());
        map.put("type",dto.getType());
        map.put("pid", dto.getPid());
        if (this.selectList(map).isEmpty()) {
            TrainConstant entity = new TrainConstant();
            BeanUtils.copyProperties(dto,entity);
            entity.setIsDeleted(0);
            entity.setCreateUser(loginUserId);
            entity.setCreateTime(System.currentTimeMillis()/1000);
            return this.insertSelective(entity);
        }
        throw new BusinessException(ResultCode.EXCEPTION,"同名类型档案已存在,不能重复添加");
    }

    @Override
    public Integer changeStatus(Map<String, Object> params){
        TrainConstant entity = new TrainConstant();
        entity.setId((Integer) params.get("id"));
        entity.setStatus((Integer) params.get("status"));
        entity.setUpdateUser(params.get("userId").toString());
        entity.setUpdateTime(System.currentTimeMillis()/1000);
        return this.updateSelective(entity);
    }

    /**
     * 基础档案下拉框选项
     * @param type
     * @param pid
     * @return
     */
    @Override
    public List<String> selectBoxList(Integer type, String pid) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("type", type);
        queryMap.put("pid", pid);
        queryMap.put("status", 1);
        queryMap.put("is_deleted", 0);
        List<TrainConstantVO> constantList = this.selectList(queryMap);
        List<String> choiceList = new ArrayList<>();
        if (!constantList.isEmpty()) {
            for (TrainConstantVO vo : constantList) {
                choiceList.add(vo.getId() + "-" + vo.getName());
            }
        }
        return choiceList;
    }

    /**
     * 学习地图学习方向选择列表
     * @param pid
     * @return
     */
    @Override
    public List<LearnMapSblSelctVO> selectListByMap(String pid) {
        return trainConstantMapper.selectListByMap(pid);
    }

    /**
     * 根据名称获得详情
     * @param type
     * @param pid
     * @param name
     * @return
     */
    @Override
    public TrainConstant getInfoByName(Integer type, String pid, String name) {
        TrainConstant constantQuery = new TrainConstant();
        constantQuery.setType(type);
        constantQuery.setPid(pid);
        constantQuery.setName(name);
        constantQuery.setStatus(1);
        constantQuery.setIsDeleted(0);
        return selectOne(constantQuery);
    }

    /**
     * 根据ID获取详情
     * @param id
     * @param type
     * @param pid
     * @return
     */
    @Override
    public TrainConstant getInfoBySblId(Integer id, Integer type, String pid) {
        TrainConstant constantQuery = new TrainConstant();
        constantQuery.setId(id);
        constantQuery.setType(type);
        constantQuery.setPid(pid);
        constantQuery.setStatus(1);
        constantQuery.setIsDeleted(0);
        return selectOne(constantQuery);
    }

    /**
     * 删除档案
     * @param id
     * @param loginEmplId
     * @return
     */
    @Override
    public Integer delById(Integer id, String loginEmplId) {
        TrainConstant constant = new TrainConstant();
        constant.setId(id);
        constant.setIsDeleted(1);
        constant.setUpdateUser(loginEmplId);
        constant.setUpdateTime(System.currentTimeMillis()/1000);
        return updateSelective(constant);
    }

    /**
     * 基础设置导出
     * @param params
     * @return
     */
    @Override
    public List<ConstantExcel> constantExp(Map<String, Object> params) {
        return trainConstantMapper.constantExp(params);
    }
}