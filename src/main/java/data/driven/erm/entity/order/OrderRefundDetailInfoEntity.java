package data.driven.erm.entity.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lxl
 * @describe 订单退款详情信息表实体
 * @Date: 2019/1/29 14:37
 * @Version 1.0
 */
public class OrderRefundDetailInfoEntity {
    /**
     * 订单退款详情信息主键id
     */
    private String orderRefundDetailInfoId;

    /**
     * 小程序id
     */
    private String appid;

    /**
     * 微信用户id
     */
    private  String wechatUserId;

    /**
     * 门店id
     */
    private String storeId;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     *商户退款单号
     */
    private String outRefundNo;

    /**
     * 商户订单号(订单外键)
     */
    private String outTradeNo;

    /**
     * 订单金额
     */
    private BigDecimal totalFee;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款状态(暂时不用)
     * 1 - 已退款， -1 - 退款异常 -2 - 退款关闭
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 交易时间
     */
    private Date transactionDate;

    /**
     * 处理方式
     */
    private Integer processingMode;

    /**
     * 退款原因
     */
    private Integer refundReason;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 备注信息
     */
    private String remarks;

    public OrderRefundDetailInfoEntity(String appid, String wechatUserId, String storeId, String transactionId,
                                       String outRefundNo, String outTradeNo, BigDecimal totalFee, BigDecimal refundFee,
                                       String commodityName,Date transactionDate,Integer processingMode,
                                       Integer refundReason,String mobilePhone,String remarks) {
        this.appid = appid;
        this.wechatUserId = wechatUserId;
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.outRefundNo = outRefundNo;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.refundFee = refundFee;
        this.commodityName = commodityName;
        this.transactionDate = transactionDate;
        this.processingMode = processingMode;
        this.refundReason = refundReason;
        this.mobilePhone = mobilePhone;
        this.remarks = remarks;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(Integer processingMode) {
        this.processingMode = processingMode;
    }

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderRefundDetailInfoId() {
        return orderRefundDetailInfoId;
    }

    public void setOrderRefundDetailInfoId(String orderRefundDetailInfoId) {
        this.orderRefundDetailInfoId = orderRefundDetailInfoId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
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



























