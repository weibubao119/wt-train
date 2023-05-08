package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheets;

import java.util.List;
import java.util.Map;

public interface QuestionnaireScoreSheetsService extends ICrudService<QuestionnaireScoreSheets, Long> {
    List<QuestionnaireScoreSheetsDTO> getListByQuery(Map<String, Object> params);
}