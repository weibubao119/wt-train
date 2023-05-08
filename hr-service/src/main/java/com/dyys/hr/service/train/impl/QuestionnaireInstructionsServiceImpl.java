package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireInstructionsMapper;
import com.dyys.hr.dto.train.QuestionnaireInstructionsDTO;
import com.dyys.hr.entity.train.QuestionnaireInstructions;
import com.dyys.hr.service.train.QuestionnaireInstructionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireInstructionsServiceImpl extends AbstractCrudService<QuestionnaireInstructions, Long> implements QuestionnaireInstructionsService {
    @Autowired
    private QuestionnaireInstructionsMapper questionnaireInstructionsMapper;
    @Override
    public List<QuestionnaireInstructionsDTO> getListByQuery(Map<String, Object> params){
        return questionnaireInstructionsMapper.getListByQuery(params);
    }
}