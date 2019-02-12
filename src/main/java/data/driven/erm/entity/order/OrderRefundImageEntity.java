package data.driven.erm.entity.order;

/**
 * @Author: lxl
 * @describe 退款图片表实体
 * @Date: 2019/2/12 11:33
 * @Version 1.0
 */
public class OrderRefundImageEntity {
    /**
     * 主键
     */
    private String id;

    /**
     * 订单退款详情信息表
     */
    private String orderRefundDetailInfoId;

    /**
     * 图片id
     */
    private String pictureId;

    public OrderRefundImageEntity(){}


    public OrderRefundImageEntity(String id, String orderRefundDetailInfoId, String pictureId) {
        this.id = id;
        this.orderRefundDetailInfoId = orderRefundDetailInfoId;
        this.pictureId = pictureId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderRefundDetailInfoId() {
        return orderRefundDetailInfoId;
    }

    public void setOrderRefundDetailInfoId(String orderRefundDetailInfoId) {
        this.orderRefundDetailInfoId = orderRefundDetailInfoId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }
}
