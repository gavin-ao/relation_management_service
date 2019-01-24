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
    /**小程序appId**/
    @NotNull
    private String appid;
    /**门店id**/
    @NotNull
    private String storeId;
    /**订单类目，用来拼接统一下单接口的body字段**/
    @NotNull
    private String orderTitle;
    /**商户订单号，32位以内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一**/
    @NotNull
    private String outTradeNo;
    /**订单总金额(分)**/
    @NotNull
    private Integer totalFee;
    /**交易类型，
     * JSAPI -JSAPI支付
     NATIVE -Native支付
     APP -APP支付**/
    @NotNull
    private String tradeType;
    /**用户标识,trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识**/
    @NotNull
    private String openid;
    /**终端IP,调用微信支付API的机器IP**/
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
