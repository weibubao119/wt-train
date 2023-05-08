package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainApproveFlowMapper;
import com.dyys.hr.dao.train.TrainApproveFlowNodeMapper;
import com.dyys.hr.dto.train.TrainApproveFlowDTO;
import com.dyys.hr.dto.train.TrainApproveFlowNodeDTO;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.TrainApproveFlow;
import com.dyys.hr.entity.train.TrainApproveFlowNode;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.train.TrainApproveFlowNodeService;
import com.dyys.hr.service.train.TrainApproveFlowService;
import com.dyys.hr.vo.train.TrainApproveFlowPageVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 培训-审批流配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
@Service
public class TrainApproveFlowServiceImpl extends AbstractCrudService<TrainApproveFlow, Integer> implements TrainApproveFlowService {
    @Autowired
    private TrainApproveFlowMapper trainApproveFlowMapper;
    @Autowired
    private TrainApproveFlowNodeMapper trainApproveFlowNodeMapper;
    @Autowired
    private TrainApproveFlowNodeService trainApproveFlowNodeService;
    @Autowired
    private IStaffJobService staffJobService;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;

    @Override
    public PageInfo<TrainApproveFlowPageVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainApproveFlowPageVO> voList = trainApproveFlowMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public Integer save(TrainApproveFlowDTO dto, String loginUserId) {
        //新增审批流
        TrainApproveFlow flowEntity = new TrainApproveFlow();
        BeanUtils.copyProperties(dto,flowEntity);
        flowEntity.setCreateUser(loginUserId);
        flowEntity.setCreateTime(System.currentTimeMillis()/1000);
        Integer flowId = this.insertSelective(flowEntity);
        List<TrainApproveFlowNodeDTO> flowNodeList = dto.getFlowNodeList();
        for (TrainApproveFlowNodeDTO flowNodeDTO : flowNodeList){
            TrainApproveFlowNode nodeEntity = new TrainApproveFlowNode();
            BeanUtils.copyProperties(flowNodeDTO,nodeEntity);
            nodeEntity.setFlowId(flowId);
            nodeEntity.setCreateUser(loginUserId);
            nodeEntity.setCreateTime(System.currentTimeMillis()/1000);
            trainApproveFlowNodeService.insertSelective(nodeEntity);
        }
        return flowId;
    }

    @Override
    public Integer update(TrainApproveFlowDTO dto, String loginUserId) {
        //更新审批流
        TrainApproveFlow flowEntity = new TrainApproveFlow();
        BeanUtils.copyProperties(dto,flowEntity);
        flowEntity.setUpdateUser(loginUserId);
        flowEntity.setUpdateTime(System.currentTimeMillis()/1000);
        this.updateSelective(flowEntity);
        Integer flowId = dto.getId();
        //删除之前节点数据
        Map<String, Object> map = new HashMap<>();
        map.put("flowId",flowId);
        trainApproveFlowNodeMapper.deleteByParams(map);
        List<TrainApproveFlowNodeDTO> flowNodeList = dto.getFlowNodeList();
        for (TrainApproveFlowNodeDTO flowNodeDTO : flowNodeList){
            TrainApproveFlowNode nodeEntity = new TrainApproveFlowNode();
            BeanUtils.copyProperties(flowNodeDTO,nodeEntity);
            nodeEntity.setFlowId(flowId);
            nodeEntity.setCreateUser(loginUserId);
            nodeEntity.setCreateTime(System.currentTimeMillis()/1000);
            trainApproveFlowNodeService.insertSelective(nodeEntity);
        }
        return flowId;
    }

    @Override
    public Integer changeStatus(Map<String, Object> params) {
        TrainApproveFlow flowEntity = new TrainApproveFlow();
        flowEntity.setId(Integer.parseInt(params.get("id").toString()));
        flowEntity.setStatus(Integer.parseInt(params.get("status").toString()));
        flowEntity.setUpdateUser(params.get("userId").toString());
        flowEntity.setUpdateTime(System.currentTimeMillis()/1000);
        return this.updateSelective(flowEntity);
    }

    /**
     * 员工审批流选择
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        String emplId = (String)params.get("emplId"); // 当前登录员工工号
        StaffJob staffJob = staffJobService.getInfoByEmplId(emplId);
        String deptIdPath = staffDepartmentService.selectById(staffJob.getDeptId()).getDeptIdPath();
        String maxDeptId = "";
        if (deptIdPath != null) {
            String[] deptIds = deptIdPath.split(",");
            if (deptIds.length > 1 && !deptIds[1].equals("")) {
                maxDeptId = deptIds[1];
            }
        }
        params.put("maxDeptId", maxDeptId);
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainApproveFlowMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public TrainApproveFlowDTO getDetail(Integer id){
        TrainApproveFlow approveFlow = this.selectById(id);
        TrainApproveFlowDTO flowDTO = new TrainApproveFlowDTO();
        BeanUtils.copyProperties(approveFlow,flowDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("flowId",id);
        flowDTO.setFlowNodeList(trainApproveFlowNodeMapper.getNodeDTOListByQuery(map));
        return flowDTO;
    }
}