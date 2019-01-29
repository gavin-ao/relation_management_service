package data.driven.erm.business.order.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRefundDetailInfoService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @Author: lxl
 * @describe 订单退款详情信息表Impl
 * @Date: 2019/1/29 14:45
 * @Version 1.0
 */
public class OrderRefundDetailInfoServiceImpl implements OrderRefundDetailInfoService {
    private static final Logger logger = LoggerFactory.getLogger(OrderRefundDetailInfoServiceImpl.class);

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    /**
     * @description 新增订单退款详情信息
     * @author lxl
     * @date 2019-01-29 14:47
     * @param orderRefundDetailInfoEntity 订单退款详情实体
     * @return
     */
    @Override
    public JSONObject insertOrderRefundDetailInfoEntity(OrderRefundDetailInfoEntity orderRefundDetailInfoEntity) {
        String uuid = UUIDUtil.getUUID();
        Date createAt = new Date();
        orderRefundDetailInfoEntity.setOrderRefundDetailInfoId(uuid);
        orderRefundDetailInfoEntity.setCreateAt(createAt);
        try {
            jdbcBaseDao.insert(orderRefundDetailInfoEntity,"order_refund_detail_info");
            JSONObject result = putMsg(true, "200", "插入订单退款详情信息成功");
            result.put("orderRefundDetailInfoId", uuid);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"200","插入订单退款详情信息失败");
    }
}
