package data.driven.erm.business.order;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.entity.order.OrderDetailEntity;
import data.driven.erm.entity.order.OrderEntity;
import data.driven.erm.vo.order.OrderVO;
import data.driven.erm.vo.wechat.WechatUserInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    * 修改订单状态
    * @author Logan
    * @date 2019-02-13 01:41
    * @param orderId
    * @param state

    * @return
    */
    public void updateOrderState(String orderId, Integer state);


    /**
     * 查询当前用户的所有订单
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public List<OrderVO> findOrderList(String appInfoId, String wechatUserId);

    /**
     * 统计某个商品的当月销量
     * @param commodityId
     * @return
     */
    public Integer totalSalesVolume(Integer commodityId);

    /**
     * 根据id查询订单
     * @param orderId
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public OrderVO getOrderById(String orderId, String appInfoId, String wechatUserId);

    /**
     * 查找订单详细
     * @param orderId
     * @return
     */
    public List<OrderDetailEntity> findOrderDetailByOrderId(String orderId);

    /**
     * @description 查找订单信息
     * @author lxl
     * @date 2019-01-24 10:46
     * @param orderId 订单id
     * @return
     */
    OrderEntity findOrderByOrderId(String orderId);

    /**
     * @description 提交统一下单信息
     * @author lxl
     * @date 2019-01-24 11:05
     * @param request
     * @param appId 小程序appId
     * @param openid 微信用户唯一标示
     * @param orderId 订单id
     * @return
     */
    JSONObject submissionUnifiedorder(HttpServletRequest request,String appId, String openid, String orderId,
                                      String storeId);

    /**
     * @description 申请退款
     * @author lxl
     * @date 2019-01-29 15:34
     * @param appid 小程序id
     * @param storeId 门店id
     * @param transactionId 微信订单号
     * @param outTradeNo 商户订单号
     * @param outRefundNo 商户退款单号
     * @param totalFee 订单金额
     * @param refundFee 退款金额
     * @return
     */
    JSONObject orderRefund(String appid, String storeId,String transactionId, String outTradeNo, String outRefundNo,
                           BigDecimal totalFee, BigDecimal refundFee);


}
