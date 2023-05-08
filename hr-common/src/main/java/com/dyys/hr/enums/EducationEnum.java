package com.dyys.hr.enums;

/**
 * 学历
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:29
 **/
public enum EducationEnum {

    /**
     * 小学
     */
    PRIMARY(1,"小学"),

    /**
     * 初中
     */
    JUNIOR(2,"初中"),

    /**
     * 高中
     */
    HIGHT(3,"高中"),

    /**
     * 职高
     */
    MIDDLE_VOCATIONAL(4,"职高"),

    /**
     * 技校
     */
    TECHNICAL(5,"技校"),

    /**
     * 中师
     */
    MIDDLE_DIVISION(6,"中师"),

    /**
     * 中技
     */
    MIDDLE_TECHNICAL(7,"中技"),

    /**
     * 中专
     */
    SENIOR(8,"中专"),

    /**
     * 高职
     */
    VOCATIONAL_COLLEGE(9,"高职"),

    /**
     * 高专
     */
    HIGH_SCHOOL(10,"高专"),

    /**
     * 大专
     */
    COLLEGE(11,"大专"),

    /**
     * 大普
     */
    BIG_ORDINARY(12,"大普"),

    /**
     * 本科
     */
    UNDERGRADUCATE(13,"本科"),

    /**
     * 硕士研究生
     */
    POSTGRADUCATE(14,"硕士研究生"),

    /**
     * 博士研究生
     */
    DOCTOR(15,"博士研究生"),

    /**
     * 其他
     */
    OTHER(16,"其它");


    private Integer value;

    private String name;

    EducationEnum(Integer value,String name) {
        this.value = value;
        this.name =name;
    }
    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (EducationEnum v : EducationEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (EducationEnum v : EducationEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (EducationEnum v : EducationEnum.values()) {
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
