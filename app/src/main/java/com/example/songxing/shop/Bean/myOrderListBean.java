package com.example.songxing.shop.Bean;

/**
 * Created by songxing on 2019/5/29.
 */

public class myOrderListBean {
    private String pictureUrl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    private String oderId;
    private String buyerNme;
    private String buyerPhone;
    private String buyerAddress;
    private String oderAmount;

    public String getOderId() {
        return oderId;
    }

    public void setOderId(String oderId) {
        this.oderId = oderId;
    }

    private String oderStatus;
    private String payStatus;
    private String createTime;

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

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
