package com.dyys.hr.enums;

/**
 * 培训渠道
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:38
 **/
public enum TrainChannelEnum {

    /**
     * 党校
     */
    SCHOOL(1,"党校"),
    /**
     * 行政学院
     */
    PRESCHOOL(2,"行政学院"),
    /**
     * 高校、科研机构
     */
    HIGHTSCHOOL(3,"高校、科研机构"),
    /**
     * 其他培训机构
     */
    OTHERTRAIN(4,"其他培训机构");

    private Integer value;

    private String name;

    TrainChannelEnum(Integer value, String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainChannelEnum v : TrainChannelEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TrainChannelEnum v : TrainChannelEnum.values()) {
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
