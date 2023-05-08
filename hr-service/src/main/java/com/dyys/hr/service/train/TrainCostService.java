package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainCostDTO;
import com.dyys.hr.entity.train.TrainCost;
import com.dyys.hr.vo.train.TrainCostVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainCostService extends ICrudService<TrainCost, Long> {
    PageInfo<TrainCostVO> pageList(Map<String, Object> params);

    Long save(TrainCostDTO dto, String loginUserId);

    Integer deleteByCostId(Long costId);

    /**
     * 获取项目培训总费用
     * @param programsId
     * @return
     */
    Float getTotalFeeByProgramsId(Long programsId);

    /**
     * 获取项目培训各科目费用列表
     * @param programsId
     * @return
     */
    List<Map<String,Object>> subjectAmountList(Long programsId);

    /**
     * 获取某科目的培训费用
     * @param params
     * @return
     */
    Float getSubjectAmount(Map<String, Object> params);
}