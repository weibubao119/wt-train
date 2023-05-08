package com.dyys.hr.enums;

/**
 * 教师职称等级
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:25
 **/
public enum ProfessionalLevelEnum {
    /**
     * 初级
     */
    COMMISSIONER(0,"初级"),

    /**
     * 助理级
     */
    ASSISTANT(1,"助理级"),
    /**
     * 中级
     */
    MIDDLE(2,"中级"),
    /**
     * 副高
     */
    FITSENIOR(3,"副高"),
    /**
     * 正高
     */
    MIANSENIOR(4,"正高");


    private Integer value;

    private String name;

    ProfessionalLevelEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProfessionalLevelEnum v : ProfessionalLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (ProfessionalLevelEnum v : ProfessionalLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (ProfessionalLevelEnum v : ProfessionalLevelEnum.values()) {
            if (v.getName().equals(name)) {
                return v.getValue();
            }
        }
        return -1;
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
