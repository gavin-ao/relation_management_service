package data.driven.erm.business.order.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRefundDetailInfoService;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.order.OrderRefundDetailInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @Author: lxl
 * @describe 订单退款详情信息表Impl
 * @Date: 2019/1/29 14:45
 * @Version 1.0
 */
@Service
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

    /**
     * 分页获取退货订单列表
     *
     * @param keyword
     * @param userId
     * @param pageBean
     * @return
     * @author Logan
     * @date 2019-02-12 16:14
     */
    @Override
    public Page<OrderRefundDetailInfoVO> findRefundPage(String keyword, String userId, PageBean pageBean) {
        String sql = "SELECT refundInfo.*, orderInfo.state as orderState FROM order_refund_detail_info refundInfo\n" +
                     "JOIN order_info orderInfo ON refundInfo.out_trade_no = orderInfo.order_id \n" +
                      "WHERE orderInfo.state IN ( 3, 4, 5, 6 ) ";
        StringBuffer where = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        if (keyword != null) {
            where.append(" and refundInfo.out_trade_no like ?");
            paramList.add("%" + keyword.trim() + "%");
        }

        if (where.length() > 0) {
            sql += where;
        }
        sql += " order by create_at desc";
        return jdbcBaseDao.queryPageWithListParam(OrderRefundDetailInfoVO.class, pageBean, sql, paramList);
    }

    /**
     * 获取退款详情
     *
     * @param storeId
     * @param outRefundNo
     * @return
     * @author Logan
     * @date 2019-02-13 00:09
     */
    @Override
    public OrderRefundDetailInfoVO getRefundDetailInfo(String storeId, String outRefundNo) {
        String sql = "SELECT refundInfo.*, orderInfo.state as orderState FROM order_refund_detail_info refundInfo\n" +
                "JOIN order_info orderInfo ON refundInfo.out_trade_no = orderInfo.order_id \n" +
                "WHERE refundInfo.store_id=? and refundInfo.out_refund_no=? ";
        return jdbcBaseDao.executeQuery(OrderRefundDetailInfoVO.class,sql,storeId,outRefundNo);
    }

    /**
     * 是否同意退款
     *
     * @param agree
     * @param storeId
     * @param outRefundNo
     * @author Logan
     * @date 2019-02-13 01:14
     */
    @Override
    public void agreeRefund(Boolean agree, String storeId, String outRefundNo) {
        Integer state=6;
        if(agree){
            state = 5;
        }
        String sql = "UPDATE order_info orderInfo\n" +
                     "JOIN order_refund_detail_info refundInfo ON refundInfo.out_trade_no = orderInfo.order_id \n" +
                     "SET orderInfo.state = ? \n" +
                     "WHERE refundInfo.store_id=? and refundInfo.out_refund_no=? \n";
        jdbcBaseDao.executeUpdate(sql,state,storeId,outRefundNo);
    }
}
