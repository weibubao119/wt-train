package com.dyys.hr.enums;

/**
 * 结课状态
 *
 * @author JUCHUAN LI
 * @date 2019/6/23 1:59
 **/
public enum TrainClassStatusEnum {

    /**
     * 0
     */
    CLASSING(0,"未结课"),

    /**
     * 正常
     */
    ENDCLASS(1,"结课");

    private Integer value;

    private String name;

    TrainClassStatusEnum(Integer value, String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainClassStatusEnum v : TrainClassStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TrainClassStatusEnum v : TrainClassStatusEnum.values()) {
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
