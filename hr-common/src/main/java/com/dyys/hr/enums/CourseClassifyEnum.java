package com.dyys.hr.enums;

/**
 * 课程类别
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:31
 **/
public enum CourseClassifyEnum {

    /**
     * 管理
     */
    MANAGER(1),
    /**
     * 技能
     */
    SKILL(2),
    /**
     * 操作
     */
    HANDLE(3);


    private Integer value;

    CourseClassifyEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (CourseClassifyEnum v : CourseClassifyEnum.values()) {
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
