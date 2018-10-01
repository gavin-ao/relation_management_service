package data.driven.erm.entity.commodity;

import java.util.Date;

/**
 * 商品分类表Entity
 * @author hejinkai
 * @date 2018/8/18
 */
public class CommodityCatgEntity {
    /** 主键 **/
    private String catgId;
    /** 分类名称 **/
    private String catgName;
    /** 分类层级码 **/
    private String catgCode;
    /** 层级 **/
    private Integer catgLevel;
    /** 排序 **/
    private Integer ord;
    /** 创建时间 **/
    private Date createAt;
    /** 创建人 **/
    private String creator;

    public String getCatgId() {
        return catgId;
    }

    public void setCatgId(String catgId) {
        this.catgId = catgId;
    }

    public String getCatgName() {
        return catgName;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public Integer getCatgLevel() {
        return catgLevel;
    }

    public void setCatgLevel(Integer catgLevel) {
        this.catgLevel = catgLevel;
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
