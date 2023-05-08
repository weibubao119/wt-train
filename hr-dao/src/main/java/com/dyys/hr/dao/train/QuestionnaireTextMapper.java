package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireTextDTO;
import com.dyys.hr.entity.train.QuestionnaireText;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户评估问卷-文本框数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireTextMapper extends ICrudMapper<QuestionnaireText> {
    List<QuestionnaireTextDTO> getListByQuery(Map<String, Object> params);
}