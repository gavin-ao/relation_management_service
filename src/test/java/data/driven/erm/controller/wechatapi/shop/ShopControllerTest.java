package data.driven.erm.controller.wechatapi.shop;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.shop.ShopService;
import data.driven.erm.controller.wechatapi.UnitTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @Author: lxl
 * @describe 此Controller都是提供给电商管理后台接口的测试
 * @Date: 2019/2/26 17:53
 * @Version 1.0
 */
public class ShopControllerTest extends UnitTestBase {
    @Autowired
    private ShopService shopService;

//    @Test
//    public void saveCommodityCatg(){
//        JSONObject resultJsonObject = shopService.updateCommodityCatgOrd("8","特供酒系列1","006",3,"update");
//        System.out.println(resultJsonObject.toString());
//    }

    @Test
    public void saveCommodityInfo(){
        shopService.saveCommodityInfo("http://shop.xinkebao.cn/files/upload/img/u/2019/02/21/GJIR.jpg","8","8","008",
                "【茅台官方授权】贵州茅台迎宾酒 53度 中国红 500ml",new BigDecimal(228),new BigDecimal(0.02),"insert");
    }
}
