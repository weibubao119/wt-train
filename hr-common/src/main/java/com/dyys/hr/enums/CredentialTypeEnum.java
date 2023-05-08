package com.dyys.hr.enums;

/**
 * 证件类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:11
 **/
public enum CredentialTypeEnum {
    /**
     * 身份证
     **/
    IDCARD(1),
    /**
     * 护照
     */
    PASSPORT(2),
    /**
     * 军人证
     */
    MILITARY(3),
    /**
     * 港澳居民来往内地通行证
     */
    PASS(4),
    /**
     * 外国人永久居留身份证
     */
    FOREIGNER(5),
    /**
     * 其他
     **/
    OTHER(6);

    private Integer value;

    CredentialTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (CredentialTypeEnum v : CredentialTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
