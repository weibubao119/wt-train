package com.dyys.hr.enums;

/**
 * 项目类型
 * @author JUCHUAN LI
 * @date 2019/6/23 1:35
 **/
public enum ProjectTypeEnum {

    /**
     * 场地费
     */
    SITEFEE(1),

    /**
     * 管理费
     */
    MANAGERFEE(2),

    /**
     * 教材费
     */
    MATERIALFEE(3),

    /**
     * 教师课酬费用
     */
    TEACHERFEE(4),

    /**
     * 杂费
     */
    OTHERFEE(5);

    private Integer value;

    ProjectTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProjectTypeEnum v : ProjectTypeEnum.values()) {
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
