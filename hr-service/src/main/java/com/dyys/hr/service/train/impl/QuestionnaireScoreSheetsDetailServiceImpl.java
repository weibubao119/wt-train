package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireScoreSheetsDetailMapper;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheetsDetail;
import com.dyys.hr.service.train.QuestionnaireScoreSheetsDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireScoreSheetsDetailServiceImpl extends AbstractCrudService<QuestionnaireScoreSheetsDetail, Long> implements QuestionnaireScoreSheetsDetailService {
    @Autowired
    private QuestionnaireScoreSheetsDetailMapper questionnaireScoreSheetsDetailMapper;

    @Override
    public List<QuestionnaireScoreSheetsDetailDTO> getListByQuery(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getListByQuery(params);
    }

    @Override
    public List<String> getGroupName(Long sheetsId,String field){
        return questionnaireScoreSheetsDetailMapper.getGroupName(sheetsId,field);
    }

    @Override
    public double getAvgData(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getAvgData(params);
    }

    @Override
    public List<Map<String,Object>> getGroupTableDataNameData(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getGroupTableDataNameData(params);
    }

    @Override
    public List<Map<String,Object>> getGroupTableHeaderNameData(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getGroupTableHeaderNameData(params);
    }

    @Override
    public List<Map<String,Object>> getGroupTextTitleData(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getGroupTextTitleData(params);
    }

    @Override
    public List<Map<String,Object>> getTextTitleValueList(Map<String, Object> params){
        return questionnaireScoreSheetsDetailMapper.getTextTitleValueList(params);
    }
}