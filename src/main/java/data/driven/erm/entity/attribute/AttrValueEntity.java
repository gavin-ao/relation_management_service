package data.driven.erm.entity.attribute;

import java.util.Date;

/**
 * @Author: lxl
 * @describe 属性值实体
 * @Date: 2019/2/15 11:59
 * @Version 1.0
 */
public class AttrValueEntity {
    /**
     * 主键
     */
    private String attrValueId;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 属性表id
     */
    private String attrId;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 修改时间
     */
    private Date updateAt;

    public AttrValueEntity(){}
    public AttrValueEntity(String attrValueId, String attrValue, String attrId, Date createAt, Date updateAt) {
        this.attrValueId = attrValueId;
        this.attrValue = attrValue;
        this.attrId = attrId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(String attrValueId) {
        this.attrValueId = attrValueId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
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

























