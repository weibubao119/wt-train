package com.dyys.hr.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 评估问卷评分表评分对象配置
 * @author wsj
 * @projectName hr-recruit
 * @date 2022/6/1716:39
 */
public class QuestionnaireScoreObjectData {
    public static Map<Integer,String> typeMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"课程");
        map.put(2,"讲师");
        map.put(3,"组织方");
        map.put(4,"培训机构");
        return map;
    }
}
