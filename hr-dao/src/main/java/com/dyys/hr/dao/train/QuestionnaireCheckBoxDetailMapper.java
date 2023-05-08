package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDTO;
import com.dyys.hr.dto.train.QuestionnaireCheckBoxDetailDTO;
import com.dyys.hr.entity.train.QuestionnaireCheckBox;
import com.dyys.hr.entity.train.QuestionnaireCheckBoxDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *  用户评估问卷-选择框详细数据mapper
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireCheckBoxDetailMapper extends ICrudMapper<QuestionnaireCheckBoxDetail> {
    List<QuestionnaireCheckBoxDetailDTO> getListByQuery(Map<String, Object> params);
}