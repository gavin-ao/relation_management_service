package data.driven.erm.business.order;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.vo.order.OrderVO;

import java.util.List;

/**
 * 订单信息Service
 * @author hejinkai
 * @date 2018/10/3
 */
public interface OrderService {

    /**
     * 判断一个人是否成功下过单，或者存在未支付的订单
     * @param wechatUserId
     * @param appInfoId
     * @return true - 存在，false - 不存在
     */
    public boolean haveOrder(String wechatUserId, String appInfoId);

    /**
     * 更新订单
     * @param orderJson
     * @return
     */
    public JSONObject updateOrder(String orderJson);

    /**
     * 修改订单状态
     * @param orderId
     * @param state
     * @return
     */
    public void updateOrderState(String orderId, Integer state);

    /**
     * 查询当前用户的所有订单
     * @param wechatUserId
     * @param appInfoId
     * @return
     */
    public List<OrderVO> findOrderList(String wechatUserId, String appInfoId);

    /**
     * 统计某个商品的销量
     * @param commodityId
     * @return
     */
    public Integer totalSalesVolume(String commodityId);
}
