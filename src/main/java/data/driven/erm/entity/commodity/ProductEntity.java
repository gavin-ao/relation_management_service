package data.driven.erm.entity.commodity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表entity
 * @author hejinkai
 * @date 2018/8/18
 */
public class ProductEntity {

    /** 主键 **/
    private Integer id;
    /** 产品分类表主键 **/
    private Integer productCategoryId;
    /** 分类层级码 **/
    private String catgCode;
    /** 产品名称 **/
    private String name;
    /**商品编号**/
    private String sn;
    /**商品销量**/
    private Integer sales;
    /**商品大标题**/
    private String caption;
    /**缩略图**/
    private String thumbnail;
    /** 商品图文介绍 **/
    private String introduction;

    /**市场价，原价，划线价**/
    private BigDecimal marketPrice;
    /** 现价零售价格 **/
    private BigDecimal price;
    /** 是否上架 **/
    private Boolean isMarketable;
    /** 设置定时上架时间 **/
    private Date fixedTime;
    /** 库存**/
    private Integer stock;
    /**json字符串，其他属性**/
    private String parameterValue;
    /** 备注 **/
    private String remark;
    /** 排序 **/
    private Integer sortNum;
    /**1:推荐**/
    private Integer isRecommend;
    /**删除标记**/
    private Boolean active;
    /** 创建时间 **/
    private Date createDate;
    /** 修改时间 **/
    private Date modifyDate;
    /** 品牌id **/
    private Integer brandId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getMarketable() {
        return isMarketable;
    }

    public void setMarketable(Boolean marketable) {
        isMarketable = marketable;
    }

    public Date getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(Date fixedTime) {
        this.fixedTime = fixedTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
