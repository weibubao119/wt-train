package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainApprove;
import com.dyys.hr.vo.train.TrainApproveVO;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批记录表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-17
 */
public interface TrainApproveService extends ICrudService<TrainApprove, Long> {

    /**
     * 审批记录列表
     * @param params
     * @return
     */
    List<TrainApproveVO> getListByQuery(Map<String, Object> params);

    /**
     * 进行审批
     * @param params
     * @return
     */
    Integer doApprove(Map<String, Object> params);
}