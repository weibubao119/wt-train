package com.dyys.hr.enums;

/**
 * 政治面貌
 *
 * @author ZHIQIANG LI
 * @date 2019/6/24 1:47
 **/
public enum PoliticalStatusEnum {

    /**
     * 共产党员
     */
    POLITICAL1(1,"中国共产党党员"),

    /**
     * 中国共产党预备党员
     */
    POLITICAL2(2,"中国共产党预备党员"),

    /**
     * 中国共产主义青年团团员
     */
    POLITICAL3(3,"中国共产主义青年团团员"),

    /**
     * 中国国民党革命委员会会员
     */
    POLITICAL4(4,"中国国民党革命委员会会员"),

    /**
     * 中国民主同盟盟员
     */
    POLITICAL5(5,"中国民主同盟盟员"),

    /**
     * 中国民主建国会会员
     */
    POLITICAL6(6,"中国民主建国会会员"),

    /**
     * 中国民主促进会会员
     */
    POLITICAL7(7,"中国民主促进会会员"),

    /**
     * 中国农工民主党党员
     */
    POLITICAL8(8,"中国农工民主党党员"),

    /**
     * 中国致公党党员
     */
    POLITICAL9(9,"中国致公党党员"),

    /**
     * 九三学社社成员
     */
    POLITICAL10(10,"九三学社社成员"),

    /**
     * 台湾民主自治同盟盟员
     */
    POLITICAL11(11,"台湾民主自治同盟盟员"),

    /**
     * 无党派民主人士
     */
    POLITICAL12(12,"无党派民主人士"),

    /**
     * 群众
     */
    OTHER(13,"群众");

    private Integer value;

    private String name;

    PoliticalStatusEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (PoliticalStatusEnum v : PoliticalStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (PoliticalStatusEnum v : PoliticalStatusEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (PoliticalStatusEnum v : PoliticalStatusEnum.values()) {
            if (v.getName().equals(name)) {
                return v.getValue();
            }
        }
        return 4;
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
