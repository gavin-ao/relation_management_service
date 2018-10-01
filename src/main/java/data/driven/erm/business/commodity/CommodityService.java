package data.driven.erm.business.commodity;

import data.driven.erm.vo.commodity.CommodityVO;

import java.util.List;

/**
 * 商品详情Service
 * @author hejinkai
 * @date 2018/10/1
 */
public interface CommodityService {

    /**
     * 根据分类id获取商品数据
     * @param catgId
     * @return
     */
    public List<CommodityVO> findCommodityListByCatgId(String catgId);

    /**
     * 根据id获取对象信息
     * @param commodityId
     * @return
     */
    public CommodityVO getCommodityById(String commodityId);

}
