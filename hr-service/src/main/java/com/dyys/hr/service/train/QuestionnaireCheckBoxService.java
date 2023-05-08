package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBox;

import java.util.List;
import java.util.Map;

public interface QuestionnaireCheckBoxService extends ICrudService<QuestionnaireCheckBox, Long> {
    List<QuestionnaireCheckBoxDTO> getListByQuery(Map<String, Object> params);
}