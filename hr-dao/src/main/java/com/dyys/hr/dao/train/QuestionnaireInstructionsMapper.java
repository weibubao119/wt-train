package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireInstructionsDTO;
import com.dyys.hr.entity.train.QuestionnaireInstructions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户评估问卷-说明数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireInstructionsMapper extends ICrudMapper<QuestionnaireInstructions> {
    List<QuestionnaireInstructionsDTO> getListByQuery(Map<String, Object> params);
}