package com.dyys.hr.enums;

/**
 * 招聘类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:44
 **/
public enum RecruitTypeEnum {
    /**
     * 校招
     */
    SCHOOL(1, "校招"),
    /**
     * 社招
     */
    SOCIAL(2, "社招"),
    /**
     * 内部
     */
    INSIDE(3, "内部");

    private Integer value;
    private String text;

    RecruitTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (RecruitTypeEnum v : RecruitTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getText(Integer value) {
        for (RecruitTypeEnum v : RecruitTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getText();
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
    }}
