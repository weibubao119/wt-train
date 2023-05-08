package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainApproveFlowNodeMapper;
import com.dyys.hr.entity.train.TrainApproveFlowNode;
import com.dyys.hr.service.train.TrainApproveFlowNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批流节点配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
@Service
public class TrainApproveFlowNodeServiceImpl extends AbstractCrudService<TrainApproveFlowNode, Integer> implements TrainApproveFlowNodeService {
    @Autowired
    private TrainApproveFlowNodeMapper trainApproveFlowNodeMapper;

    /**
     * 获取审批流节点列表
     * @param params
     * @return
     */
    @Override
    public List<TrainApproveFlowNode> getNodeListByQuery(Map<String, Object> params){
        return trainApproveFlowNodeMapper.getNodeListByQuery(params);
    }
}