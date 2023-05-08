package com.dyys.hr.enums;

/**
 * 省份
 *
 * @author ZHIQIANG LI
 * @date 2019/10/28 22:20
 **/
public enum ProvinceEnum {

    BEI_JING(1, "北京市"),
    TIAN_JIN(2, "天津省"),
    HE_BEI(3, "河北省"),
    SHAN_XI(4, "山西省"),
    NEI_MENG_GU(5, "内蒙古自治区"),
    LIAO_NING(6, "辽宁省"),
    JI_LIN(7, "吉林省"),
    HEI_LONG_JIANG(8, "黑龙江省"),
    SHANG_HAI(9, "上海市"),
    JIANG_SU(10, "江苏省"),
    ZHE_JIANG(11, "浙江省"),
    AN_HUI(12, "安徽省"),
    FU_JIAN(13, "福建省"),
    JIANG_XI(14, "江西省"),
    SHAN_DONG(15, "山东省"),
    HE_NAN(16, "河南省"),
    HU_BEI(17, "湖北省"),
    HU_NAN(18, "湖南省"),
    GUANG_DONG(19, "广东省"),
    GUANG_XI(20, "广西壮族自治区"),
    HAI_NAN(21, "海南省"),
    CHONG_QING(22, "重庆市"),
    SI_CHUAN(23, "四川省"),
    GUI_ZHOU(24, "贵州省"),
    YUN_NAN(25, "云南省"),
    XI_ZANG(26, "西藏自治区"),
    SHANXI(27, "陕西省"),
    GAN_SU(28, "甘肃省"),
    QING_HAI(29, "青海省"),
    NING_XIA(30, "宁夏回族自治区"),
    XIN_JIANG(31, "新疆维吾尔自治区"),
    TAI_WAN(32, "台湾省"),
    HONG_KONG(33, "香港特别行政区"),
    MACAO(34, "澳门特别行政区"),
    OTHER(35, "其它");


    private Integer value;

    private String name;

    ProvinceEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProvinceEnum v : ProvinceEnum.values()) {
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
        for (ProvinceEnum v : ProvinceEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (ProvinceEnum v : ProvinceEnum.values()) {
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
