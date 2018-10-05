package data.driven.erm.business.order;

import data.driven.erm.vo.order.OrderVO;

/**
 * 订单返利
 * @author hejinkai
 * @date 2018/10/5
 */
public interface OrderRebateService {

    /**
     * 插入返利信息
     * @param order
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public boolean insertOrderRebate(OrderVO order, String appInfoId, String wechatUserId);

    /**
     * 查询返利的总金额
     * @param appInfoId
     * @param wechatUserId
     * @return
     */
    public Integer getRebateMoney(String appInfoId, String wechatUserId);

}
