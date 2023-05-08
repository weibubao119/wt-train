package com.dyys.hr.enums;

/**
 * 资格证取得方式
 *
 * @author ZHIQIANG LI
 * @date 2019/6/30 23:49
 **/
public enum  ProfessionAcquireModeEnum {

    /**
     * 评定
     */
    ASSESS(1,"评定"),
    /**
     * 考试
     */
    TEST(2,"考试"),
    /**
     * 认定
     */
    IDENTIFICATION(3,"认定");

    private Integer value;

    private String name;

    ProfessionAcquireModeEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }
    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProfessionAcquireModeEnum v : ProfessionAcquireModeEnum.values()) {
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
        for (ProfessionAcquireModeEnum v : ProfessionAcquireModeEnum.values()) {
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
