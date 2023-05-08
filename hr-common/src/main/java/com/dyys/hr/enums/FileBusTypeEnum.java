package com.dyys.hr.enums;

/**
 * 附件业务类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/28 2:13
 **/
public enum FileBusTypeEnum {

    /**
     * 简历
     */
    RESUME(0, "/resume"),

    /**
     * 简历-头像
     */
    RESUME_AVATAR(1, "/resume/avatar"),

    /**
     * 简历-教育经历
     */
    RESUME_EDUCATION(2, "/resume/education"),
    /**
     * 简历-计算机水平
     */
    RESUME_COMPUTER(3, "/resume/computer"),
    /**
     * 简历-专业技术资格
     */
    RESUME_PROFESSIONAL(4, "/resume/professional"),
    /**
     * 简历-执业资格
     */
    RESUME_PRACTICE(5, "/resume/practice"),
    /**
     * 简历-职业资格
     */
    RESUME_CAREER(6, "/resume/career"),
    /**
     * 简历-技能等级
     */
    RESUME_SKILL_LEVEL(7, "/resume/skill"),
    /**
     * 简历-外语水平
     */
    RESUME_FOREIGN(8, "/resume/foreign"),

    /**
     * 培训-公司计划
     */
    TRAIN_COMPANY(9, "/train/company"),

    /**
     * 培训-
     */
    TRAIN_DEPT(10, "/train/dept"),
    /**
     * 培训讲师头像
     */
    TRAIN_TEACHER_AVATAR(11,"/train/teacher/avatar"),
    /**
     * 课程附件
     */
    TRAIN_COURSE(12,"/train/course"),

    /**
     * 培训材料
     */
    TRAIN_MATERIAL(15,"/train/material"),

    /**
     * 证书附件
     */
    TRAIN_CERTIFICATE(13,"/train/certificate"),

    /**
     * 培训通知
     */
    TRSIN_NOTICE(16,"/train/notice"),

    /**
     *  BPM申请
     */
    BPM_APPLY(14,"/bpm/apply"),

    /**
     *  招聘计划附件
     */
    RECRUIT_PLAN(17,"/recruit/plan");

    private Integer value;
    private String pathPrefix;

    FileBusTypeEnum(Integer value, String pathPrefix) {
        this.value = value;
        this.pathPrefix = pathPrefix;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (FileBusTypeEnum v : FileBusTypeEnum.values()) {
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

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }}
