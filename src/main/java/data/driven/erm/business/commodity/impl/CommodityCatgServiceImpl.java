package data.driven.erm.business.commodity.impl;

import data.driven.erm.business.commodity.CommodityCatgService;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.vo.commodity.CommodityCatgVO;
import data.driven.erm.vo.order.OrderRefundDetailInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 根据父节点的Code获取子节点的目录页
     * level=0时，则返回根节点的目录页面
     *
     * @param parentCode
     * @param keyword
     * @param userId
     * @param pageBean
     * @return
     * @author Logan
     * @date 2019-02-15 16:08
     */
    @Override
    public Page<CommodityCatgVO> findfindCommodityCatgPage(Integer level,String parentCode, String keyword, String userId, PageBean pageBean) {
        String sql = "";
        StringBuffer where = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();

        if(level>0){
            sql= "select * from commodity_catg_info where catg_level =? and catg_code like ? and catg_code <> ?";
            paramList.add(level+1);
            paramList.add(parentCode+ "%");
            paramList.add(parentCode);
        }else{
            sql= "select * from commodity_catg_info where catg_level=1";
        }
        if (keyword != null) {
            where.append(" and catg_code like ?");
            paramList.add("%" + keyword.trim() + "%");
        }

        if (where.length() > 0) {
            sql += where;
        }
        sql += " order by create_at desc";

        return jdbcBaseDao.queryPageWithListParam(CommodityCatgVO.class, pageBean, sql, paramList);
    }
}
