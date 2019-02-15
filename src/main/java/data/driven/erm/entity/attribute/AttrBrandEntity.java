package data.driven.erm.entity.attribute;

import java.util.Date;

/**
 * @Author: lxl
 * @describe 属性品牌实体
 * @Date: 2019/2/15 12:04
 * @Version 1.0
 */
public class AttrBrandEntity {
    /**
     * 主键
     */
    private String attrBrandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建时间
     */
    private Date updateAt;

    public String getAttrBrandId() {
        return attrBrandId;
    }

    public void setAttrBrandId(String attrBrandId) {
        this.attrBrandId = attrBrandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
