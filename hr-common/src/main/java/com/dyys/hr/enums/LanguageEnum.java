package com.dyys.hr.enums;

/**
 * 语种
 *
 * @author ZHIQIANG LI
 * @date 2019/10/27 12:56
 **/
public enum LanguageEnum {

    ENGLISH(1, "英语"),
    FRENCH(2, "法语"),
    JAPANESE(3, "日语"),
    RUSSIAN(4, "俄语"),
    GERMAN(5, "德语"),
    SPANISH(6, "西班牙语"),
    ARABIC(7, "阿拉伯语"),
    LAOS(8, "老挝语");

    private Integer value;

    private String name;

    LanguageEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (LanguageEnum v : LanguageEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取参数值
     */
    public static String getNameByValue(Integer value) {
        for (LanguageEnum v : LanguageEnum.values()) {
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
