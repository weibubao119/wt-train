package com.dyys.hr.enums;

/**
 * 性别
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:07
 **/
public enum GenderEnum {

    /**
     * 未知
     **/
    UNKNOWN(0,"未知"),

    /**
     * 男
     **/
    BOY(1,"男"),

    /**
     * 女
     **/
    GIRL(2,"女");


    private Integer value;

    private String name;

    GenderEnum(Integer value,String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (GenderEnum v : GenderEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (GenderEnum v : GenderEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (GenderEnum v : GenderEnum.values()) {
            if (v.getName().equals(name)) {
                return v.getValue();
            }
        }
        return 0;
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
