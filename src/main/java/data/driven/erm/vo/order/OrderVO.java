package data.driven.erm.vo.order;

import data.driven.erm.entity.order.OrderDetailEntity;
import data.driven.erm.entity.order.OrderEntity;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/10/3
 */
public class OrderVO extends OrderEntity {

    /** 订单详情集合 **/
    private List<OrderDetailEntity> detailList;

    public List<OrderDetailEntity> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetailEntity> detailList) {
        this.detailList = detailList;
    }
}
