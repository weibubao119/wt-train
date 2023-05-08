package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainApproveFlowDTO;
import com.dyys.hr.entity.train.TrainApproveFlow;
import com.dyys.hr.vo.train.TrainApproveFlowPageVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 培训-审批流配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
public interface TrainApproveFlowService extends ICrudService<TrainApproveFlow, Integer> {
    /**
     * 审批流分页
     * @param params
     * @return
     */
    PageInfo<TrainApproveFlowPageVO> pageList(Map<String, Object> params);

    /**
     * 新增
     * @param dto
     * @param loginUserId
     * @return
     */
    Integer save(TrainApproveFlowDTO dto, String loginUserId);

    /**
     * 更新
     * @param dto
     * @param loginUserId
     * @return
     */
    Integer update(TrainApproveFlowDTO dto, String loginUserId);

    /**
     * 更新状态
     * @param params
     * @return
     */
    Integer changeStatus(Map<String, Object> params);

    /**
     * 员工审批流选择
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    TrainApproveFlowDTO getDetail(Integer id);
}