package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainPlanDetailDTO;
import com.dyys.hr.entity.train.TrainPlanDetail;
import com.dyys.hr.entity.train.excel.PlanDetailExcel;
import com.dyys.hr.entity.train.excel.PlanDetailExportExcel;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.train.TrainPlanDetailExcelVO;
import com.dyys.hr.vo.train.TrainPlanDetailSelectVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface TrainPlanDetailService extends ICrudService<TrainPlanDetail, Long> {
    PageInfo<TrainPlanDetailSelectVO> selectList(Map<String, Object> params);

    /**
     * 培训计划导入模板下拉框数据
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 培训计划导入数据处理
     * @param excelList
     * @return
     */
    TrainPlanDetailExcelVO handlePlanDetailExcel(List<PlanDetailExcel> excelList);

    /**
     * 需求汇总导出培训计划数据
     * @param planId
     * @return
     */
    List<PlanDetailExportExcel> exportListByPlanId(Long planId);
}