package com.dyys.hr.enums;

/**
 * 学位取得方式
 *
 * @author ZHIQIANG LI
 * @date 2019/10/27 12:56
 **/
public enum AcquireModeEnum {

    FULL_TIME(1, "全日制"),
    SELF_EXAMINATION(2, "自学考试"),
    NIGHT_SCHOOL(3, "夜大"),
    ELECTRICITY_SCHOOL(4, "电大"),
    AMATEUR_COLLEGE(5, "业余大学"),
    PARTY_SCHOOL(6, "党校"),
    CORRESPONDENCE(7, "函数"),
    ONLINE_TRANSACTION(8, "网络教育"),
    HIGHER_EDUCATION(9, "高等学校进修"),
    SEMINAR(10, "研修班"),
    FOREIGN_INSTITUTIONS(11, "国外院校"),
    ADULT_BIRTH(12, "成人（脱产）"),
    IN_SERVICE(13, "在职"),
    OTHER(14, "其他");

    private Integer value;

    private String name;

    AcquireModeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (AcquireModeEnum v : AcquireModeEnum.values()) {
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
        for (AcquireModeEnum v : AcquireModeEnum.values()) {
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
