package com.dyys.hr.enums;

/**
 * OFFER发放状态
 *
 * @author ZHIQIANG LI
 * @date 2019/6/23 1:54
 **/
public enum OfferStatusEnum {
    /**
     * 未开始
     */
    WAIT(0,"未开始"),
    /**
     * 已通知
     */
    NOTIFIED(1,"已通知"),
    /**
     * 接收Offer
     */
    ACCEPT(2,"接受Offer"),
    /**
     * 拒绝Offer
     */
    REFUSE(3,"拒绝Offer");

    private Integer value;
    private String text;

    OfferStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }


    public static String getText(Integer value) {
        for (OfferStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum.getText();
            }
        }
        return "";
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValid(Integer value) {
        for (OfferStatusEnum v : OfferStatusEnum.values()) {
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
    }
}
