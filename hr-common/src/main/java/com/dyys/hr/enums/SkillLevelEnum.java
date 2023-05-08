package com.dyys.hr.enums;

/**
 * @author ZHIQIANG LI
 * @date 2019/7/1 0:44
 **/
public enum  SkillLevelEnum {
    /**
     * 初级工
     */
    ASSISTANT(1,"初级工"),
    /**
     * 中级工
     */
    MIDDLE(2,"中级工"),
    /**
     * 高级工
     */
    HIGH(3,"高级工"),
    /**
     * 技师
     */
    TECHNICIAN(4,"技师"),
    /**
     * 高级技师
     */
    HIGH_TECHNICIAN(5,"高级技师");

    private Integer value;

    private String name;

    SkillLevelEnum(Integer value,String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (SkillLevelEnum v : SkillLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (SkillLevelEnum v : SkillLevelEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (SkillLevelEnum v : SkillLevelEnum.values()) {
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
