package data.driven.erm.entity.commodity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表entity
 * @author hejinkai
 * @date 2018/8/18
 */
public class CommodityEntity {

    /** 主键 **/
    private String commodityId;
    /** 产品分类表主键 **/
    private String catgId;
    /** 分类层级码 **/
    private String catgCode;
    /** 产品名称 **/
    private String commodityName;
    /** 建议价格 **/
    private BigDecimal suggestPrices;
    /** 零售价格 **/
    private BigDecimal prices;
    /** 备注 **/
    private String remark;
    /** 图片id **/
    private String pictureId;
    /** 排序 **/
    private Integer ord;
    /** 创建时间 **/
    private Date createAt;
    /** 创建人 **/
    private String creator;

    /**上架状态**/
    private Integer isMarketable;

    public Integer getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Integer isMarketable) {
        this.isMarketable = isMarketable;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCatgId() {
        return catgId;
    }

    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getSuggestPrices() {
        return suggestPrices;
    }

    public void setSuggestPrices(BigDecimal suggestPrices) {
        this.suggestPrices = suggestPrices;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
