package data.driven.erm.business.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.SerializerFeature;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.business.wechat.WechatUserService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.CommodityEntity;
import data.driven.erm.entity.order.OrderEntity;
import data.driven.erm.util.DateFormatUtil;
import data.driven.erm.util.HttpUtil;
import data.driven.erm.util.IpUtils;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderDetailVO;
import data.driven.erm.vo.order.OrderVO;
import data.driven.erm.vo.pay.SubmissionUnifiedorderParam;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @author hejinkai
 * @date 2018/10/3
 */
@Service
public class OrderServiceImpl implements OrderService{
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private JDBCBaseDao jdbcBaseDao;
    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public boolean haveOrder(String appInfoId, String wechatUserId) {
        String sql = "select order_id from order_info where wechat_user_id = ? and app_info_id = ? and (state = 0 or state = 1 or state = 2) limit 1";
        Object orderId = jdbcBaseDao.getColumn(sql, wechatUserId, appInfoId);
        if(orderId != null){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject updateOrder(String orderJson, WechatUserInfoVO wechatUserInfoVO) {
        if(orderJson == null){
            return putMsg(false, "101", "参数为空");
        }
        OrderVO order = null;
        try{
            order = JSON.parseObject(orderJson, OrderVO.class);
        }catch (Exception e){
            return putMsg(false, "102", "格式错误，json转化失败");
        }
        order.setCreateAt(new Date());
        order.setWechatUserId(wechatUserInfoVO.getWechatUserId());
        order.setAppInfoId(wechatUserInfoVO.getAppInfoId());

        //判断是否享有受邀优惠
        boolean haveInvitation = false;
        if(haveOrder(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId())){
            haveInvitation = true;
        }else{
            String inviter = wechatUserService.getInviter(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
            if(inviter != null && inviter.trim().length() > 0){
                haveInvitation = true;
            }
        }

        if(haveInvitation){
            order.setRebate(1);
        }

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);

        if(order.getOrderId() == null){
            order.setOrderId(UUIDUtil.getUUID());
            orderEntity.setOrderId(order.getOrderId());
            jdbcBaseDao.insert(orderEntity, "order_info");
        }else{
            jdbcBaseDao.update(orderEntity, "order_info", "order_id", false);
            deleteDetail(orderEntity.getOrderId());
        }
        try{
            insertOrderDetail(order, haveInvitation);
        }catch (Exception e){
            return putMsg(false, "103", "订单生成失败");
        }
        JSONObject result = putMsg(true, "200", "订单生成成功");
        result.put("orderId", order.getOrderId());
        return result;
    }

    /**
     * 清除订单详情
     * @param orderId
     */
    private void deleteDetail(String orderId){
        String sql = "delete from order_detail_info where order_id = ?";
        jdbcBaseDao.executeUpdate(sql, orderId);
    }

    /**
     * 插入订单详情
     * @param order
     * @param haveInvitation
     */
    private boolean insertOrderDetail(OrderVO order, boolean haveInvitation) throws Exception{
        List<OrderDetailVO> detailList = order.getDetailList();
        List<String> idList = detailList.stream().collect(Collectors.mapping(o -> o.getCommodityId(), Collectors.toList()));
        Map<String, CommodityEntity> commodityMap = findCommodityByIds(idList);
        BigDecimal orderRealPayment = new BigDecimal(0);
        for(OrderDetailVO detail : detailList){
            CommodityEntity commodityEntity = commodityMap.get(detail.getCommodityId());
            if(commodityEntity == null){
                return false;
            }
            detail.setDetailId(UUIDUtil.getUUID());
            detail.setCommodityName(commodityEntity.getCommodityName());
            detail.setPictureId(commodityEntity.getPictureId());
            detail.setUnitPrice(commodityEntity.getPrices());
            detail.setTotalPrice(commodityEntity.getPrices().multiply(new BigDecimal(detail.getAmount())));
            BigDecimal price = detail.getTotalPrice();
            //判断是否有优惠
            if(haveInvitation){
                price = price.multiply(new BigDecimal(0.95));
            }
            detail.setRealPayment(price);
            detail.setOrderId(order.getOrderId());

            orderRealPayment = orderRealPayment.add(detail.getRealPayment());
        }
        updateOrderRealPayment(order.getOrderId(), orderRealPayment);
        String insertSql = "insert into order_detail_info(detail_id,order_id,commodity_id,commodity_name,picture_id,amount,unit_price,total_price,real_payment)";
        String valueSql = "(:detail_id,:order_id,:commodity_id,:commodity_name,:picture_id,:amount,:unit_price,:total_price,:real_payment)";
        jdbcBaseDao.executeBachOneSql(insertSql, valueSql, detailList);
        return true;
    }

    /**
     * 修改订单实付金额
     * @param orderId
     * @param realPayment
     */
    private void updateOrderRealPayment(String orderId, BigDecimal realPayment){
        String sql = "update order_info set real_payment = ? where order_id = ?";
        jdbcBaseDao.executeUpdate(sql, realPayment, orderId);
    }

    /**
     * 根据商品id查询商品名称和图片id
     * @param idList
     * @return
     */
    private Map<String, CommodityEntity> findCommodityByIds(List<String> idList){
        StringBuilder sb = new StringBuilder();
        for(String id : idList){
            sb.append(",?");
        }
        sb.delete(0, 1);
        String sql = "select commodity_id,commodity_name,prices,picture_id from commodity_info where commodity_id in (" + sb + ")";
        List<CommodityEntity> list = jdbcBaseDao.queryListWithListParam(CommodityEntity.class, sql,idList);
        if(list != null && list.size() > 0){
            return list.stream().collect(Collectors.toMap(o -> o.getCommodityId(), o -> o));
        }
        return null;
    }

    @Override
    public void updateOrderState(String orderId, String wechatUserId, Integer state) {
        String sql = "update order_info set state = ? where order_id = ? and wechat_user_id = ?";
        jdbcBaseDao.executeUpdate(sql, state, orderId, wechatUserId);
    }

    @Override
    public List<OrderVO> findOrderList(String appInfoId, String wechatUserId) {
        String sql = "select order_id,addr_id,order_num,real_payment,state,create_at from order_info where wechat_user_id = ? and app_info_id = ? order by create_at desc";
        List<OrderVO> orderVOList = jdbcBaseDao.queryList(OrderVO.class, sql, wechatUserId, appInfoId);
        if(orderVOList != null && orderVOList.size() > 0){
            List<String> orderIdList = orderVOList.stream().collect(Collectors.mapping(o -> o.getOrderId(), Collectors.toList()));
            Map<String, List<OrderDetailVO>> orderDetailMap = findOrderDetailListGroupByOrder(orderIdList);
            for(OrderVO order : orderVOList){
                order.setDetailList(orderDetailMap.get(order.getOrderId()));
            }
            return orderVOList;
        }
        return null;
    }

    /**
     * 根据订单id查询订单详情，按照订单id分组
     * @param orderIdList
     * @return
     */
    public Map<String, List<OrderDetailVO>> findOrderDetailListGroupByOrder(List<String> orderIdList){
        StringBuilder sb = new StringBuilder();
        for(String id : orderIdList){
            sb.append(",?");
        }
        sb.delete(0, 1);
        String sql = "select odi.detail_id,odi.order_id,odi.commodity_id,odi.commodity_name,odi.amount,odi.unit_price,odi.total_price,odi.real_payment,p.file_path" +
                " from order_detail_info odi left join sys_picture p on p.picture_id = odi.picture_id where odi.order_id in (" + sb + ")";
        List<OrderDetailVO> list = jdbcBaseDao.queryListWithListParam(OrderDetailVO.class, sql, orderIdList);
        if(list != null && list.size() > 0){
            return list.stream().collect(Collectors.groupingBy(o -> o.getOrderId()));
        }
        return null;
    }

    @Override
    public Integer totalSalesVolume(String commodityId) {
        String sql = "select sum(d.amount) from order_detail_info d left join order_info o on o.order_id = d.order_id" +
                " where d.commodity_id = ? and DATE_FORMAT(o.create_at, '%Y%m') = ? and (o.state = 1 or o.state = 2)";
        String monthStr = DateFormatUtil.getLocal("yyyyMM").format(new Date());
        return jdbcBaseDao.getCount(sql, commodityId, monthStr);
    }

    @Override
    public OrderVO getOrderById(String orderId, String appInfoId, String wechatUserId) {
        String sql = "select order_id,wechat_user_id,app_info_id,addr_id,order_num,real_payment,rebate from order_info where order_id = ? and wechat_user_id = ? and app_info_id = ?";
        List<OrderVO> list = jdbcBaseDao.queryList(OrderVO.class, sql, orderId, wechatUserId, appInfoId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<OrderDetailVO> findOrderDetailByOrderId(String orderId) {
        String sql = "select detail_id,order_id,commodity_id,commodity_name,amount,unit_price,total_price,real_payment from order_detail_info where order_id = ?";
        List<OrderDetailVO> orderDetailVOList = jdbcBaseDao.queryList(OrderDetailVO.class, sql, orderId);
        return orderDetailVOList;
    }

    /**
     * @description 查找订单信息
     * @author lxl
     * @date 2019-01-24 10:46
     * @param orderId 订单id
     * @return
     */
    @Override
    public OrderEntity findOrderByOrderId(String orderId) {
        String sql = "select order_id,wechat_user_id,app_info_id,addr_id,order_num,real_payment,state,rebate,create_at" +
                " from order_info where order_id= ?";
        List<OrderEntity> orderEntities = jdbcBaseDao.queryList(OrderEntity.class,sql,orderId);
        if (orderEntities != null && orderEntities.size() > 0){
            return orderEntities.get(0);
        }
        return null;
    }

    /**
     * @description 获取订单列表中商品的分类名称
     * @author lxl
     * @date 2019-01-24 12:21
     * @param orderId
     * @return catgName 分类名称
     */
    private String getCatgName(String orderId){
        String sql = "select group_concat(distinct cci.catg_name) as catg_name from (order_detail_info odi " +
                "LEFT JOIN commodity_info ci ON odi.commodity_id = ci.commodity_id ) LEFT JOIN commodity_catg_info cci " +
                "ON ci.catg_id = cci.catg_id where odi.order_id = ?";
        Object catgName = jdbcBaseDao.getColumn(sql,orderId);
        if (catgName != null){
            return catgName.toString();
        }
        return null;
    }

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
    @Override
    public JSONObject submissionUnifiedorder(HttpServletRequest request, String appId, String openid, String orderId) {
        String URL = "https://pay.easy7share.com/pay/pushOrder";

        String ip = IpUtils.getIpAddr(request);
        OrderEntity orderEntity = findOrderByOrderId(orderId);
        List<OrderDetailVO> orderDetailVOList = findOrderDetailByOrderId(orderId);
        String catgName = getCatgName(orderId);
        SubmissionUnifiedorderParam submissionUnifiedorderParam = new SubmissionUnifiedorderParam(appId,"1",catgName,
                orderId,orderEntity.getRealPayment().multiply(new BigDecimal("100")).intValue(),"JSAPI",openid,ip);
        //javaBean转json字符串
        String submissionUnifiedorderString = JSONObject.toJSONString(submissionUnifiedorderParam,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse);
        logger.info("支付系统中的统一下单参数 "+submissionUnifiedorderString);
        String result =  HttpUtil.doPost(URL,submissionUnifiedorderString);
        logger.info("统一下单后返回的信息 " + result);
        JSONObject resultJson = JSON.parseObject(result);
        resultJson.put("orderId",orderId);
        return resultJson;
    }
}






















