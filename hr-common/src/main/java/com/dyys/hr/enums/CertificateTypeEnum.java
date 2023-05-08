package com.dyys.hr.enums;

/**
 * 证书类型
 *
 * @author ZHIQIANG LI
 * @date 2019/7/8 0:16
 **/
public enum CertificateTypeEnum {

    /**
     * 毕业
     */
    GRADUATION(1,"毕业"),

    /**
     * 结业
     */
    COMPLETE(2,"结业"),

    /**
     * 肄业
     */
    INDUSTRY(3,"肄业");

    private Integer value;

    private String name;

    CertificateTypeEnum(Integer value,String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (CertificateTypeEnum v : CertificateTypeEnum.values()) {
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
        for (CertificateTypeEnum v : CertificateTypeEnum.values()) {
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
