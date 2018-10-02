package data.driven.erm.entity.order;

import java.math.BigDecimal;

/**
 * 订单详情表
 * @author hejinkai
 * @date 2018/10/3
 */
public class OrderDetailEntity {
    /** 主键 **/
    private String detailId;
    /** 订单外键 **/
    private String orderId;
    /** 购买的商品主键 **/
    private String commodityId;
    /** 商品名称-快照 **/
    private String commodityName;
    /** 商品图片id-快照 **/
    private String pictureId;
    /** 购买数量 **/
    private Integer amount;
    /** 单价 **/
    private BigDecimal unitPrice;
    /** 总价 **/
    private BigDecimal totalPrice;
    /** 实付金额 **/
    private BigDecimal realPayment;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getRealPayment() {
        return realPayment;
    }

    public void setRealPayment(BigDecimal realPayment) {
        this.realPayment = realPayment;
    }
}
