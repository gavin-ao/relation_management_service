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
    @NotNull
    private String appId;
    @NotNull
    private String storeId;
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
