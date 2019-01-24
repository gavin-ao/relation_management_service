package data.driven.erm.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.driven.erm.util.HttpUtil;
import data.driven.erm.vo.pay.PayPushOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: relation_management_service
 * @description: 支付API
 * @author: Logan
 * @create: 2019-01-23 16:58
 **/
@Component
public class PayAPI {
    private static Logger logger = LoggerFactory.getLogger(PayAPI.class);
    /**获取统一下单信息的接口地址**/
    private static String PREPAY_URL = "/pay/prepay";
    /**统一下单信息的接口地址**/
    private static String PUSH_ORDER_URL = "/pay/pushOrder";
    @Value("${pay.url}")
    private String payUrl;
    private String getPrepayRequestURL(){
        StringBuilder urlBuilder = new StringBuilder(payUrl).append(PREPAY_URL);
        return urlBuilder.toString();
    }
    private String getPushOrderUrl(){
        StringBuilder urlBuilder = new StringBuilder(payUrl).append(PUSH_ORDER_URL);
        return urlBuilder.toString();
    }

    /**
    * 获取统一下单的信息
    * @author Logan
    * @date 2019-01-23 17:05
    * @param appid 小程序id，
    * @param storeId 门店id
    * @param outTradeNo 商户订单号

    * @return
    */
    public JSONObject getPrepay(String appid,String storeId,String outTradeNo){
         JSONObject param = new JSONObject();
         param.put("appId",appid);
         param.put("storeId",storeId);
         param.put("outTradeNo",outTradeNo);
         String url = getPrepayRequestURL();
         String  result = HttpUtil.doPostSSL(url,JSONObject.toJSONString(param));
         JSONObject resultObj = JSONObject.parseObject(result);
         return  resultObj;
    }


    /**
    * 统一下单接口
    * @author Logan
    * @date 2019-01-23 17:11
    * @param pushOrderVO

    * @return
    */
    public JSONObject pushOrder(PayPushOrderVO pushOrderVO){
       String url = getPushOrderUrl();
       String json = JSONObject.toJSONString(pushOrderVO);
       JSONObject param = JSONObject.parseObject(json);
       String content = HttpUtil.doPostSSL(url,param);
       JSONObject result = JSONObject.parseObject(content);
       return result;
    }
}
