package com.dyys.hr.enums;

/**
 * 计划类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:59
 **/
public enum TrainPlanTypeEnum {

    /**
     * 公司计划
     */
    COMPANY(1),
    /**
     * 部门计划
     */
    DEPT(2);

    private Integer value;

    TrainPlanTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainPlanTypeEnum v : TrainPlanTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
