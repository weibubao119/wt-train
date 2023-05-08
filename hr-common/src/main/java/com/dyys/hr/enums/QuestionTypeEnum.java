package com.dyys.hr.enums;

/**
 * 问题类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:21
 **/
public enum QuestionTypeEnum {
    /**
     * 判断题
     */
    JUDGE(5),
    /**
     * 主观题
     */
    SUBJECTIVE(4),
    /**
     * 力特克量题
     */
    TURK(3),
    /**
     * 多选题
     */
    MULTISELECT(2),
    /**
     * 单选题
     */
    SINGLESELECT(1);

    private Integer value;

    QuestionTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (QuestionTypeEnum v : QuestionTypeEnum.values()) {
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
