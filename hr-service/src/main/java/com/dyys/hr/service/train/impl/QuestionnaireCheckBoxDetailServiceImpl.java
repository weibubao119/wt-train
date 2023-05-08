package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireCheckBoxDetailMapper;
import com.dyys.hr.dao.train.QuestionnaireCheckBoxMapper;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDTO;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBox;
import com.dyys.hr.entity.train.QuestionnaireCheckBoxDetail;
import com.dyys.hr.service.train.QuestionnaireCheckBoxDetailService;
import com.dyys.hr.service.train.QuestionnaireCheckBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireCheckBoxDetailServiceImpl extends AbstractCrudService<QuestionnaireCheckBoxDetail, Long> implements QuestionnaireCheckBoxDetailService {
    @Autowired
    private QuestionnaireCheckBoxDetailMapper questionnaireCheckBoxDetailMapper;

    @Override
    public List<QuestionnaireCheckBoxDetailDTO> getListByQuery(Map<String, Object> params){
        return questionnaireCheckBoxDetailMapper.getListByQuery(params);
    }
}