package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireTextMapper;
import com.dyys.hr.dto.train.QuestionnaireTextDTO;
import com.dyys.hr.entity.train.QuestionnaireText;
import com.dyys.hr.service.train.QuestionnaireTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireTextServiceImpl extends AbstractCrudService<QuestionnaireText, Long> implements QuestionnaireTextService {
    @Autowired
    private QuestionnaireTextMapper questionnaireTextMapper;

    @Override
    public List<QuestionnaireTextDTO> getListByQuery(Map<String, Object> params){
        return questionnaireTextMapper.getListByQuery(params);
    }

}