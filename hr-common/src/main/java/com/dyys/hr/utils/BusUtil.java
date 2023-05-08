package com.dyys.hr.utils;

import com.dyys.hr.enums.*;

import java.util.Map;

/**
 * 业务相关 工具类
 *
 * @author ZHIQIANG LI
 * @date 2019/8/28 23:58
 **/
public class BusUtil {

    public static String getUpdateResumeEmailModel(String name, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",您好：").append("\n");
        sb.append("     ").append("感谢您投递我公司职位，我们对您个人简历十分感兴趣，特邀请您更新补充简历信息，以便后续能够更加深入了解。请您点击链接进入简历完善页面补充您的简历信息：\n");
        sb.append(url);

        return sb.toString();
    }

    public static String getEmailModel(String content, Map<String, String> data) {
        for (String key : data.keySet()) {
            if (null == data.get(key)) {
                content = content.replace("{" + key + "}", "-");
            } else {
                content = content.replace("{" + key + "}", data.get(key));
            }
        }
        return content;
    }

    public static String getResumeStatusText(Integer stage, Integer status) {
        switch (ResumeStageEnum.getStage(stage)) {
            case FILTER:
                return ResumeFilterStatusEnum.getText(status);
            case INTERVIEW:
                return ResumeInterviewStatusEnum.getText(status);
            case APPROVAL:
                return ApprovalStatusEnum.getText(status);
            case OFFER:
                return OfferStatusEnum.getText(status);
            case ENTRY:
                return EntryStatusEnum.getText(status);
            case ELIMINATED:
                return "淘汰";
            default:
                return "";
        }
    }

}
