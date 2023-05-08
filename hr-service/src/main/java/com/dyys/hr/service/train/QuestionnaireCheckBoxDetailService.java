package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBoxDetail;

import java.util.List;
import java.util.Map;

public interface QuestionnaireCheckBoxDetailService extends ICrudService<QuestionnaireCheckBoxDetail, Long> {
    List<QuestionnaireCheckBoxDetailDTO> getListByQuery(Map<String, Object> params);
}