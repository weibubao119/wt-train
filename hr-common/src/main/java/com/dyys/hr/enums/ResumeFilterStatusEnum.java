package com.dyys.hr.enums;

/**
 * 简历筛选状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:51
 **/
public enum ResumeFilterStatusEnum {

    /**
     * 未开始
     */
    WAIT(0, "未开始"),
    /**
     * 已通过
     */
    PASS(1, "已通过");

    private Integer value;
    private String text;

    ResumeFilterStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ResumeFilterStatusEnum v : ResumeFilterStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getText(Integer value) {
        for (ResumeFilterStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum.getText();
            }
        }
        return "";
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
