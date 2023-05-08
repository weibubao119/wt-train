package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainTemporaryDemandDTO;
import com.dyys.hr.entity.train.TrainTemporaryDemand;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TrainTemporaryDemandService extends ICrudService<TrainTemporaryDemand, Long> {
    PageInfo<TrainTemporaryDemandPageVO> pageList(Map<String, Object> params);

    /**
     * 创建
     * @param dto
     * @param loginUserId
     * @return
     */
    Long save(TrainTemporaryDemandDTO dto,String loginUserId);

    /**
     * 更新需求
     * @param dto
     * @param loginUserId
     * @return
     */
    int update(TrainTemporaryDemandDTO dto,String loginUserId);

    /**
     * 需求详情
     * @param demandId
     * @return
     */
    TrainTemporaryDemandDetailVO getDetail(Long demandId);

    /**
     * 管理员待办临时需求审批
     * @param params
     * @return
     */
    PageInfo<AdminTemporaryDemandListVO> adminTemporaryDemandList(Map<String, Object> params);
}