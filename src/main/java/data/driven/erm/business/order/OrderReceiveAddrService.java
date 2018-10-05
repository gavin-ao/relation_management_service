package data.driven.erm.business.order;

import data.driven.erm.entity.order.OrderReceiveAddrEntity;

import java.util.List;

/**
 * 订单收件地址
 * @author hejinkai
 * @date 2018/10/2
 */
public interface OrderReceiveAddrService {

    /**
     * 根据用户获取地址列表
     * @param wechatUserId
     * @return
     */
    public List<OrderReceiveAddrEntity> findAddrList(String wechatUserId);

    /**
     * 根据地址id获取详情
     * @param addrId
     */
    public OrderReceiveAddrEntity getAddr(String addrId);

    /**
     * 获取默认地址
     * @param wechatUserId
     */
    public OrderReceiveAddrEntity getDefaultAddr(String wechatUserId);

    /**
     * 新增/更新地址
     * @param addr
     */
    public void updateAddr(OrderReceiveAddrEntity addr);

    /**
     * 删除地址
     * @param ids
     * @param wechatUserId
     */
    public void deleteAddr(String ids, String wechatUserId);

}
