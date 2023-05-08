package com.dyys.hr.enums;

/**
 * 课程周期
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:27
 **/
public enum TimeCycleEnum {

    /**
     * 天
     */
    DAY(1),
    /**
     * 周
     */
    WEEK(2),
    /**
     * 月
     */
    MONTH(3),
    /**
     * 年
     */
    YEAR(4);


    private Integer value;

    TimeCycleEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TimeCycleEnum v : TimeCycleEnum.values()) {
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
