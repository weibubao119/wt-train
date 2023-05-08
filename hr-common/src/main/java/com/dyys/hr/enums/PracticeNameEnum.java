package com.dyys.hr.enums;

/**
 * 执业资格
 *
 * @author ZHIQIANG LI
 * @date 2019/11/16 0:58
 **/
public enum PracticeNameEnum {

    PRACTICENAME1(1, "注册一级建造师"),
    PRACTICENAME2(2, "注册二级建造师"),
    PRACTICENAME3(3, "注册一级建筑师"),
    PRACTICENAME4(4, "注册二级建筑师"),
    PRACTICENAME5(5, "注册土木工程师（岩土）"),
    PRACTICENAME6(6, "注册土木工程师（港口与航道工程）"),
    PRACTICENAME7(7, "注册化工工程师"),
    PRACTICENAME8(8, "注册电气工程师"),
    PRACTICENAME9(9, "注册安全工程师"),
    PRACTICENAME10(10, "国际商务专业人员"),
    PRACTICENAME11(11, "注册咨询工程师(投资)"),
    PRACTICENAME12(12, "房地产经纪人"),
    PRACTICENAME13(13, "房地产经纪人协理"),
    PRACTICENAME14(14, "注册税务师"),
    PRACTICENAME15(15, "注册会计师"),
    PRACTICENAME16(16, "注册造价工程师"),
    PRACTICENAME17(17, "注册监理工程师"),
    PRACTICENAME18(18, "注册价格鉴证师"),
    PRACTICENAME19(19, "注册公用设备工程师"),
    PRACTICENAME20(20, "消防监督检查人员"),
    PRACTICENAME21(21, "注册城市规划师"),
    PRACTICENAME22(22, "执业药师"),
    PRACTICENAME23(23, "执业医师"),
    PRACTICENAME24(24, "执业助理医师"),
    PRACTICENAME25(25, "护士执业资格"),
    PRACTICENAME26(26, "国际会计师"),
    PRACTICENAME27(27, "注册设备监理师");


    private Integer value;

    private String name;

    PracticeNameEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (PracticeNameEnum v : PracticeNameEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value) {
        for (PracticeNameEnum v : PracticeNameEnum.values()) {
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
