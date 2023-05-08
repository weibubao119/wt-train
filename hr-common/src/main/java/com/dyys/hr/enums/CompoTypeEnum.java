package com.dyys.hr.enums;


/**
 * 字段控件类型
 *
 * @author 邱亚敏
 * @date 2019/06/23
 */
public enum CompoTypeEnum {

    /**
     * 文本框
     **/
    TEXT(1),

    /**
     * 下拉框
     **/
    SELECT(2),

    /**
     * 日期控件
     **/
    DATE(3),

    /**
     * 上传控件
     **/
    UPLOAD(4),

    /**
     * 表格
     **/
    SHEET(5),

    /**
     * 图片
     **/
    PICTURE(6),

    /**
     * 多行文本
     **/
    TEXTAREA(7);

    private Integer value;

    CompoTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (CompoTypeEnum v : CompoTypeEnum.values()) {
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
