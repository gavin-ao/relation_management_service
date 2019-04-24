package data.driven.erm.business.commodity.impl;

import data.driven.erm.business.commodity.ProductService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品详情Service
 * @author hejinkai
 * @date 2018/10/1
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<ProductEntity> findProductListByCatgId(String catgId) {
        String sql = "select id,`name`,caption,thumbnail,sn,sales,isMarketable,price,\n" +
                     "marketPrice,stock,parameterValue,sort_num,active,fixedTime,brandId,\n" +
                     "createDate,modifyDate\n" +
                     "from tb_product \n" +
                     "where active=1 and isMarketable =1 and productCategoryId=? order by sort_num";
        List<ProductEntity> list = jdbcBaseDao.queryList(ProductEntity.class, sql, catgId);
        return list;
    }

    @Override
    public ProductEntity getProductById(Integer productId) {
        String sql =  "select id,`name`,caption,thumbnail,sn,sales,isMarketable,price,\n" +
                "marketPrice,stock,parameterValue,introduction,sort_num,active,fixedTime,brandId,\n" +
                "createDate,modifyDate\n" +
                "from tb_product \n" +
                "where id=? order by sort_num";
        return jdbcBaseDao.executeQuery(ProductEntity.class,sql,productId);
    }

}
