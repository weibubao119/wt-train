package com.dyys.hr.enums;

/**
 * 与本人关系
 *
 * @author ZHIQIANG LI
 * @date 2019/6/30 23:49
 **/
public enum RelationTypeEnum {

    /**
     * 配偶
     */
    MATE(1,"配偶"),
    /**
     * 父亲
     */
    FATHER(2,"父亲"),
    /**
     * 母亲
     */
    MOTHER(3,"母亲"),
    /**
     * 子女
     */
    CHILDREN(4,"子女"),
    /**
     * 配偶父亲
     */
    LAW_FATHER(5,"配偶父亲"),
    /**
     * 配偶母亲
     */
    LAW_MOTHER(6,"配偶母亲"),
    /**
     * 兄
     */
    OLD_BROTHER(7,"兄"),
    /**
     * 弟
     */
    YOUNG_BROTHER(8,"弟"),
    /**
     * 姐
     */
    OLD_SISTER(9,"姐"),
    /**
     * 妹
     */
    YOUNG_SISTER(10,"妹"),
    /**
     * 其它
     */
    OTHER(11,"其它"),;

    private Integer value;

    private String name;

    RelationTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (RelationTypeEnum v : RelationTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取参数值
     */
    public static String getNameByValue(Integer value) {
        for (RelationTypeEnum v : RelationTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
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
