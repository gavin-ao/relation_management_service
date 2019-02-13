package data.driven.erm.business.order.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRefundImageService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderRefundImageEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @Author: lxl
 * @describe 退款图片表Impl
 * @Date: 2019/2/13 11:00
 * @Version 1.0
 */
@Service
public class OrderRefundImageServiceImpl implements OrderRefundImageService{
    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    /**
     * @description 新增退款图片表
     * @author lxl
     * @date 2019-02-13 11:03
     * @param orderRefundDetailInfoId 订单退款详情信息表id
     * @param pictureId 图片id
     * @return
     */
    @Override
    public JSONObject insertOrderRefundImageEntity(String orderRefundDetailInfoId,String pictureId) {
        String uuid = UUIDUtil.getUUID();
        OrderRefundImageEntity orderRefundImageEntity = new OrderRefundImageEntity(uuid,orderRefundDetailInfoId,pictureId);
        try {
            jdbcBaseDao.insert(orderRefundImageEntity,"order_refund_image");
            JSONObject result = putMsg(true, "200", "退款图片表信息成功");
            result.put("id", uuid);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"200","退款图片表信息失败");
    }
}
