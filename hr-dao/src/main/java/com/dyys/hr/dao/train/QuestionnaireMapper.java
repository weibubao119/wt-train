package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireDTO;
import com.dyys.hr.entity.train.Questionnaire;
import com.dyys.hr.vo.train.QuestionnaireTemplatePageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 评估问卷-主表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireMapper extends ICrudMapper<Questionnaire> {
    List<QuestionnaireTemplatePageVo> pageList(Map<String, Object> params);

    QuestionnaireDTO getDetailByQuery(Map<String, Object> params);

    List<Map<String,Object>> selectList(Map<String, Object> params);
}