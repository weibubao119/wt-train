package com.dyys.hr.enums;

/**
 * 报名方式
 * @author  李巨川
 * @date  2019/6/23 15:23
 */
public enum TrainSignUpTypeEnum {

    /**
     * 内部报名
     */
    SIGNUPINTER(1),

    /**
     * 外部报名
     */
    SIGNUPEXTER(2);

    private Integer value;


    TrainSignUpTypeEnum(Integer value){
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainSignUpTypeEnum v : TrainSignUpTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
