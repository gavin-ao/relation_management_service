package data.driven.erm.business.commodity;

import data.driven.erm.entity.commodity.CommodityCatgEntity;

import java.util.List;

/**
 * 商品分类service
 * @author hejinkai
 * @date 2018/10/1
 */
public interface CommodityCatgService {
    /**
     * 查找所有的产品分类
     * @return
     */
    public List<CommodityCatgEntity> findCommodityCatgList();
}
