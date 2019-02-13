package data.driven.erm.business.order;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: lxl
 * @describe 退款图片表Service
 * @Date: 2019/2/13 11:00
 * @Version 1.0
 */
public interface OrderRefundImageService {

    /**
     * @description 新增退款图片表
     * @author lxl
     * @date 2019-02-13 11:03
     * @param orderRefundDetailInfoId 订单退款详情信息表id
     * @param pictureId 图片id
     * @return
     */
    JSONObject insertOrderRefundImageEntity(String orderRefundDetailInfoId,String pictureId);


}
