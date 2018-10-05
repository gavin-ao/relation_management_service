package data.driven.erm.business.order.impl;

import data.driven.erm.business.order.OrderRebateService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderRebateEntity;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hejinkai
 * @date 2018/10/5
 */
@Service
public class OrderRebateServiceImpl implements OrderRebateService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public boolean insertOrderRebate(OrderVO order, String appInfoId, String wechatUserId) {
        if(order.getRealPayment()!=null&&order.getRealPayment().doubleValue()>0){
            if(!exists(order.getWechatUserId(), appInfoId, wechatUserId)){
                OrderRebateEntity orderRebateEntity = new OrderRebateEntity();
                orderRebateEntity.setRebateId(UUIDUtil.getUUID());
                orderRebateEntity.setWechatUserId(wechatUserId);
                orderRebateEntity.setAppInfoId(appInfoId);
                orderRebateEntity.setOrderId(order.getOrderId());
                orderRebateEntity.setFromUserId(order.getWechatUserId());
                orderRebateEntity.setRebateAt(new Date());
                orderRebateEntity.setRebateMoney(order.getRealPayment().multiply(new BigDecimal(0.05)));
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
}
