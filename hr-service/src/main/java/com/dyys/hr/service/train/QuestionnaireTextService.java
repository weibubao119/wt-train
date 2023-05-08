package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireTextDTO;
import com.dyys.hr.entity.train.QuestionnaireText;

import java.util.List;
import java.util.Map;

public interface QuestionnaireTextService extends ICrudService<QuestionnaireText, Long> {
    List<QuestionnaireTextDTO> getListByQuery(Map<String, Object> params);
}