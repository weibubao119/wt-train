package com.dyys.hr.enums;

/**
 * 需求人才收集明细类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 2:01
 **/
public enum TalentMasterTypeEnum {

    /**
     * 需求信息表(临时需求)
     */
    COLLECT(1),
    /**
     * 反馈信息表
     */
    FEEDBACK(2);

    private Integer value;

    TalentMasterTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TalentMasterTypeEnum v : TalentMasterTypeEnum.values()) {
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
