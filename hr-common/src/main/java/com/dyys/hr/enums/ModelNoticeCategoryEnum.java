package com.dyys.hr.enums;

/**
 * @author mia.qiu
 * @date 2019/7/11 22:47
 */
public enum ModelNoticeCategoryEnum {
    /**
    * 面试
    */
    INTER(1),
    /**
     * 录用
     */
    OFFER(2),
    /**
     * 提醒
     */
    ALERT(3);

    private Integer value;

    ModelNoticeCategoryEnum(Integer value) {
        this.value = value;
    }
    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ModelNoticeCategoryEnum v : ModelNoticeCategoryEnum.values()) {
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
