package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainApproveFlowNodeDTO;
import com.dyys.hr.entity.train.TrainApproveFlowNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批流节点配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
@Mapper
public interface TrainApproveFlowNodeMapper extends ICrudMapper<TrainApproveFlowNode> {
	void deleteByParams(Map<String, Object> params);

	List<TrainApproveFlowNode> getNodeListByQuery(Map<String, Object> params);

	List<TrainApproveFlowNodeDTO> getNodeDTOListByQuery(Map<String, Object> params);

}