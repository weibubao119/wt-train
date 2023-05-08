package com.dyys.hr.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 培训系统常量类型数据设置
 * @author wsj
 * @projectName hr-recruit
 * @date 2022/6/1716:39
 */
public class TrainConstantTypeData {

    /**
     * 基础档案类别
     * @return
     */
    public static Map<Integer,String> typeMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"课程类别");
        map.put(2,"培训需求依据");
        map.put(3,"培训考核方法");
        map.put(4,"讲师等级");
        map.put(5,"机构类型");
        map.put(6,"计划类型");
        map.put(7,"职序与学习方向");
        map.put(8,"培训科目");
        map.put(9,"机构等级");
        map.put(10,"学习方式");
        return map;
    }

    public static Map<Integer, String> trainShapeMap() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1, "内部培训");
        map.put(2, "外部培训");
        return map;
    }
}
