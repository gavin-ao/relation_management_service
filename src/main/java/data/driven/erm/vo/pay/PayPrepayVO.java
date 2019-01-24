package data.driven.erm.vo.pay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @program: pay-relation_management_service
 * @description: 支付信息参数VO
 * @author: Logan
 * @create: 2019-01-23 16:00
 **/

public class PayPrepayVO implements Serializable {
    /**小程序appId**/
    @NotNull
    private String appId;
    /**门店id**/
    @NotNull
    private String storeId;
    /**商户订单号，32位以内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一**/
    @NotNull
    private String outTradeNo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
