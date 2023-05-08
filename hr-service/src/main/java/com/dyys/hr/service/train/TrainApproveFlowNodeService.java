package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainApproveFlowNode;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批流节点配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
public interface TrainApproveFlowNodeService extends ICrudService<TrainApproveFlowNode, Integer> {
    /**
     * 获取审批流节点列表
     * @param params
     * @return
     */
    List<TrainApproveFlowNode> getNodeListByQuery(Map<String, Object> params);
}