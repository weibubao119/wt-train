package com.dyys.hr.enums;

/**
 * 民族
 *
 * @author ZHIQIANG LI
 * @date 2019/6/30 23:49
 **/
public enum NationEnum {

    /**
     * 汉族
     */
    HAN(1, "汉族"),
    /**
     * 蒙古族
     */
    MONGGU(2, "蒙古族"),
    /**
     * 回族
     */
    HUI(3, "回族"),
    /**
     * 藏族
     */
    ZANG(4, "藏族"),
    /**
     * 维吾尔族
     */
    WEIWU(5, "维吾尔族"),
    /**
     * 苗族
     */
    MIAO(6, "苗族"),
    /**
     * 彝族
     */
    YI(7, "彝族"),
    /**
     * 壮族
     */
    ZHUANG(8, "壮族"),
    /**
     * 布依族
     */
    BUYI(9, "布依族"),
    /**
     * 朝鲜族
     */
    CHAOXIAN(10, "朝鲜族"),
    /**
     * 满族
     */
    MAN(11, "满族"),
    /**
     * 侗族
     */
    DONG(12, "侗族"),
    /**
     * 瑶族
     */
    YAO(13, "瑶族"),
    /**
     * 白族
     */
    BAI(14, "白族"),
    /**
     * 土家族
     */
    TUJIA(15, "土家族"),
    /**
     * 哈尼族
     */
    HANI(16, "哈尼族"),
    /**
     * 哈萨克族
     */
    HASAKE(17, "哈萨克族"),
    /**
     * 傣族
     */
    DAI(18, "傣族"),
    /**
     * 黎族
     */
    LI(19, "黎族"),
    /**
     * 傈僳族
     */
    LISU(20, "傈僳族"),
    /**
     * 佤族
     */
    WA(21, "佤族"),
    /**
     * 畲族
     */
    SHE(22, "畲族"),
    /**
     * 高山族
     */
    GAOSHAN(23, "高山族"),
    /**
     * 拉祜族
     */
    LAGU(24, "拉祜族"),
    /**
     * 水族
     */
    SHUI(25, "水族"),
    /**
     * 东乡族
     */
    DONGXIANG(26, "东乡族"),
    /**
     * 纳西族
     */
    NAXI(27, "纳西族"),
    /**
     * 景颇族
     */
    JINGPO(28, "景颇族"),
    /**
     * 柯尔克孜族
     */
    KEERKEMEI(29, "柯尔克孜族"),
    /**
     * 土族
     */
    TU(30, "土族"),
    /**
     * 达斡尔族
     */
    DAWOER(31, "达斡尔族"),
    /**
     * 仫佬族
     */
    MULAO(32, "仫佬族"),
    /**
     * 羌族
     */
    QIANG(33, "羌族"),
    /**
     * 布朗族
     */
    BULANG(34, "布朗族"),
    /**
     * 撒拉族
     */
    SALA(35, "撒拉族"),
    /**
     * 毛难族
     */
    MAONAN(36, "毛难族"),
    /**
     * 仡佬族
     */
    QILAO(37, "仡佬族"),
    /**
     * 锡伯族
     */
    XIBO(38, "锡伯族"),
    /**
     * 阿昌族
     */
    ACANG(39, "阿昌族"),
    /**
     * 普米族
     */
    PUMI(40, "普米族"),
    /**
     * 塔吉克族
     */
    TAJIKE(41, "塔吉克族"),
    /**
     * 怒族
     */
    NU(42, "怒族"),
    /**
     * 乌孜别克族
     */
    WUZIBIEKE(43, "乌孜别克族"),
    /**
     * 俄罗斯族
     */
    ELUOSI(44, "俄罗斯族"),
    /**
     * 鄂温克族
     */
    EWENKE(45, "鄂温克族"),
    /**
     * 崩龙族
     */
    BENGLONG(46, "崩龙族"),
    /**
     * 保安族
     */
    BAOAN(47, "保安族"),
    /**
     * 裕固族
     */
    YUGU(48, "裕固族"),
    /**
     * 京族
     */
    JING(49, "京族"),
    /**
     * 塔塔尔族
     */
    TATAER(50, "塔塔尔族"),
    /**
     * 独龙族
     */
    DULONG(51, "独龙族"),
    /**
     * 鄂伦春族
     */
    ELUNCHUN(52, "鄂伦春族"),
    /**
     * 赫哲族
     */
    HEZHE(53, "赫哲族"),
    /**
     * 门巴族
     */
    MENBA(54, "门巴族"),
    /**
     * 珞巴族
     */
    LUOBA(55, "珞巴族"),
    /**
     * 基诺族
     */
    JINUO(56, "基诺族");

    private Integer value;

    private String name;

    NationEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (NationEnum v : NationEnum.values()) {
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
        for (NationEnum v : NationEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (NationEnum v : NationEnum.values()) {
            if (v.getName().equals(name)) {
                return v.getValue();
            }
        }
        return 1;
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
