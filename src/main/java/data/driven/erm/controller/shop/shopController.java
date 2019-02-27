package data.driven.erm.controller.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.commodity.CommodityCatgService;
import data.driven.erm.business.commodity.CommodityService;
import data.driven.erm.business.shop.ShopService;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.attribute.AttrInfoVO;
import data.driven.erm.vo.commodity.CommodityCatgVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lxl
 * @describe 此Controller都是提供给电商管理后台接口的
 * @Date: 2019/2/26 17:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/shop")
public class shopController {
    @Autowired
    private ShopService shopService;

    /**
     * @description 保存类目信息
     * @author lxl
     * @date 2019-02-26 17:12
     * @param catgId 主键id
     * @param catgName 分类名称
     * @param catgCode 分类层级码
     * @param catgLevel 层级
     * @param saveType 保存类型 insert 新增 修改 update
     * @return
     */
    @RequestMapping("/saveCommodityCatg")
    @ResponseBody
    public JSONObject saveCommodityCatg(String catgId,String catgName,String catgCode,Integer catgLevel,String saveType){
        return shopService.updateCommodityCatgOrd(catgId,catgName,catgCode,catgLevel,saveType);
    }

    /**
     * @description 保存商品信息
     * @author lxl
     * @date 2019-02-26 18:13
     * @param commodityId 商品id
     * @param catgId 类目id
     * @param commodityName 商品名称
     * @param suggestPrices 建议价格
     * @param prices 零售价格
     * @param saveType 保存类型 insert 新增 update 修改
     * @param urlPath 图片的URL
     * @return
     */
    @RequestMapping("/saveCommodityInfo")
    @ResponseBody
    public JSONObject saveCommodityInfo(HttpServletRequest request,String urlPath,String commodityId, String catgId, String catg_code,String commodityName, BigDecimal suggestPrices, BigDecimal prices, String saveType){

//    public JSONObject saveCommodityInfo(HttpServletRequest req,String urlPath){
//       String s = req.getParameter("commodityId");
//        return null;
        return shopService.saveCommodityInfo(urlPath,commodityId,catgId,catg_code,commodityName,suggestPrices,prices,saveType);
//        return shopService.saveCommodityInfo("http://shop.xinkebao.cn/files/upload/img/u/2019/02/21/GJIR.jpg","8","8","008",
//                "【茅台官方授权】贵州茅台迎宾酒 53度 中国红 500ml",new BigDecimal(228),new BigDecimal(0.02),"insert");
    }
}
















