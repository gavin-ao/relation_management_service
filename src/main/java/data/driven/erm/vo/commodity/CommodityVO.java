package data.driven.erm.vo.commodity;

import data.driven.erm.entity.commodity.CommodityEntity;

import java.util.List;

/**
 * 商品VO
 * @author hejinkai
 * @date 2018/10/1
 */
public class CommodityVO extends CommodityEntity {
    /** 图片 **/
    public String filePath;
    /** 图文详情 **/
    public List<String> commodityImageTextList;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getCommodityImageTextList() {
        return commodityImageTextList;
    }

    public void setCommodityImageTextList(List<String> commodityImageTextList) {
        this.commodityImageTextList = commodityImageTextList;
    }
}
