package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainTraineeSummary;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.vo.train.TrainTraineeSummaryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainTraineeSummaryService extends ICrudService<TrainTraineeSummary, Long> {
    PageInfo<TrainTraineeSummaryVO> traineePageList(Map<String, Object> params);

    //计算项目培训实际成绩(平均分)
    String getProgramsAverageResult(Map<String, Object> params);

    /**
     * Excel培训总结模板下拉选项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 导入培训总结数据
     * @param excelList
     * @param programsId
     * @param loginEmplId
     * @return
     */
    List<SummaryExcel> importExl(List<SummaryExcel> excelList, Long programsId, String loginEmplId);
}