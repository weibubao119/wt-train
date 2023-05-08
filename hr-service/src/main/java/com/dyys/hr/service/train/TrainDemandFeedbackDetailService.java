package com.dyys.hr.service.train;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainDemandFeedbackDetailDTO;
import com.dyys.hr.dto.train.TrainDemandRelateDTO;
import com.dyys.hr.entity.train.TrainDemandFeedbackDetail;
import com.dyys.hr.entity.train.excel.DemandFeedbackExcel;
import com.dyys.hr.entity.train.excel.DemandFeedbackImportExcel;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainDemandFeedbackDetailService extends ICrudService<TrainDemandFeedbackDetail, Long> {
    /**
     * 需求汇总页面数据列表
     * @param params
     * @return
     */
    PageInfo<TrainDemandFeedbackDetailVO> pageList(Map<String, Object> params);

    /**
     * 下发人反馈
     * @param dto
     * @param loginUserId
     * @return
     */
    Long save(TrainDemandFeedbackDetailDTO dto, String loginUserId);

    TrainDemandFeedbackDetailVO selectByDetailId(Long detailId);

    Integer update(TrainDemandFeedbackDetailDTO dto,String loginUserId);

    Integer deleteByDetailId(Long detailId);

    Boolean relateDemand(List<TrainDemandRelateDTO> dtoList, String loginUserId);

    /**
     * 反馈人反馈
     * @param dtoList
     * @param loginUserId
     * @return
     */
    Boolean feedbackUserSave(List<TrainDemandFeedbackDetailDTO> dtoList, String loginUserId);

    List<TrainDemandFeedbackDetailVO> getRelateDemandDetailList(Map<String, Object> params);

    /**
     * 需求汇总-反馈列表导出
     * @param demandId
     * @return
     */
    List<DemandFeedbackExcel> exportList(String demandId);

    /**
     * excel模板中下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 校验单人反馈批量导入数据
     * @param excelList
     * @param demandId
     * @param loginEmplId
     * @return
     */
    TrainDemandFeedbackDetailExcelVO checkOwnImportDetail(List<DemandFeedbackImportExcel> excelList, Long demandId, String loginEmplId);

    /**
     * 汇总需求-批量导入
     * @param excelList
     * @param demandId
     * @param loginEmplId
     * @return
     */
    List<DemandFeedbackImportExcel> importExl(List<DemandFeedbackImportExcel> excelList, Long demandId, String loginEmplId);
}