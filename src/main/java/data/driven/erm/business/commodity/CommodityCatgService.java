package data.driven.erm.business.commodity;

import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.vo.commodity.CommodityCatgVO;

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

    /**
    * 根据父节点的Code获取子节点的目录页
     * parentCode为""时，则返回根节点的目录页面
    * @author Logan
    * @date 2019-02-15 16:08
    * @param parentCode
    * @param keyword
    * @param userId
    * @param pageBean

    * @return
    */
    public Page<CommodityCatgVO> findfindCommodityCatgPage(Integer level, String parentCode, String keyword, String userId, PageBean pageBean);
}
