package data.driven.erm.business.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.vo.order.OrderRefundDetailInfoVO;

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

    /**
    * 分页获取退货订单列表
    * @author Logan
    * @date 2019-02-12 16:14
    * @param keyword
    * @param userId
    * @param pageBean

    * @return
    */
    Page<OrderRefundDetailInfoVO> findRefundPage(String keyword, String userId, PageBean pageBean);

    /**
    * 获取退款详情
    * @author Logan
    * @date 2019-02-13 00:09
    * @param storeId
    * @param outRefundNo

    * @return
    */
    OrderRefundDetailInfoVO getRefundDetailInfo(String storeId,String outRefundNo);

    /**
    * 是否同意退款
    * @author Logan
    * @date 2019-02-13 01:14
    * @param agree
    * @param storeId
    * @param outRefundNo

    */
    void agreeRefund(Boolean agree,String storeId,String outRefundNo);

}
