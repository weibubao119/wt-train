package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainApproveFlow;
import com.dyys.hr.vo.train.TrainApproveFlowPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批流配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
@Mapper
public interface TrainApproveFlowMapper extends ICrudMapper<TrainApproveFlow> {
    List<TrainApproveFlowPageVO> pageList(Map<String, Object> params);

    /**
     * 员工审批流选择列表
     * @param params
     * @return
     */
    List<Map<String,Object>> selectList(Map<String, Object> params);
}