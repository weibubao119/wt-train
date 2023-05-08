package com.dyys.hr.enums;

/**
 * 培训类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:59
 **/
public enum TrainTypeEnum {

    /**
     * 内部培训
     */
    OUTPRODUCTION(1),
    /**
     * 外部培训
     */
    HALFPRODUCTION(2);

    private Integer value;

    TrainTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainTypeEnum v : TrainTypeEnum.values()) {
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
