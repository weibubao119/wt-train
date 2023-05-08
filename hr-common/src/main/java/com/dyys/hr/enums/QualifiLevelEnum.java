package com.dyys.hr.enums;

/**
 * 专业资格等级
 *
 * @author ZHIQIANG LI
 * @date 2019/7/1 0:27
 **/
public enum  QualifiLevelEnum {

    /**
     * 初（专员）级
     */
    COMMISSIONER(1,"初（专员）级"),
    /**
     * 初（助理）级
     */
    ASSISTANT(2,"初（助理）级"),
    /**
     * 中级
     */
    MIDDLE(3,"中级"),
    /**
     * 副高级
     */
    DEPUTY_HIGH(4,"副高级"),
    /**
     * 正高级
     */
    POSITIVE(5,"正高级");

    private Integer value;

    private String name;

    QualifiLevelEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (QualifiLevelEnum v : QualifiLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (QualifiLevelEnum v : QualifiLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
