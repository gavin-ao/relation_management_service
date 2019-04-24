package data.driven.erm.business.commodity;

import data.driven.erm.entity.commodity.ProductEntity;

import java.util.List;

/**
 * 商品详情Service
 * @author hejinkai
 * @date 2018/10/1
 */
public interface ProductService {

    /**
     * 根据分类id获取商品数据
     * 注意：提供列表使用，不查询图文介绍的富文本信息
     * @param catgId
     * @return
     */
    List<ProductEntity> findProductListByCatgId(String catgId);

    /**
     * 根据id获取对象信息
     * @param productId
     * @return
     */
    ProductEntity getProductById(Integer productId);

}
