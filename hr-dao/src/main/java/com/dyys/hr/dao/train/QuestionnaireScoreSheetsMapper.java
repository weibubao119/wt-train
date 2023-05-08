package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheets;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *  用户评估问卷-评分表数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireScoreSheetsMapper extends ICrudMapper<QuestionnaireScoreSheets> {
    List<QuestionnaireScoreSheetsDTO> getListByQuery(Map<String, Object> params);
}