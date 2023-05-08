package com.dyys.hr.enums;

/**
 * 邮件类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:18
 **/
public enum  EmailTypeEnum {


    /**
     * 问卷提醒邮件
     */
    QUESTIONNAIRE(1),

    /**
     * 需求反馈邮件
     */
    FEEDBACK(2);

    private Integer value;

    EmailTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (EmailTypeEnum v : EmailTypeEnum.values()) {
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
