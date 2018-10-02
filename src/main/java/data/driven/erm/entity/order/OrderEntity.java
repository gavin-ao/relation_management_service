package data.driven.erm.entity.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息表
 * @author hejinkai
 * @date 2018/10/3
 */
public class OrderEntity {
    /** 主键 **/
    public String orderId;
    /** 微信用户id **/
    public String wechatUserId;
    /** 小程序id **/
    public String appInfoId;
    /** 收件地址外键 **/
    public String addrId;
    /** 订单流水号 **/
    public String orderNum;
    /** 实付金额 **/
    public BigDecimal realPayment;
    /** 订单状态 0 - 未支付， 1 - 已支付，2 - 已完成， -1 - 已取消。 **/
    public Integer state;
    /** 创建日期 **/
    public Date createAt;

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

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getRealPayment() {
        return realPayment;
    }

    public void setRealPayment(BigDecimal realPayment) {
        this.realPayment = realPayment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
