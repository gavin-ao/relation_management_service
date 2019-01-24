package data.driven.erm.vo.pay;

import java.math.BigDecimal;

/**
 * @Author: lxl
 * @describe 支付系统中的统一下单参数
 * @Date: 2019/1/24 11:23
 * @Version 1.0
 */
public class SubmissionUnifiedorderParam {

    /**
     * 小程序id
     */
    private String appid;
    /**
     * 门店id
     */
    private String storeId;

    /**
     * 商品描述
     */
    private String orderTitle;

    /**
     * 订单id
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private Integer totalFee;

    /**
     * 交易金额
     */
    private String tradeType;

    /**
     * 微信唯一id
     */
    private String openid;

    /**
     * 终端ip
     */
    private String ip;

    public SubmissionUnifiedorderParam() {}

    public SubmissionUnifiedorderParam(String appid, String storeId, String orderTitle, String outTradeNo,
                                       Integer totalFee, String tradeType, String openid, String ip) {
        this.appid = appid;
        this.storeId = storeId;
        this.orderTitle = orderTitle;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.tradeType = tradeType;
        this.openid = openid;
        this.ip = ip;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
