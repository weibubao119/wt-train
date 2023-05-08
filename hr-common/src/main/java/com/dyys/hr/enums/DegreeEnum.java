package com.dyys.hr.enums;

/**
 * 学位
 *
 * @author ZHIQIANG LI
 * @date 2019/10/27 12:56
 **/
public enum DegreeEnum {

    DEGREE1(1, "名誉博士"),
    DEGREE2(2, "哲学博士"),
    DEGREE3(3, "经济学博士"),
    DEGREE4(4, "管理学博士"),
    DEGREE5(5, "法学博士"),
    DEGREE6(6, "教育学博士"),
    DEGREE7(7, "文学博士"),
    DEGREE8(8, "历史学博士"),
    DEGREE9(9, "理学博士"),
    DEGREE10(10, "工学博士"),
    DEGREE11(11, "农学博士"),
    DEGREE12(12, "医学博士"),
    DEGREE13(13, "军事学博士"),
    DEGREE14(14, "副博士"),
    DEGREE15(15, "哲学副博士"),
    DEGREE16(16, "经济学副博士"),
    DEGREE17(17, "法学副博士"),
    DEGREE18(18, "教育学副博士"),
    DEGREE19(19, "文学副博士"),
    DEGREE20(20, "历史学副博士"),
    DEGREE21(21, "理学副博士"),
    DEGREE22(22, "工学副博士"),
    DEGREE23(23, "农学副博士"),
    DEGREE24(24, "医学副博士"),
    DEGREE25(25, "军事学副博士"),
    DEGREE26(26, "哲学硕士"),
    DEGREE27(27, "经济学硕士"),
    DEGREE28(28, "法学硕士"),
    DEGREE29(29, "法律硕士"),
    DEGREE30(30, "教育学硕士"),
    DEGREE31(31, "文学硕士"),
    DEGREE32(32, "历史学硕士"),
    DEGREE33(33, "理学硕士"),
    DEGREE34(34, "工学硕士"),
    DEGREE35(35, "农学硕士"),
    DEGREE36(36, "医学硕士"),
    DEGREE37(37, "军事学硕士"),
    DEGREE38(38, "工商管理硕士"),
    DEGREE39(39, "公共管理硕士"),
    DEGREE40(40, "工程硕士"),
    DEGREE41(41, "管理学硕士"),
    DEGREE42(42, "管理学硕士"),
    DEGREE43(43, "哲学学士"),
    DEGREE44(44, "经济学学士"),
    DEGREE45(45, "法学学士"),
    DEGREE46(46, "教育学士"),
    DEGREE47(47, "文学学士"),
    DEGREE48(48, "历史学学士"),
    DEGREE49(49, "理学学士"),
    DEGREE50(50, "工学学士"),
    DEGREE51(51, "农学学士"),
    DEGREE52(52, "医学学士"),
    DEGREE53(53, "商学学士"),
    DEGREE54(54, "军事学学士");

    private Integer value;

    private String name;

    DegreeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (DegreeEnum v : DegreeEnum.values()) {
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
        for (DegreeEnum v : DegreeEnum.values()) {
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
