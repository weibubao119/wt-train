package com.dyys.hr.enums;

/**
 * 面试状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:52
 **/
public enum ResumeInterviewStatusEnum {
    /**
     * 未开始
     */
    WAIT(0,"未开始"),
    /**
     * 已安排
     */
    ARRANGE(1,"已安排"),
    /**
     * 已通过
     */
    PASS(2,"已通过");

    private Integer value;
    private String text;

    ResumeInterviewStatusEnum(Integer value,String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ResumeInterviewStatusEnum v : ResumeInterviewStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getText(Integer value) {
        for (ResumeInterviewStatusEnum statusEnum : values()) {
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
