package data.driven.erm.business.order;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.vo.order.OrderVO;
import data.driven.erm.vo.wechat.WechatUserInfoVO;

import java.util.List;

/**
 * 订单信息Service
 * @author hejinkai
 * @date 2018/10/3
 */
public interface OrderService {

    /**
     * 判断一个人是否成功下过单，或者存在未支付的订单
     * @param appInfoId
     * @param wechatUserId
     * @return true - 存在，false - 不存在
     */
    public boolean haveOrder(String appInfoId, String wechatUserId);
    /**
     * 判断是否享受受邀下单优惠
     * @param appInfoId
     * @param wechatUserId
     * @return true - 享受，false - 不享受
     */
    public boolean haveInvitationDiscountOrder(String appInfoId, String wechatUserId);

    /**
     * 更新订单
     * @param orderJson
     * @param wechatUserInfoVO
     * @return
     */
    public JSONObject updateOrder(String orderJson, WechatUserInfoVO wechatUserInfoVO);

    /**
     * 修改订单状态
     * @param orderId
     * @param wechatUserId
     * @param state
     * @return
     */
    public void updateOrderState(String orderId, String wechatUserId, Integer state);

    /**
     * 查询当前用户的所有订单
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public List<OrderVO> findOrderList(String appInfoId, String wechatUserId);

    /**
     * 统计某个商品的销量
     * @param commodityId
     * @return
     */
    public Integer totalSalesVolume(String commodityId);

    /**
     * 根据id查询订单
     * @param orderId
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public OrderVO getOrderById(String orderId, String appInfoId, String wechatUserId);
}
