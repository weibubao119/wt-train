package com.dyys.hr.enums;

/**
 * 简历阶段
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:49
 **/
public enum ResumeStageEnum {
    /**
     * 简历筛选
     */
    FILTER(1, "简历筛选"),
    /**
     * 面试
     */
    INTERVIEW(2, "面试"),
    /**
     * 录用审批
     */
    APPROVAL(3, "录用审批"),
    /**
     * offer发放
     */
    OFFER(4, "Offer发放"),
    /**
     * 办理入职
     */
    ENTRY(5, "办理入职"),
    /**
     * 淘汰
     */
    ELIMINATED(6, "淘汰");

    private Integer value;
    private String text;

    ResumeStageEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static ResumeStageEnum getStage(Integer stage) {
        for (ResumeStageEnum resumeStage : values()) {
            if (resumeStage.getValue().equals(stage)) {
                return resumeStage;
            }
        }
        return null;
    }

    public static String getNameByValue(Integer value){
        for (ResumeStageEnum v : ResumeStageEnum.values()) {
            if (v.getValue().equals(value)) {
                return v.getText();
            }
        }
        return "";
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ResumeStageEnum v : ResumeStageEnum.values()) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }}
