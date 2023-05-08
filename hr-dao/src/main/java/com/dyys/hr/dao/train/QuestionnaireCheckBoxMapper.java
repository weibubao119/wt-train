package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDTO;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBox;
import com.dyys.hr.entity.train.QuestionnaireScoreSheets;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *  用户评估问卷-选择框数据mapper
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireCheckBoxMapper extends ICrudMapper<QuestionnaireCheckBox> {
    List<QuestionnaireCheckBoxDTO> getListByQuery(Map<String, Object> params);
}