package com.dyys.hr.constants;

/**
 * 系统常量
 *
 * @author ZHIQIANG LI
 * @date 2019/6/26 1:21
 **/
public class Constant {
    public static final String TOKEN = "token";

    /**
     * 申请编号前缀
     */
    public static final String APPLY_NO_PREFIX = "AN";

    /**
     * 培训班申请的编号前缀
     */
    public static final String APPLY_CLASS_NO_PREFIX = "AC";

    /**
     * 讲师的编号前缀
     */
    public static final String TRAIN_TEACHER_NO_PREFIX = "TE";

    /**
     * 课程的编号前缀
     */
    public static final String TRAIN_COURSE_NO_PREFIX = "CO";

    /**
     * 证书的编号前缀
     */
    public static final String TRAIN_CERTIFICATE_NO_PREFIX = "CR";


    public static final String TRAIN_PLAN_NO_PREFIX = "TP";

    /**
     * 流程阶段字典码
     */
    public static final String PROCESS_TYPE_CODE = "ResumeStage";

    /**
     * 表单名称
     */
    public static final String BASIC_FORM_NAME = "基础信息";

    /**
     * 流程阶段-淘汰阶段中文名
     */
    public static final String PROCESS_OUT_NAME = "淘汰";


    /** REDIS KEY **/
    // ------------------------------------------------------------------------------

    /**
     * BPM申请编号 KEY [string]
     */
    public static final String REDIS_NO = "wtrain:no:%s:%s";

    /**
     * 字典code KEY [string]
     */
    public static final String REDIS_DICT_TYPE_CODE = "wtrain:dictTypeCode:%s";

    /**
     * 用户登录token KEY [string]
     */
    public static final String REDIS_TOKEN = "wtrain:user:token:%s";


    /**
     * 用户信息 KEY [hash]
     */
    public static final String REDIS_USER_INFO = "wtrain:user:%d:info";

    /**
     * 部门信息 KEY [hash]
     */
    public static final String REDIS_DEPT_INFO = "wtrain:dept:%d:info";

    /**
     * 岗位信息 KEY [hash]
     */
    public static final String REDIS_POST_INFO = "wtrain:post:%d:info";

    /**
     * 字典信息 KEY [String]
     */
    public static final String REDIS_DICT_INFO = "wtrain:dict:%s:info";

    /**
     * 全部部门 KEY
     */
    public static final String REDIS_DEPT_ALL_INFO = "wtrain:dept:all:info";

    /**
     * 全部人员 KEY
     */
    public static final String REDIS_USER_ALL_INFO = "wtrain:user:all:info";

    /**
     * 全部职位 KEY
     */
    public static final String REDIS_POST_ALL_INFO = "wtrain:post:all:info";

    /**
     * 正在申请的职位
     */
    public static final String REDIS_IDENTITY_POST = "wtrain:identity:%s:post:%d";

    /**
     * Title 各类标题
     */
    // --------------------------------------------------------------------------------

    public static final String TITLE_UPDATE_RESUME_EMAIL = "邀请简历更新";

    public static final String TITLE_INTERVIEW_EMAIL = "面试通知";

    public static final String TITLE_OFFER_EMAIL = "录用通知";

    public static final String TITLE_BPM_RECRUIT_DEMAND = "招聘年度需求反馈申请";

    public static final String TITLE_BPM_RECRUIT_DEMAND_TEMP = "招聘临时需求申请";

    public static final String TITLE_BPM_RECRUIT_PLAN = "招聘计划申请";

    public static final String TITLE_BPM_RECRUIT_INSIDE = "内部应聘申请";

    public static final String BPM_DEFAULT_REMARK = "请批示";

    /**
     * 培训邮件提醒
     */
    public static final String BIZ_CODE_TRAIN = "train";
    /**
     * 招聘邮件提醒
     */
    public static final String BIZ_CODE_RECRUIT = "recruit";

    // 角色常量
    // --------------------------------------------------------------------------------

    /**
     * 培训管理员
     */
    public static final String ROLE_TRAIN_ADMIN = "CNMC_TS_ADMIN_133";

    /**
     * 培训专员
     */
    public static final String ROLE_TRAINER = "CNMC_TS_USER_133";

    /**
     * 招聘管理员
     */
    public static final String ROLE_RECRUIT_ADMIN = "CNMC_RS_ADMIN_133";

    /**
     * 招聘专员
     */
    public static final String ROLE_RECRUITER = "CNMC_RS_USER_133";



    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";


    /**
     * 部门组织树 KEY [string]
     */
    public static final String DEPT_CRUMB = "wtrain:dept:crumb:%s";

    /**
     * 部门组织树筛选 KEY [string]
     */
    public static final String DEPT_COMPANY_CRUMB = "wtrain:dept:crumb:company:%s";
}
