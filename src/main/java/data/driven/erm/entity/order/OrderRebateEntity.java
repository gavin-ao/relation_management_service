package data.driven.erm.entity.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单返利
 * @author hejinkai
 * @date 2018/10/5
 */
public class OrderRebateEntity {
    /** 主键 **/
    private String rebateId;
    /** 订单外键 **/
    private String orderId;
    /** 微信用户id **/
    public String wechatUserId;
    /** 小程序id **/
    public String appInfoId;
    /** 来自返利的用户id **/
    public String fromUserId;
    /** 返利金额 **/
    public BigDecimal rebateMoney;
    /** 返利时间 **/
    public Date rebateAt;
    /** 数据状态，0-已到账，1-已提现 **/
    public Integer state;

    public String getRebateId() {
        return rebateId;
    }

    public void setRebateId(String rebateId) {
        this.rebateId = rebateId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public BigDecimal getRebateMoney() {
        return rebateMoney;
    }

    public void setRebateMoney(BigDecimal rebateMoney) {
        this.rebateMoney = rebateMoney;
    }

    public Date getRebateAt() {
        return rebateAt;
    }

    public void setRebateAt(Date rebateAt) {
        this.rebateAt = rebateAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
