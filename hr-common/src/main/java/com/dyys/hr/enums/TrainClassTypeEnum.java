package com.dyys.hr.enums;

/**
 * 培训类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:35
 **/
public enum TrainClassTypeEnum {

    /**
     * 工商管理
     */
    COMMERCIAL(1,"工商管理"),
    /**
     * 适应性短期
     */
    ADAPTIVE(2,"适应性短期"),
    /**
     * 学历学位教育
     */
    DEGRESS(3,"学历学位教育"),
    /**
     * 专业技术资格
     */
    PROFESSIONSKILL(4,"专业技术资格"),
    /**
     * 职（执）业资格
     */
    INDUSTRY(5,"职（执）业资格"),
    /**
     * 基础知识
     */
    BASIC(6,"基础知识"),
    /**
     * 专业知识
     */
    PROFESSIONAL(7,"专业知识"),
    /**
     * 能力素质
     */
    QUALITY(8,"能力素质"),
    /**
     * 继续教育
     */
    GOEDUCATION(9,"继续教育"),
    /**
     * 其他
     */
    OTHER(10,"其它");


    private Integer value;

    private String name;

    TrainClassTypeEnum(Integer value, String name) {
        this.value = value;
        this.name =name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainClassTypeEnum v : TrainClassTypeEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(Integer value){
        for (TrainClassTypeEnum v : TrainClassTypeEnum.values()) {
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
