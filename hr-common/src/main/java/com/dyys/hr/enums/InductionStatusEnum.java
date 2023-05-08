package com.dyys.hr.enums;

/**
 * 入职管理状态
 *
 * @author mia qiu
 * @date 2019/9/11 1:54
 **/
public enum InductionStatusEnum {
    /**
     * 待入职
     */
    WAIT(0),
    /**
     * 已入职
     */
    JOINED(1),
    /**
     * 未入职
     */
    UNJOINED(2),
    /**
     * 处理中
     */
    PROCESSING(3);

    private Integer value;

    InductionStatusEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (InductionStatusEnum v : InductionStatusEnum.values()) {
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
