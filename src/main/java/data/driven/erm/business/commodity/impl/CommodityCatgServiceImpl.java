package data.driven.erm.business.commodity.impl;

import data.driven.erm.business.commodity.CommodityCatgService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类service
 * @author hejinkai
 * @date 2018/10/1
 */
@Service
public class CommodityCatgServiceImpl implements CommodityCatgService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<CommodityCatgEntity> findCommodityCatgList() {
        String sql = "select catg_id,catg_name,catg_code,catg_level,ord from commodity_catg_info order by catg_level,ord,catg_id";
        List<CommodityCatgEntity> list = jdbcBaseDao.queryList(CommodityCatgEntity.class, sql);
        return list;
    }
}
