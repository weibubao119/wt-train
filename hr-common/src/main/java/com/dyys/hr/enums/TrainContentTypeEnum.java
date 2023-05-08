package com.dyys.hr.enums;

/**
 * 计划类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:59
 **/
public enum TrainContentTypeEnum {

    /**
     * 管理
     */
    MANAGE(1),
    /**
     * 技术
     */
    TECHNOLOGY(2),

    /**
     * 生产
     */
    PRODUCE(3),

    /**
     * 商务
     */
    BUSINESS(4),

    /**
     * 服务
     */
    SERVICE(5);

    private Integer value;

    TrainContentTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainContentTypeEnum v : TrainContentTypeEnum.values()) {
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
