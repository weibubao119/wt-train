package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.QuestionnaireDTO;
import com.dyys.hr.dto.train.QuestionnaireUserSubmitDTO;
import com.dyys.hr.entity.train.Questionnaire;
import com.dyys.hr.vo.train.QuestionnaireTemplatePageVo;
import com.dyys.hr.vo.train.QuestionnaireUserPageVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;


public interface QuestionnaireService extends ICrudService<Questionnaire, Long> {
    PageInfo<QuestionnaireTemplatePageVo> pageList(Map<String, Object> params);

    Long save(QuestionnaireDTO dto, String loginUserId);

    Integer update(QuestionnaireDTO dto, String loginUserId);

    QuestionnaireDTO getDetail(Map<String, Object> params);

    PageInfo<QuestionnaireUserPageVo> userFillPageList(Map<String, Object> params);

    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    Integer userSubmit(QuestionnaireUserSubmitDTO dto, String loginUserId);

    QuestionnaireUserSubmitDTO getUserSubmitDetail(Map<String, Object> params);
}