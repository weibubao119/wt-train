package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.QuestionnaireUser;
import com.dyys.hr.vo.train.QuestionnaireUserPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 评估问卷-用户填写问卷记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Mapper
public interface QuestionnaireUserMapper extends ICrudMapper<QuestionnaireUser> {
    List<QuestionnaireUserPageVo> userFillPageList(Map<String, Object> params);

    QuestionnaireUser getByQuery(Map<String, Object> params);

    List<QuestionnaireUser> getList(Map<String,Object> query);

    BigDecimal getObjectTotalScore(Map<String,Object> query);
}