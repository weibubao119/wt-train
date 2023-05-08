package com.dyys.hr.enums;

/**
 * excel文件导出枚举
 *
 * @author ZHIQIANG LI
 * @date 2019/9/8 1:01
 **/
public enum ExcelFileEnum {
    /**
     * 招聘-招聘需求汇总报表
     */
    RECRUIT_COLLECT_DETAIL("recruit_collect_detail"),
    /**
     * 招聘-招聘计划明细报表
     */
    RECRUIT_PLAN_DETAIL("recruit_plan_detail"),
    /**
     * 培训-内部培训登记台账
     */
    TRAIN_REPORT_SATISFACTION("train_report_satisfaction"),

    /**
     * 招聘-岗位数据分析
     */
    RECRUIT_POSITION_ANALYSIS("recruit_position_analysis"),

    /**
     * 招聘-候选人信息明细表
     */
    RECRUIT_APPLICANT_LIST("recruit_applicant_list"),

    /**
     * 培训-员工培训个人登记台账
     */
    TRAIN_USER_SIGNUP("train_user_signup"),

    /**
     * 培训-内部培训登记台账
     */
    TRAIN_INSIDE_SIGNUP("train_inside_signup"),

    /**
     * 培训-外部培训登记台账
     */
    TRAIN_OUTSIDE_SIGNUP("train_outside_signup"),
    /**
     * 培训-经费统计报表
     */
    TRAIN_FUNS_REPORT("train_funs_rport"),

    /**
     * 导出签到明细
     */
    TRAIN_SIGN_DETAIL("train_sign_detail"),

    /**
     * 培训班报名学员
     */
    TRAIN_CLASS_SIGNUP("train_class_signup"),

    /**
     * 培训班成绩
     */
    Train_CLASS_RESULTS("train_class_results"),

    /**
     * 招聘-投递简历汇总表
     */
    RECRUIT_RESUME_TOTAL("recruit_resume_total");

    private String type;

    ExcelFileEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }}
