package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireInstructionsDTO;
import com.dyys.hr.entity.train.QuestionnaireInstructions;

import java.util.List;
import java.util.Map;

public interface QuestionnaireInstructionsService extends ICrudService<QuestionnaireInstructions, Long> {
    List<QuestionnaireInstructionsDTO> getListByQuery(Map<String, Object> params);
}