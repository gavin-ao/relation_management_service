package data.driven.erm.entity.attribute;

import java.util.Date;

/**
 * @Author: lxl
 * @describe 属性表实体
 * @Date: 2019/2/15 11:55
 * @Version 1.0
 */
public class AttrInfoEntity {
    /**
     * 主键
     */
    private String attrId;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性类型 0 描述  1 规格
     */
    private Integer attrType;

    /**
     * 属性码
     */
    private String attrCode;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    public AttrInfoEntity(){}
    public AttrInfoEntity(String attrId, String attrName, Integer attrType, String attrCode, Date createAt, Date updateAt) {
        this.attrId = attrId;
        this.attrName = attrName;
        this.attrType = attrType;
        this.attrCode = attrCode;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
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




























