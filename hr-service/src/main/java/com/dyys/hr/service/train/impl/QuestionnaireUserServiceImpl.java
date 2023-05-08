package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireUserMapper;
import com.dyys.hr.entity.train.QuestionnaireUser;
import com.dyys.hr.service.train.QuestionnaireUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionnaireUserServiceImpl extends AbstractCrudService<QuestionnaireUser, Long> implements QuestionnaireUserService {
    @Autowired
    private QuestionnaireUserMapper questionnaireUserMapper;
    @Override
    public List<QuestionnaireUser> getList(Map<String,Object> query){
        return  questionnaireUserMapper.getList(query);
    }

    @Override
    public BigDecimal getObjectTotalScore(Map<String,Object> query){
        return  questionnaireUserMapper.getObjectTotalScore(query);
    }

    /**
     * 删除评估问卷用户
     * @param trainAppraiseId
     * @param type
     * @return
     */
    @Override
    public Integer delByCondition(Long trainAppraiseId, Integer type) {
        Condition condition = new Condition(QuestionnaireUser.class);
        condition.createCriteria().andEqualTo("type", type)
                .andEqualTo("trainAppraiseId", trainAppraiseId);
        return questionnaireUserMapper.deleteByCondition(condition);
    }
}