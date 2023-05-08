package com.dyys.hr.enums;

/**
 * 专业分类
 *
 * @author ZHIQIANG LI
 * @date 2019/6/30 23:49
 **/
public enum ProfessionTypeEnum {

    /**
     * 工程技术
     */
    ENGINE(1,"工程技术"),
    /**
     * 农业技术
     */
    AGRICULTURE(2,"农业技术"),
    /**
     * 科学研究
     */
    SCIENCE(3,"科学研究"),
    /**
     * 卫生技术
     */
    HEATH(4,"卫生技术"),
    /**
     * 教学
     */
    EDUCATION(5,"教学"),
    /**
     * 经济
     */
    ECONOMY(6,"经济"),
    /**
     * 会计
     */
    ACCOUNT(7,"会计"),
    /**
     * 统计
     */
    STAT(8,"统计"),
    /**
     * 翻译
     */
    TRANSLATION(9,"翻译"),
    /**
     * 图书、档案、文博
     */
    JOURNAL(10,"图书、档案、文博"),
    /**
     * 新闻、出版
     */
    PUBLISH(11,"新闻、出版"),
    /**
     * 律师、公证
     */
    LAW(12,"律师、公证"),
    /**
     * 播音
     */
    BROADCAST(13,"播音"),
    /**
     * 工艺美术
     */
    INDUSTRIAL_ART(14,"工艺美术"),
    /**
     * 体育
     */
    GYM(15,"体育"),
    /**
     * 艺术
     */
    ART(16,"艺术"),
    /**
     * 政工
     */
    POLITICAL(17,"政工"),;

    private Integer value;

    private String name;

    ProfessionTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProfessionTypeEnum v : ProfessionTypeEnum.values()) {
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
        for (ProfessionTypeEnum v : ProfessionTypeEnum.values()) {
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
