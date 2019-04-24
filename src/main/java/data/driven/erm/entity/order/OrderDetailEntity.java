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
    private Integer productId;
    /** 商品名称-快照 **/
    private String productName;
    /** 商品图片-快照 **/
    private String thumbnail;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
