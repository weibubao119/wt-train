package com.dyys.hr.enums;

/**
 * 技术资格专业
 *
 * @author ZHIQIANG LI
 * @date 2019/11/16 0:11
 **/
public enum ProfessionEnum {

    PROFESSION1(1, "安全专业人员"),
    PROFESSION2(2, "采矿专业人员"),
    PROFESSION3(3, "采暖专业人员"),
    PROFESSION4(4, "测绘专业人员"),
    PROFESSION5(5, "出版专业人员"),
    PROFESSION6(6, "畜牧(兽医)"),
    PROFESSION7(7, "档案专业人员"),
    PROFESSION8(8, "党校教师(市级以上)"),
    PROFESSION9(9, "党校教师(县级)"),
    PROFESSION10(10, "地质矿产专业人员"),
    PROFESSION11(11, "地质实验测试(选矿)专业人员"),
    PROFESSION12(12, "电气专业人员"),
    PROFESSION13(13, "电子信息专业人员"),
    PROFESSION14(14, "翻译专业人员"),
    PROFESSION15(15, "纺织专业人员"),
    PROFESSION16(16, "高校教管研究人员"),
    PROFESSION17(17, "高校教师"),
    PROFESSION18(18, "高校实验人员"),
    PROFESSION19(19, "给排水专业人员"),
    PROFESSION20(20, "工艺美术人员"),
    PROFESSION21(21, "公证专业人员"),
    PROFESSION22(22, "广播电视专业播音员"),
    PROFESSION23(23, "广播电影电视工程专业人员"),
    PROFESSION24(24, "国际商务专业人员"),
    PROFESSION25(25, "护理专业人员"),
    PROFESSION26(26, "化验专业人员"),
    PROFESSION27(27, "环境保护专业人员"),
    PROFESSION28(28, "会计专业人员"),
    PROFESSION29(29, "机械专业人员"),
    PROFESSION30(30, "技工学校教师"),
    PROFESSION31(31, "技校实习指导教师"),
    PROFESSION32(32, "建设专业人员"),
    PROFESSION33(33, "交通公路专业人员"),
    PROFESSION34(34, "交通水运专业人员"),
    PROFESSION35(35, "经济专业人员"),
    PROFESSION36(36, "矿业工程"),
    PROFESSION37(37, "林(果)业人员"),
    PROFESSION38(38, "律师专业人员"),
    PROFESSION39(39, "煤炭专业人员"),
    PROFESSION40(40, "农技人员"),
    PROFESSION41(41, "农业经济人员"),
    PROFESSION42(42, "农业科研人员"),
    PROFESSION43(43, "轻工工程专业人员"),
    PROFESSION44(44, "群众文化专业人员"),
    PROFESSION45(45, "社会科学研究人员"),
    PROFESSION46(46, "审计专业人员"),
    PROFESSION47(47, "石油化工专业人员"),
    PROFESSION48(48, "水产专业人员"),
    PROFESSION49(49, "水利专业人员"),
    PROFESSION50(50, "水泥专业人员"),
    PROFESSION51(51, "水文(工程、环境)地质专业人员"),
    PROFESSION52(52, "思想政治工作专业人员"),
    PROFESSION53(53, "探矿专业人员"),
    PROFESSION54(54, "体育教练"),
    PROFESSION55(55, "铁道运输人员"),
    PROFESSION56(56, "统计专业人员"),
    PROFESSION57(57, "图书、资料专业人员"),
    PROFESSION58(58, "土地专业人员"),
    PROFESSION59(59, "网络专业人员"),
    PROFESSION60(60, "文物、博物专业人员"),
    PROFESSION61(61, "物化探与遥感专业人员"),
    PROFESSION62(62, "小学教师(含幼教)"),
    PROFESSION63(63, "新闻专业人员"),
    PROFESSION64(64, "岩土工程专业人员"),
    PROFESSION65(65, "药学专业人员"),
    PROFESSION66(66, "冶金专业人员"),
    PROFESSION67(67, "冶炼专业人员"),
    PROFESSION68(68, "医疗专业人员"),
    PROFESSION69(69, "艺术专业人员"),
    PROFESSION70(70, "预防专业人员"),
    PROFESSION71(71, "炸药专业人员"),
    PROFESSION72(72, "职工教育人员"),
    PROFESSION73(73, "制酸专业人员"),
    PROFESSION74(74, "质量技术监督专业人员"),
    PROFESSION75(75, "中学教师"),
    PROFESSION76(76, "中专实验人员"),
    PROFESSION77(77, "中专校教师"),
    PROFESSION78(78, "自然科学实验人员"),
    PROFESSION79(79, "自然科学研究人员");

    private Integer value;

    private String name;

    ProfessionEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (ProfessionEnum v : ProfessionEnum.values()) {
            if (v.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取参数值
     */
    public static String getNameByValue(Integer value) {
        for (ProfessionEnum v : ProfessionEnum.values()) {
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
