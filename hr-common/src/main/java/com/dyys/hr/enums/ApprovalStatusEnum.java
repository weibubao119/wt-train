package com.dyys.hr.enums;

/**
 * 审批状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:14
 **/
public enum ApprovalStatusEnum {
    /**
     * 未开始
     */
    WAIT(0,"未开始"),
    /**
     * 待审批
     */
    PENDING(1,"待审批"),
    /**
     * 审批中
     */
    DOING(2,"审批中"),
    /**
     * 审批通过
     */
    PASS(3,"审批通过"),
    /**
     * 审批不通过
     */
    REFUSE(4,"审批不通过");

    private Integer value;
    private String text;

    ApprovalStatusEnum(Integer value,String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value) {
        for (ApprovalStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum.getText();
            }
        }
        return "";
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ApprovalStatusEnum v : ApprovalStatusEnum.values()) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
