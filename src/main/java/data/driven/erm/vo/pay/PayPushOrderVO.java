package data.driven.erm.vo.pay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @program: relation_management_service
 * @description: 统一下单参数实体
 * @author: Logan
 * @create: 2019-01-23 17:17
 **/

public class PayPushOrderVO implements Serializable {
    @NotNull
    private String appid;
    /**门店id**/
    @NotNull
    private String storeId;
    @NotNull
    private String orderTitle;
    @NotNull
    private String outTradeNo;
    @NotNull
    private Double totalFee;
    @NotNull
    private String tradeType;
    @NotNull
    private String openid;
    private String ip;

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

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
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
