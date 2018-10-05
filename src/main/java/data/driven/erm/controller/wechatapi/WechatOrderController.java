package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.commodity.CommodityService;
import data.driven.erm.business.order.OrderRebateService;
import data.driven.erm.business.order.OrderReceiveAddrService;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.business.wechat.WechatUserService;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.entity.order.OrderReceiveAddrEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.commodity.CommodityVO;
import data.driven.erm.vo.order.OrderVO;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @author hejinkai
 * @date 2018/10/3
 */
@Controller
@RequestMapping("/wechatapi/order")
public class WechatOrderController {

    private static final Logger logger = LoggerFactory.getLogger(WechatOrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderReceiveAddrService orderReceiveAddrService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private OrderRebateService orderRebateService;
    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 立即购买，查询产品和地址
     * @param sessionID
     * @param commodityId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/purchaseImmediately")
    public JSONObject purchaseImmediately(String sessionID, String commodityId){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        JSONObject result = putMsg(true, "200", "调用成功");
        CommodityVO commodityVO = commodityService.getCommodityById(commodityId);
        result.put("commodityVO", commodityVO);
        //TODO 满赠
        result.put("fullOfGifts", null);
        //TODO 优惠
        List<String> discountList = new ArrayList<String>();
        discountList.add("95折");
        if(orderService.haveInvitationDiscountOrder(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId())){
            discountList.add("受邀优惠立减5%");
        }
        result.put("discount", discountList);
        OrderReceiveAddrEntity addr = orderReceiveAddrService.getDefaultAddr(wechatUserInfoVO.getWechatUserId());
        result.put("addr", addr);
        return result;
    }

    /**
     * 提交订单
     * @param sessionID
     * @param orderJson
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/submitOrder")
    public JSONObject submitOrder(String sessionID, String orderJson){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        JSONObject result = orderService.updateOrder(orderJson, wechatUserInfoVO);
        return result;
    }

    /**
     * 完成支付
     * @param sessionID
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/completionOfPayment")
    public JSONObject completionOfPayment(String sessionID, String orderId){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 1);
        String userId = wechatUserService.getInviter(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
        if(userId != null){
            OrderVO order = orderService.getOrderById(orderId, wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
            if(order.getRebate().intValue() == 1){
                orderRebateService.insertOrderRebate(order, wechatUserInfoVO.getAppInfoId(), userId);
            }
        }
        return putMsg(true, "200", "调用成功");
    }

    /**
     * 完成订单
     * @param sessionID
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/completionOfOrder")
    public JSONObject completionOfOrder(String sessionID, String orderId){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 2);
        return putMsg(true, "200", "调用成功");
    }

    /**
     * 根据用户查询订单列表
     * @param sessionID
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/findOrderList")
    public JSONObject findOrderList(String sessionID){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        List<OrderVO> orderList = orderService.findOrderList(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
        JSONObject result = putMsg(true, "200", "调用成功");
        result.put("data", JSONUtil.replaceNull(orderList));
        return result;
    }




}
