package com.dyys.hr.enums;

/**
 * 户口类型
 *
 * @author ZHIQIANG LI
 * @date 2019/7/8 0:12
 **/
public enum RegisteredTypeEnum {

    /**
     * 本地非农业（常驻）
     */
    LOCAL_NO_GEOPONICS_RESIDENT(1,"本地非农业（常驻）"),
    /**
     * 本地非农业（临时）
     */
    LOCAL_NO_GEOPONICS_TEMP(2,"本地非农业（临时）"),
    /**
     * 本地农业
     */
    LOCAL_GEOPONICS(3,"本地农业"),
    /**
     * 外地非农业
     */
    OUTLAND_NO_GEOPONICS(4,"外地非农业"),
    /**
     * 外地农业
     */
    OUTLAND_GEOPONICS(5,"外地农业");

    private Integer value;

    private String name;

    RegisteredTypeEnum(Integer value,String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (RegisteredTypeEnum v : RegisteredTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (RegisteredTypeEnum v : RegisteredTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name){
        for (RegisteredTypeEnum v : RegisteredTypeEnum.values()) {
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
