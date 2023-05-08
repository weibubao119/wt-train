package com.dyys.hr.enums;

/**
 * 入职状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:55
 **/
public enum EntryStatusEnum {
    /**
     * 待入职
     */
    WAIT(0,"待入职"),
    /**
     * 已入职
     */
    JOINED(1,"已入职"),
    /**
     * 未入职
     */
    UNJOINED(2,"未入职");

    private Integer value;
    private String text;

    EntryStatusEnum(Integer value,String text) {
        this.value = value;
        this.text = text;
    }

    public static String getText(Integer value) {
        for (EntryStatusEnum statusEnum : values()) {
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
        for (EntryStatusEnum v : EntryStatusEnum.values()) {
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
