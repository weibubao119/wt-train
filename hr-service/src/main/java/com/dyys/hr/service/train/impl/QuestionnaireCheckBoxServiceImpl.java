package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireCheckBoxMapper;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBox;
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
public class QuestionnaireCheckBoxServiceImpl extends AbstractCrudService<QuestionnaireCheckBox, Long> implements QuestionnaireCheckBoxService {
    @Autowired
    private QuestionnaireCheckBoxMapper questionnaireCheckBoxMapper;
    @Autowired
    private QuestionnaireCheckBoxDetailService questionnaireCheckBoxDetailService;

    @Override
    public List<QuestionnaireCheckBoxDTO> getListByQuery(Map<String, Object> params){
        List<QuestionnaireCheckBoxDTO> checkBoxDTOS = questionnaireCheckBoxMapper.getListByQuery(params);
        if(checkBoxDTOS != null && !checkBoxDTOS.isEmpty()){
            //获取选择框详情数据
            Map<String, Object> query = new HashMap<>();
            for (QuestionnaireCheckBoxDTO checkBoxDTO : checkBoxDTOS){
                query.put("checkBoxId",checkBoxDTO.getId());
                checkBoxDTO.setCheckBoxDetailList(questionnaireCheckBoxDetailService.getListByQuery(query));
            }
        }
        return checkBoxDTOS;
    }
}