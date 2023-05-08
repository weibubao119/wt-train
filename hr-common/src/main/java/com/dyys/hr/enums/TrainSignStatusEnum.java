package com.dyys.hr.enums;

/**
 * 签到状态
 *
 * @author JUCHUAN LI
 * @date 2019/6/23 1:59
 **/
public enum TrainSignStatusEnum {

    /**
     * 迟到
     */
    LATE(0,"迟到"),

    /**
     * 正常
     */
    NORMAL(1,"正常");

    private Integer value;

    private String name;

    TrainSignStatusEnum(Integer value, String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainSignStatusEnum v : TrainSignStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TrainSignStatusEnum v : TrainSignStatusEnum.values()) {
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
