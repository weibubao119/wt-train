package com.dyys.hr.enums;


/**
 * 模型分类类型
 *
 * @author 邱亚敏
 * @date 2019/06/23
 */
public enum ModelTypeEnum {

    /**
     * 表单
     **/
    FORM(1),

    /**
     * 流程
     **/
    PROCESS(2),

    /**
     * 通知模板
     **/
    NOTICE(3);

    private Integer value;

    ModelTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ModelTypeEnum v : ModelTypeEnum.values()) {
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
