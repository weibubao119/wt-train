package com.dyys.hr.enums;

/**
 * 外语水平
 *
 * @author ZHIQIANG LI
 * @date 2019/10/27 12:56
 **/
public enum ExtentEnum {

    CET2(1, "国家二级"),
    CET4(2, "国家四级"),
    CET6(3, "国家六级"),
    PRO_4(4, "专业四级"),
    PRO_8(5, "专业八级"),
    C_LEVEL(6, "职称外语C级"),
    B_LEVEL(7, "职称外语B级"),
    A_LEVEL(8, "职称外语A级"),
    PRIMARY(9, "初级口译"),
    JUNIOR(10, "中级口译"),
    SENIOR(11, "高级口译"),
    TOEFL(12, "托福"),
    IELTS(13, "雅思"),
    GRE(14, "GRE"),
    TOEIC(15, "托业"),
    LIKE2(16, "相当于国家二级"),
    LIKE4(17, "相当于国家四级"),
    OTHER(18, "其他");

    private Integer value;

    private String name;

    ExtentEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ExtentEnum v : ExtentEnum.values()) {
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
        for (ExtentEnum v : ExtentEnum.values()) {
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
