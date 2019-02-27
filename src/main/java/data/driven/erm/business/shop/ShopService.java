package data.driven.erm.business.shop;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @Author: lxl
 * @describe
 * @Date: 2019/2/26 17:19
 * @Version 1.0
 */
public interface ShopService {

    /**
     * @description 通过级别得到最大的排序信息
     * @author lxl
     * @date 2019-02-26 17:22
     * @param level 级别
     * @return
     */
    Integer getCommodityCatgOrd(Integer level);

    /**
     * @description 保存与修改类目信息
     * @author lxl
     * @date 2019-02-26 17:35
     * @param catgId 主键id
     * @param catgName 分类名称
     * @param catgCode 分类层级码
     * @param catgLevel 层级
     * @param saveType 保存类型 insert 新增 修改 update
     * @return
     */
    JSONObject updateCommodityCatgOrd(String catgId, String catgName, String catgCode, Integer catgLevel, String saveType);

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
     * @param url 图片地址
     * @param isMarkeTable 上架状态 0 未上架 1 上架
     * @return
     */
    JSONObject saveCommodityInfo(String url,String commodityId, String catgId,String catg_code, String commodityName,
                                 BigDecimal suggestPrices, BigDecimal prices, String saveType,Integer isMarkeTable);

}
