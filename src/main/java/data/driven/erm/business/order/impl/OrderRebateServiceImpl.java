package data.driven.erm.business.order.impl;

import data.driven.erm.business.order.OrderRebateService;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderRebateEntity;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderDetailVO;
import data.driven.erm.vo.order.OrderRebateVO;
import data.driven.erm.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hejinkai
 * @date 2018/10/5
 */
@Service
public class OrderRebateServiceImpl implements OrderRebateService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;
    @Autowired
    private OrderService orderService;


    @Override
    public boolean insertOrderRebate(OrderVO order, String appInfoId, String wechatUserId) {
        if(order.getRealPayment()!=null&&order.getRealPayment().doubleValue()>0){
            if(!exists(order.getWechatUserId(), appInfoId, wechatUserId)){
                List<OrderDetailVO> detailVOList = orderService.findOrderDetailByOrderId(order.getOrderId());
                BigDecimal price = new BigDecimal(0);
                BigDecimal multiplyP = new BigDecimal(0.05);
                if(detailVOList != null && detailVOList.size() > 0){
                    for (OrderDetailVO orderDetailVO : detailVOList){
                        price = price.add(orderDetailVO.getTotalPrice().multiply(multiplyP));
                    }
                }else{
                    return false;
                }

                OrderRebateEntity orderRebateEntity = new OrderRebateEntity();
                orderRebateEntity.setRebateId(UUIDUtil.getUUID());
                orderRebateEntity.setWechatUserId(wechatUserId);
                orderRebateEntity.setAppInfoId(appInfoId);
                orderRebateEntity.setOrderId(order.getOrderId());
                orderRebateEntity.setFromUserId(order.getWechatUserId());
                orderRebateEntity.setRebateAt(new Date());
                orderRebateEntity.setRebateMoney(price);
                jdbcBaseDao.insert(orderRebateEntity, "order_rebate_info");
            }
        }
        return true;
    }

    /**
     * 判断数据是否存在
     * @param fromUserId
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    private boolean exists(String fromUserId, String appInfoId, String wechatUserId){
        String sql = "select rebate_id from order_rebate_info where wechat_user_id = ? and app_info_id = ? and from_user_id = ? limit 1";
        Object id = jdbcBaseDao.getColumn(sql, wechatUserId, appInfoId, fromUserId);
        return id != null;
    }

    @Override
    public Integer getRebateMoney(String appInfoId, String wechatUserId) {
        String sql = "select sum(rebate_money) from order_rebate_info where wechat_user_id = ? and app_info_id = ?";
        return jdbcBaseDao.getCount(sql, wechatUserId, appInfoId);
    }

    @Override
    public List<OrderRebateVO> findTopRebateList(String appInfoId, String wechatUserId) {
        String sql = "select u.avatar_url,u.nick_name,u.province,ori.rebate_money from order_rebate_info ori" +
                " left join wechat_user_info u on u.wechat_user_id = ori.wechat_user_id" +
                " where ori.wechat_user_id = ? and ori.app_info_id = ? order by ori.rebate_at desc limit 20";
        List<OrderRebateVO> list = jdbcBaseDao.queryList(OrderRebateVO.class, sql, wechatUserId, appInfoId);
        return list;
    }
}
