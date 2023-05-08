package com.dyys.hr.enums;

/**
 * @author 邱亚敏
 * @date 2019/6/26 23:09
 */
public enum ProcessTypeEnum {
    /**
     * 主阶段
     **/
    MAIN(1),

    /**
     * 子阶段
     **/
    SUB(2);

    private Integer value;

    ProcessTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProcessTypeEnum v : ProcessTypeEnum.values()) {
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
