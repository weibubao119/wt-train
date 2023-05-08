package com.dyys.hr.enums;

/**
 * 学位类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/30 23:47
 **/
public enum  DegreeTypeEnum {

    /**
     * 学术学位
     */
    ACADEMIC(1,"学术学位"),
    /**
     * 工学学位
     */
    ENGINEERING(2,"工学学位");


    private Integer value;

    private String name;

    DegreeTypeEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (DegreeTypeEnum v : DegreeTypeEnum.values()) {
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
        for (DegreeTypeEnum v : DegreeTypeEnum.values()) {
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
