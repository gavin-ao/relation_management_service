package data.driven.erm.vo.pay;


/**
 * @Author: lxl
 * @describe 申请退款参数
 * @Date: 2019/1/29 15:37
 * @Version 1.0
 */
public class PayRefundParam {
    /**
     * 小程序id
     */
    private String appid;

    /**
     * 小程序门店id
     */
    private String storeId;

    /**
     *微信订单号,与商户订单号二选一
     */
    private String transactionId;

    /** 商户订单号,与微信订单号二选一**/
    private String outTradeNo;

    /** 商户退款单号，商户系统内部的退款单号，
     * 商户系统内部唯一，只能是数字、大小写字母_-|*@ ，
     * 同一退款单号多次请求只退一笔**/
    private String outRefundNo;

    /** 订单金额，订单总金额，单位为分，只能为整数**/
    private Integer	totalFee;

    /**退款金额，退款总金额，订单总金额，单位为分，只能为整数**/
    private Integer	refundFee;

    public PayRefundParam(){}

    public PayRefundParam(String appid, String storeId, String transactionId, String outTradeNo, String outRefundNo,
                          Integer totalFee, Integer refundFee) {
        this.appid = appid;
        this.storeId = storeId;
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
        this.outRefundNo = outRefundNo;
        this.totalFee = totalFee;
        this.refundFee = refundFee;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }
}
