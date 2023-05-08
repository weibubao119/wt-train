package com.dyys.hr.enums;

/**
 * 婚姻状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/24 1:49
 **/
public enum MarriageStatusEnum {

    /**
     * 未婚
     */
    UNMARRIED(1,"未婚"),

    /**
     * 已婚
     */
    MARRIED(2,"已婚"),

    /**
     * 离异
     */
    DIVORCED(3,"离异"),

    /**
     * 丧偶
     */
    WIDOWED(4,"丧偶"),

    /**
     * 其他
     */
    OTHER(5,"其它");

    private Integer value;

    private String name;

    MarriageStatusEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (MarriageStatusEnum v : MarriageStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (MarriageStatusEnum v : MarriageStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (MarriageStatusEnum v : MarriageStatusEnum.values()) {
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
