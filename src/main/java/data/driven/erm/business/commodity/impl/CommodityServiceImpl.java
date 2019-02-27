package data.driven.erm.business.commodity.impl;

import data.driven.erm.business.commodity.CommodityService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.vo.commodity.CommodityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品详情Service
 * @author hejinkai
 * @date 2018/10/1
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<CommodityVO> findCommodityListByCatgId(String catgId) {
        String sql = "select c.commodity_id,c.commodity_name,c.suggest_prices,c.prices,c.remark,p.file_path from" +
                " commodity_info c left join sys_picture p on p.picture_id = c.picture_id where c.catg_id = ?" +
                " and c.is_Marke_Table = 1 order by c.ord";
        List<CommodityVO> list = jdbcBaseDao.queryList(CommodityVO .class, sql, catgId);
        return list;
    }

    @Override
    public CommodityVO getCommodityById(String commodityId) {
        String sql = "select c.commodity_id,c.commodity_name,c.suggest_prices,c.prices,c.remark,p.file_path,p.picture_id from commodity_info c left join sys_picture p on p.picture_id = c.picture_id where c.commodity_id = ?";
        List<CommodityVO> list = jdbcBaseDao.queryList(CommodityVO.class, sql, commodityId);
        if(list != null && list.size() > 0){
            CommodityVO commodityVO = list.get(0);
            commodityVO.setCommodityImageTextList(getCommodityImageText(commodityId));
            return commodityVO;
        }
        return null;
    }

    /**
     * 获取图文详情
     * @param commodityId
     * @return
     */
    private List<String> getCommodityImageText(String commodityId){
        String sql = "select p.file_path from commodity_image_text cit left join sys_picture p on p.picture_id = cit.picture_id where cit.commodity_id = ? order by cit.ord";
        List<String> list = jdbcBaseDao.getColumns(String.class, sql, commodityId);
        return list;
    }
}
