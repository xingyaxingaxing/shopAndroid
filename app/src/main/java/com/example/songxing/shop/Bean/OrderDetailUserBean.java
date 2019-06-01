package com.example.songxing.shop.Bean;

/**
 * Created by songxing on 2019/6/1.
 */

public class OrderDetailUserBean {
    private String buyerOpenid;
    private String oderId;
    private String oderAmount;
    private String oderStatus;
    private String createTime;

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public String getOderId() {
        return oderId;
    }

    public void setOderId(String oderId) {
        this.oderId = oderId;
    }

    public String getOderAmount() {
        return oderAmount;
    }

    public void setOderAmount(String oderAmount) {
        this.oderAmount = oderAmount;
    }

    public String getOderStatus() {
        return oderStatus;
    }

    public void setOderStatus(String oderStatus) {
        this.oderStatus = oderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String buyerNme;

    private String buyerPhone;

    private String buyerAddress;

    public String getBuyerNme() {
        return buyerNme;
    }

    public void setBuyerNme(String buyerNme) {
        this.buyerNme = buyerNme;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }
}
