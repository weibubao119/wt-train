package com.dyys.hr.enums;

/**
 * 培训材料类型
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:42
 **/
public enum TrainMaterialTypeEnum {

    /**
     * 教学计划表
     */
    PLANNING(1),
    /**
     * 讲师情况表
     */
    TEACHERINFO(2),
    /**
     * 培训费用表
     */
    TRAINFEE(3),
    /**
     * 培训照片
     */
    TRAINPHOTO(4),
    /**
     * 试卷模板
     */
    TESTTEMPLATE(5),
    /**
     * 其他材料
     */
    OTHERMATERIAL(6);

    private Integer value;

    TrainMaterialTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (TrainMaterialTypeEnum v : TrainMaterialTypeEnum.values()) {
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
