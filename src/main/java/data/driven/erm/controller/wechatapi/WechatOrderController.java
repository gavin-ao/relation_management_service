package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.api.PayAPI;
import data.driven.erm.business.commodity.ProductService;
import data.driven.erm.business.order.*;
import data.driven.erm.business.wechat.SysPictureService;
import data.driven.erm.business.wechat.WechatAppInfoService;
import data.driven.erm.business.wechat.WechatUserService;
import data.driven.erm.common.Constant;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.entity.commodity.ProductEntity;
import data.driven.erm.entity.order.OrderDetailEntity;
import data.driven.erm.entity.order.OrderEntity;
import data.driven.erm.entity.order.OrderReceiveAddrEntity;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.entity.wechat.SysPictureEntity;
import data.driven.erm.entity.wechat.WechatAppInfoEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderVO;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
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
    /**返回状态码**/
    private static final String RESULT_CODE = "result_code";
    /**状态码信息**/
    private static final String SUCCESS = "success";

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderReceiveAddrService orderReceiveAddrService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRebateService orderRebateService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private PayAPI payAPI;
    @Autowired
    private WechatAppInfoService wechatAppInfoService;
    /**订单退款详情信息表Service**/
    @Autowired
    private OrderRefundDetailInfoService orderRefundDetailInfoService;
    /**图片信息Service**/
    @Autowired
    private SysPictureService sysPictureService;
    /**退款图片表**/
    @Autowired
    private OrderRefundImageService orderRefundImageService;

    /**
     * 立即购买，查询产品和地址
     * @param sessionID
     * @param commodityId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/purchaseImmediately")
    public JSONObject purchaseImmediately(String sessionID, Integer commodityId) {
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        JSONObject result = putMsg(true, "200", "调用成功");
        ProductEntity commodity = productService.getProductById(commodityId);
        result.put("commodityVO", commodity);
        commodity.setThumbnail(Constant.SHOP_FILE_PATH+commodity.getThumbnail());
        //TODO 满赠
        result.put("fullOfGifts", null);
        //TODO 优惠
        List<String> discountList = new ArrayList<String>();

        if (orderService.haveOrder(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId())) {
            discountList.add("95折");
        } else {
            String inviter = wechatUserService.getInviter(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
            if (inviter != null && inviter.trim().length() > 0) {
                discountList.add("受邀优惠立减5%");
            }
        }

        result.put("discount", discountList);
        OrderReceiveAddrEntity addr = orderReceiveAddrService.getDefaultAddr(wechatUserInfoVO.getWechatUserId());
        result.put("addr", addr);
        return result;
    }

    /**
     * 提交订单
     *
     * @param sessionID
     * @param orderJson
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/submitOrder")
    @Transactional(rollbackFor = Exception.class)
    public JSONObject submitOrder(HttpServletRequest request, String sessionID, String orderJson,String appId,String storeId) {
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        JSONObject result = orderService.updateOrder(orderJson, wechatUserInfoVO);
        logger.info("appId "+appId);
        logger.info("订单已插入数据库");
        if (result.getBoolean(SUCCESS)) {
            //如果订单生成成功，则调用支付统一下单接口
            logger.info("调用支付统一下单接口");
            JSONObject resulJson = orderService.submissionUnifiedorder(request,
                    appId,wechatUserInfoVO.getOpenId(), result.getString("orderId"),storeId);
            if (!resulJson.getBoolean(SUCCESS)) {
                throw new RuntimeException(resulJson.getString("msg"));
            } else {
                return resulJson;
            }
        }
        return result;
    }

    /**
     * 完成支付
     *
     * @param sessionID
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/completionOfPayment")
    public JSONObject completionOfPayment(String sessionID, String orderId) {
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 1);
        String userId = wechatUserService.getInviter(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
        if (userId != null) {
            OrderVO order = orderService.getOrderById(orderId, wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
            if (order != null && order.getRebate().intValue() == 1) {
                orderRebateService.insertOrderRebate(order, wechatUserInfoVO.getAppInfoId(), userId);
            }
        }
        return putMsg(true, "200", "调用成功");
    }

    /**
     * @description 申请退款
     * @author lxl
     * @date 2019-01-29 15:00
     * @param sessionID session信息
     * @param  orderId 订单id
     * @param storeId 门店id
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/orderRefund")
    public JSONObject orderRefund( String sessionID, String orderId, String storeId){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        //得到订单信息
        OrderEntity orderEntity = orderService.findOrderByOrderId(orderId);
        //得到小程序信息
        WechatAppInfoEntity wechatAppInfoEntity = wechatAppInfoService.getAppInfoEntity(wechatUserInfoVO.getAppInfoId());
        //初始化订单退款详情信息表实体
        String outRefundNo = UUIDUtil.getUUID();
        logger.info("商户退款单号： "+outRefundNo);
        OrderRefundDetailInfoEntity rrderRefundDetailInfoEntity = new OrderRefundDetailInfoEntity(wechatAppInfoEntity.getAppid(),
                wechatUserInfoVO.getWechatUserId(),storeId,"",outRefundNo,orderId,orderEntity.getRealPayment(),
                orderEntity.getRealPayment(),"",orderEntity.getCreateAt(),"","","","");
        //插入订单退款详情
        JSONObject resultJson = orderRefundDetailInfoService.insertOrderRefundDetailInfoEntity(rrderRefundDetailInfoEntity);
        if (resultJson.getBoolean(SUCCESS)){
            JSONObject refundJson = orderService.orderRefund(wechatAppInfoEntity.getAppid(),storeId,"",orderId,
                    outRefundNo,orderEntity.getRealPayment(),orderEntity.getRealPayment());
            logger.info("调用申请退款接口返回的信息： "+refundJson.toString());
            if (refundJson.getBoolean(SUCCESS)){
                //修改订单状态3为退款成功
                logger.info("进入修改订单状态中");
                orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 3);
            }
            return refundJson;
        }else{
            return resultJson;
        }
    }


    /**
     * 完成订单
     *
     * @param sessionID
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/completionOfOrder")
    public JSONObject completionOfOrder(String sessionID, String orderId) {
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 2);
        return putMsg(true, "200", "调用成功");
    }

    /**
     * 根据用户查询订单列表
     *
     * @param sessionID
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/findOrderList")
    public JSONObject findOrderList(String sessionID) {
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        List<OrderVO> orderList = orderService.findOrderList(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
        if (orderList != null && orderList.size() > 0) {
            for (OrderVO orderVO : orderList) {
                List<OrderDetailEntity> detailList = orderVO.getDetailList();
                if (detailList != null && detailList.size() > 0) {
                    for (OrderDetailEntity orderDetail : detailList) {
                        orderDetail.setThumbnail(Constant.SHOP_FILE_PATH + orderDetail.getThumbnail());
                    }
                }
            }
        }

        JSONObject result = putMsg(true, "200", "调用成功");
        result.put("data", JSONUtil.replaceNull(orderList));
        return result;
    }

    @RequestMapping(path = "/prepay")
    @ResponseBody
    public JSONObject getPrepayInfo( String sessionID,String appId,String storeId,String outTradeNo) {
        logger.info("开始获取统一下单信息");
        JSONObject result;
        String msg = "";
        result = payAPI.getPrepay(appId, storeId, outTradeNo);
        if (result == null) {
            result = new JSONObject();
            result.put("success", false);
            msg = "获取下单信息失败，请重试";
            result.put("msg", msg);
            logger.error(msg);
            return result;
        }
        if (result.getBoolean("success")) {
            logger.info("成功获取统一订单信息");
        } else {
            logger.error(result.getString("msg"));
        }
        return result;
    }

    /**
     * @description 获取申请退款详情
     * @author lxl
     * @date 2019-02-12 16:28
     * @param sessionID session信息
     * @param orderId 订单id
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/refundDetail")
    public JSONObject refundDetail(String sessionID,String orderId){
//        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        //得到订单信息
        OrderEntity orderEntity = orderService.findOrderByOrderId(orderId);
        List<OrderDetailEntity> orderDetaillist = orderService.findOrderDetailByOrderId(orderId);
        StringBuffer commodityName = new StringBuffer();
        logger.info("进入获取申请退款详情");
        logger.info("orderId "+orderId);
        if(orderDetaillist != null && orderDetaillist.size() > 0){
            for (int i = 0 ; i < orderDetaillist.size(); i++){
                OrderDetailEntity orderDetail = orderDetaillist.get(i);
                if (i == orderDetaillist.size() - 1){
                    commodityName.append(orderDetail.getProductName());
                }else{
                    commodityName.append(orderDetail.getProductName()+"、");
                }
            }
        }else{
            return putMsg(false, "200", "获取申请退款信息失败");
        }
        JSONObject refundJson = putMsg(true, "200", "插入订单退款详情信息成功");
        refundJson.put("commodityName",commodityName.toString());
        refundJson.put("totalFee",orderEntity.getRealPayment());
        refundJson.put("orderId",orderId);
        refundJson.put("refundFee",orderEntity.getRealPayment());
        refundJson.put("transactionDate",orderEntity.getCreateAt());
        return refundJson;
    }

    /**
     * @description 提交申请信息
     * @author lxl
     * @date 2019-02-13 10:01
     * @param request
     * @param response
     * @param sessionID session信息
     * @param paramJsonArr 图片数组
     * @param orderId 订单id
     * @param storeId 门店id
     * @param processingMode 退款方式
     * @param refundReason 退款原因
     * @param mobilePhone 手机号码
     * @param remarks 备注
     * @param commodityName 商品名称
     * @return
     */
    @ResponseBody
    @RequestMapping(path ="/uploadRefundInfo",method = RequestMethod.POST)
    public JSONObject uploadRefundInfo(HttpServletRequest request, HttpServletResponse response, String sessionID,
                                       String paramJsonArr,String orderId,String storeId,String processingMode,
                                       String refundReason,String mobilePhone,String remarks,String commodityName){
        logger.info("进入提交申请信息");
        logger.info("订单id："+orderId);
        logger.info("Sessionid："+sessionID);
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        if(wechatUserInfoVO.getWechatUserId() == null){
            return JSONUtil.putMsg(false,"108","请先登录授权");
        }
        if (paramJsonArr == null){
            return JSONUtil.putMsg(false,"101","参数为空");
        }
        //得到订单信息
        OrderEntity orderEntity = orderService.findOrderByOrderId(orderId);
        logger.info("得到订单信息成功");
        //得到小程序信息
        WechatAppInfoEntity wechatAppInfoEntity = wechatAppInfoService.getAppInfoEntity(wechatUserInfoVO.getAppInfoId());
        logger.info("得到小程序信息成功");
        //初始化订单退款详情信息表实体
        String outRefundNo = UUIDUtil.getUUID();
        logger.info("商户退款单号： "+outRefundNo);
        OrderRefundDetailInfoEntity rrderRefundDetailInfoEntity = new OrderRefundDetailInfoEntity(wechatAppInfoEntity.getAppid(),
                wechatUserInfoVO.getWechatUserId(),storeId,"",outRefundNo,orderId,orderEntity.getRealPayment(),
                orderEntity.getRealPayment(),commodityName,orderEntity.getCreateAt(),processingMode,
                refundReason,mobilePhone,remarks);
        //插入订单退款详情
        JSONObject resultJson = orderRefundDetailInfoService.insertOrderRefundDetailInfoEntity(rrderRefundDetailInfoEntity);
        logger.info("插入订单退款详情成功");
        if (resultJson.getBoolean(SUCCESS)){
            List<String> fileList = JSONObject.parseArray(paramJsonArr,String.class);
            List<SysPictureEntity> sysPictureList = new ArrayList<>();
            Date date = new Date();
            //上传图片
            for (String file : fileList){
                sysPictureService.insertPictures(wechatUserInfoVO,date,sysPictureList,file);
            }
            logger.info("上传图片成功");
            //插入图片信息
            if (sysPictureList.size() > 0){
                sysPictureService.insertManyPicture(sysPictureList);
                logger.info("批量插入图片数据成功");
                //插入订单退款详情信息表与退款图片表关联表
                for (SysPictureEntity sysPictureEntity : sysPictureList){
                    orderRefundImageService.insertOrderRefundImageEntity(resultJson.getString("orderRefundDetailInfoId"),
                            sysPictureEntity.getPictureId());
                }
                logger.info("插入订单退款详情信息表与退款图片表关联表");

            }
            //修改订单状态3为退款中
            logger.info("进入修改订单状态中");
            orderService.updateOrderState(orderId, wechatUserInfoVO.getWechatUserId(), 4);
            return JSONUtil.putMsg(true,"200","提交申请信息");
        }else{
            return resultJson;
        }
    }


}





















