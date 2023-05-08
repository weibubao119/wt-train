package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainPlanDetail;
import com.dyys.hr.entity.train.excel.PlanDetailExportExcel;
import com.dyys.hr.vo.train.TrainPlanDetailSelectVO;
import com.dyys.hr.vo.train.TrainPlanDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TrainPlanDetailMapper extends ICrudMapper<TrainPlanDetail> {
    List<TrainPlanDetailVO> getDetailList(Map<String, Object> params);

    List<TrainPlanDetailSelectVO> selectList(Map<String, Object> params);

    TrainPlanDetailVO getDetail(Map<String, Object> params);

    /**
     * 需求汇总导出培训计划数据
     * @param planId
     * @return
     */
    List<PlanDetailExportExcel> exportListByPlanId(Long planId);

    /**
     * 根据条件删除
     * @param params
     */
    void deleteByParams(Map<String, Object> params);
}