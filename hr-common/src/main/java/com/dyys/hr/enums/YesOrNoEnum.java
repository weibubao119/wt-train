package com.dyys.hr.enums;


/**
 * 是否，真假 枚举
 *
 * @author ZHIQIANG LI
 * @date 2019/06/23
 */
public enum YesOrNoEnum {

    /**
     * 是
     **/
    YES(1,"是"),

    /**
     * 否
     **/
    NO(0,"否");

    private Integer value;

    private String name;

    YesOrNoEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (YesOrNoEnum v : YesOrNoEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (YesOrNoEnum v : YesOrNoEnum.values()) {
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
