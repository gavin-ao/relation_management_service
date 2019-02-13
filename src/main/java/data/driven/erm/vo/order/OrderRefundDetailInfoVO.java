package data.driven.erm.vo.order;

import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: relation_management_service
 * @description: 商品退款详情VO
 * @author: Logan
 * @create: 2019-02-12 16:34
 **/

public class OrderRefundDetailInfoVO extends OrderRefundDetailInfoEntity {
    private Integer orderState;


    public OrderRefundDetailInfoVO(String appid, String wechatUserId, String storeId, String transactionId,
                                       String outRefundNo, String outTradeNo, BigDecimal totalFee, BigDecimal refundFee,
                                       String commodityName, Date transactionDate, String processingMode,
                                       String refundReason, String mobilePhone, String remarks) {
        super(appid, wechatUserId,  storeId,  transactionId,
                 outRefundNo,  outTradeNo,  totalFee,  refundFee,
                 commodityName,  transactionDate,  processingMode,
                refundReason,  mobilePhone,  remarks);
    }

    public OrderRefundDetailInfoVO() {

    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
