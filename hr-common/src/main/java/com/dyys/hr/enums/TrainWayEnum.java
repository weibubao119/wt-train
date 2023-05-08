package com.dyys.hr.enums;

/**
 * 培训方式
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:33
 **/
public enum TrainWayEnum {

    /**
     * 自主培训
     */
    OWNTRAIN(1,"自主培训"),
    /**
     * 外送培训（国内）
     */
    OUTTRAININTER(2,"外送培训（国内）"),
    /**
     * 外送培训（国外）
     */
    OUTTRAINEXTER(3,"外送培训（国外）"),
    /**
     * 自我学习
     */
    SELFSTUDY(4,"自我学习"),
    /**
     * 网络教育
     */
    ELEARNING(5,"网络教育");

    private Integer value;

    private String name;

    TrainWayEnum(Integer value, String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainWayEnum v : TrainWayEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TrainWayEnum v : TrainWayEnum.values()) {
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
