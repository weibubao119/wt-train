package com.dyys.hr.enums;

/**
 * 反馈状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:57
 **/
public enum FeedBackStatusEnum {
    /**
     * 未反馈
     */
    WAIT(0),
    /**
     * 已反馈
     */
    END(1),
    /**
     * 取消反馈
     */
    CANCEL(2);

    private Integer value;

    FeedBackStatusEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (FeedBackStatusEnum v : FeedBackStatusEnum.values()) {
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
