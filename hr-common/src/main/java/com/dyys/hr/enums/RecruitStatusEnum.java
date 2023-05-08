package com.dyys.hr.enums;

/**
 * 招聘状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:47
 **/
public enum RecruitStatusEnum {

    /**
     * 进行中
     */
    DOING(1),
    /**
     * 已结束
     */
    END(2);

    private Integer value;

    RecruitStatusEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (RecruitStatusEnum v : RecruitStatusEnum.values()) {
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
