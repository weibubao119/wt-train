package com.dyys.hr.enums;

/**
 * 人才类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:59
 **/
public enum TalentTypeEnum {

    /**
     * 高级成熟人才
     */
    SENIOR(1,"高级成熟人才"),
    /**
     * 应届毕业生
     */
    GRADUATE(2,"应届毕业生"),
    /**
     * 技能操作人员
     */
    SKILL(3,"技能操作人员");

    private Integer value;

    private String name;

    TalentTypeEnum(Integer value,String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TalentTypeEnum v : TalentTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TalentTypeEnum v : TalentTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (TalentTypeEnum v : TalentTypeEnum.values()) {
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
