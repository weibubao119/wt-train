package com.dyys.hr.enums;


/**
 * 消息提醒枚举
 *
 * @author syyang
 * @date 2019/06/23
 */
public enum NotificationType {

    /**
     * 培训需求
     **/
    TRAIN_DEMAND(1),

    /**
     * 问卷填写
     **/
    QUESTIONNAIRE_WRITE(2),

    /**
     * 问卷调查超时
     **/
    QUESTIONNAIRE_INVESTIGATION(3),

    /**
     * 招聘需求
     */
    RECRUIT_DEMAND(4);

    private Integer value;

    NotificationType(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (NotificationType v : NotificationType.values()) {
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
