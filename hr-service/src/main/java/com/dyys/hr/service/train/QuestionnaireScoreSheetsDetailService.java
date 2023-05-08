package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheetsDetail;

import java.util.List;
import java.util.Map;

public interface QuestionnaireScoreSheetsDetailService extends ICrudService<QuestionnaireScoreSheetsDetail, Long> {
    List<QuestionnaireScoreSheetsDetailDTO> getListByQuery(Map<String, Object> params);

    List<String> getGroupName(Long sheetsId,String field);

    /**
     * 获取每个打分指标的均分
     * @param params
     * @return
     */
    double getAvgData(Map<String, Object> params);

    /**
     * 获取所有评估打分项分组列表数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getGroupTableDataNameData(Map<String, Object> params);

    /**
     * 获取所有评估打分列分组列表数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getGroupTableHeaderNameData(Map<String, Object> params);

    /**
     * 获取所有问答题标题分组列表数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getGroupTextTitleData(Map<String, Object> params);

    /**
     * 获取所有问答题回答内容列表数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getTextTitleValueList(Map<String, Object> params);
}