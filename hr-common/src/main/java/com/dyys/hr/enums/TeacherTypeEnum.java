package com.dyys.hr.enums;

/**
 * 教师类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:24
 **/
public enum TeacherTypeEnum {
    /**
     * 内部讲师
     */
    INTEACHER(1),

    /**
     * 外部讲师
     */
    OUTTEACHER(2);

    private Integer value;

    TeacherTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TeacherTypeEnum v : TeacherTypeEnum.values()) {
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
