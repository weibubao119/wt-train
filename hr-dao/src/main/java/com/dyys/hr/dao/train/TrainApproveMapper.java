package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainApprove;
import com.dyys.hr.vo.train.TrainApproveVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 培训-审批记录表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-17
 */
@Mapper
public interface TrainApproveMapper extends ICrudMapper<TrainApprove> {
    List<TrainApproveVO> getListByQuery(Map<String, Object> params);

    /**
     * 获取当次审批记录
     * @param params
     * @return
     */
    List<TrainApprove> getThisApproveListByQuery(Map<String, Object> params);

    /**
     * 获取审批记录最大历史版本号
     * @param params
     * @return
     */
    Integer getMaxHistoryCode(Map<String, Object> params);
}