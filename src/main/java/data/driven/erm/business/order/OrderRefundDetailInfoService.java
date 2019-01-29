package data.driven.erm.business.order;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;

/**
 * @Author: lxl
 * @describe 订单退款详情信息表Service
 * @Date: 2019/1/29 14:45
 * @Version 1.0
 */
public interface OrderRefundDetailInfoService {

    /**
     * @description 新增订单退款详情信息
     * @author lxl
     * @date 2019-01-29 14:47
     * @param orderRefundDetailInfoEntity 订单退款详情实体
     * @return
     */
    JSONObject insertOrderRefundDetailInfoEntity(OrderRefundDetailInfoEntity orderRefundDetailInfoEntity);
}
