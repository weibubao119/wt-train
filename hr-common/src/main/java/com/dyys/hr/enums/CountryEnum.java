package com.dyys.hr.enums;

/**
 * 国家
 *
 * @author ZHIQIANG LI
 * @date 2019/10/28 23:42
 **/
public enum CountryEnum {

    COUNT0(20, "中国"),
    COUNT1(1, "比利时"),
    COUNT2(2, "加拿大"),
    COUNT3(3, "奥地利"),
    COUNT4(4, "德国"),
    COUNT5(5, "丹麦"),
    COUNT6(6, "西班牙"),
    COUNT7(7, "芬兰"),
    COUNT8(8, "法国"),
    COUNT9(9, "英国"),
    COUNT10(10, "希腊"),
    COUNT11(11, "爱尔兰"),
    COUNT12(12, "意大利"),
    COUNT13(13, "卢森堡"),
    COUNT14(14, "荷兰"),
    COUNT15(15, "挪威"),
    COUNT16(16, "波兰"),
    COUNT17(17, "葡萄牙"),
    COUNT18(18, "瑞典"),
    COUNT19(19, "美国");

    private Integer value;

    private String name;

    CountryEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (CountryEnum v : CountryEnum.values()) {
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
        for (CountryEnum v : CountryEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (CountryEnum v : CountryEnum.values()) {
            if (v.getName().equals(name)) {
                return v.getValue();
            }
        }
        return 0;
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
