package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireScoreSheetsMapper;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheets;
import com.dyys.hr.service.train.QuestionnaireScoreSheetsDetailService;
import com.dyys.hr.service.train.QuestionnaireScoreSheetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireScoreSheetsServiceImpl extends AbstractCrudService<QuestionnaireScoreSheets, Long> implements QuestionnaireScoreSheetsService {
    @Autowired
    private QuestionnaireScoreSheetsMapper questionnaireScoreSheetsMapper;
    @Autowired
    private QuestionnaireScoreSheetsDetailService questionnaireScoreSheetsDetailService;

    @Override
    public List<QuestionnaireScoreSheetsDTO> getListByQuery(Map<String, Object> params){
        List<QuestionnaireScoreSheetsDTO> sheetsDTOS = questionnaireScoreSheetsMapper.getListByQuery(params);
        if(sheetsDTOS != null && !sheetsDTOS.isEmpty()){
            //获取评分详情数据
            Map<String, Object> query = new HashMap<>();
            for (QuestionnaireScoreSheetsDTO sheetsDTO : sheetsDTOS){
                query.put("sheetsId",sheetsDTO.getId());
                sheetsDTO.setSheetsDetailList(questionnaireScoreSheetsDetailService.getListByQuery(query));
            }
        }
        return sheetsDTOS;
    }
}