package com.dyys.hr.enums;

/**
 * 招聘计划类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:46
 **/
public enum RecruitPlanTypeEnum {
    /**
     * 年度
     */
    YEAR(1),
    /**
     * 临时
     */
    TEMP(2);

    private Integer value;

    RecruitPlanTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (RecruitPlanTypeEnum v : RecruitPlanTypeEnum.values()) {
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
