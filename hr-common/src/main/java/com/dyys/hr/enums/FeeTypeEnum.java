package com.dyys.hr.enums;

/**
 * 收费项目
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:39
 **/
public enum FeeTypeEnum {
    /**
     * 场地费
     */
    SITEFEE(1),
    /**
     * 管理费
     */
    MANAGERFEE(2),
    /**
     * 教材费
     */
    MATERIALFEE(3),
    /**
     * 讲师课酬
     */
    TEACHERFEE(4),
    /**
     * 杂费
     */
    INCIDENTAL(5);

    private Integer value;

    FeeTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (FeeTypeEnum v : FeeTypeEnum.values()) {
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
