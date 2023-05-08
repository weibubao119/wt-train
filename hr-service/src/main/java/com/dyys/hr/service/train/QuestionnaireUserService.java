package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.QuestionnaireUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface QuestionnaireUserService extends ICrudService<QuestionnaireUser, Long> {
    /**
     * 问卷评估用户列表
     * @param query
     * @return
     */
    List<QuestionnaireUser> getList(Map<String,Object> query);

    /**
     * 评估对象 总分
     * @param query
     * @return
     */
    BigDecimal getObjectTotalScore(Map<String,Object> query);

    /**
     * 删除评估问卷用户
     * @param trainAppraiseId
     * @param type
     * @return
     */
    Integer delByCondition(Long trainAppraiseId, Integer type);
}