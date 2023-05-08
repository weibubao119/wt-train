package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireScoreSheetsDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireScoreSheetsDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户评估问卷-评分表详细数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireScoreSheetsDetailMapper extends ICrudMapper<QuestionnaireScoreSheetsDetail> {
    List<QuestionnaireScoreSheetsDetailDTO> getListByQuery(Map<String, Object> params);

    List<String> getGroupName(@Param("sheetsId") Long sheetsId,@Param("field") String field);

    double getAvgData(Map<String, Object> params);

    List<Map<String,Object>> getGroupTableDataNameData(Map<String, Object> params);

    List<Map<String,Object>> getGroupTableHeaderNameData(Map<String, Object> params);

    List<Map<String,Object>> getGroupTextTitleData(Map<String, Object> params);

    List<Map<String,Object>> getTextTitleValueList(Map<String, Object> params);
}