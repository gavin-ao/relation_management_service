package data.driven.erm.business.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.CommodityEntity;
import data.driven.erm.entity.order.OrderEntity;
import data.driven.erm.util.DateFormatUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderDetailVO;
import data.driven.erm.vo.order.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public boolean haveOrder(String wechatUserId, String appInfoId) {
        String sql = "select order_id from order_info where wechat_user_id = ? and app_info_id = ? and (state = 0 or state = 1 or state = 2) limit 1";
        Object orderId = jdbcBaseDao.getColumn(sql, wechatUserId, appInfoId);
        if(orderId != null){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject updateOrder(String orderJson) {
        if(orderJson == null){
            return putMsg(false, "101", "参数为空");
        }
        OrderVO order = null;
        try{
            order = JSON.parseObject(orderJson, OrderVO.class);
        }catch (Exception e){
            return putMsg(false, "102", "格式错误，json转化失败");
        }
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        if(order.getOrderId() == null){
            order.setOrderId(UUIDUtil.getUUID());
            orderEntity.setOrderId(order.getOrderId());
            jdbcBaseDao.insert(orderEntity, "order_info");
            try{
                insertOrderDetail(order);
            }catch (Exception e){
                return putMsg(false, "103", "订单生成失败");
            }
        }else{
            jdbcBaseDao.update(orderEntity, "order_info", "order_id", false);
            deleteDetail(orderEntity.getOrderId());
            try{
                insertOrderDetail(order);
            }catch (Exception e){
                return putMsg(false, "103", "订单生成失败");
            }
        }
        return putMsg(true, "200", "订单生成成功");
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
     */
    private boolean insertOrderDetail(OrderVO order) throws Exception{
        List<OrderDetailVO> detailList = order.getDetailList();
        List<String> idList = detailList.stream().collect(Collectors.mapping(o -> o.getCommodityId(), Collectors.toList()));
        Map<String, CommodityEntity> commodityMap = findCommodityByIds(idList);
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
            //TODO 实付金额
//            detail.setRealPayment();
            detail.setOrderId(order.getOrderId());
        }
        String insertSql = "insert into order_detail_info(detail_id,order_id,commodity_id,commodity_name,picture_id,amount,unit_price,total_price,real_payment)";
        String valueSql = "(:detail_id,:order_id,:commodity_id,:commodity_name,:picture_id,:amount,:unit_price,:total_price,:real_payment)";
        jdbcBaseDao.executeBachOneSql(insertSql, valueSql, detailList);
        return true;
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
    public void updateOrderState(String orderId, Integer state) {
        String sql = "update order_info set state = ? where order_id = ?";
        jdbcBaseDao.executeUpdate(sql, state, orderId);
    }

    @Override
    public List<OrderVO> findOrderList(String wechatUserId, String appInfoId) {
        String sql = "select order_id,addr_id,order_num,real_payment,state from order_info where wechat_user_id = ? and app_info_id = ?";
        List<OrderVO> orderVOList = jdbcBaseDao.queryList(OrderVO.class, sql, wechatUserId, appInfoId);
        if(orderVOList != null && orderVOList.size() > 0){
            List<String> orderIdList = orderVOList.stream().collect(Collectors.mapping(o -> o.getOrderId(), Collectors.toList()));
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
}
