package data.driven.erm.vo.attribute;

import data.driven.erm.entity.attribute.AttrInfoEntity;
import data.driven.erm.entity.attribute.AttrValueEntity;

import java.util.List;

/**
 * @Author: lxl
 * @describe 属性与属性值关联实体
 * @Date: 2019/2/15 15:03
 * @Version 1.0
 */
public class AttrInfoVO extends AttrInfoEntity {

    /**
     * 属性值列表
     */
    private List<AttrValueEntity> attrValueEntityList;

    public List<AttrValueEntity> getAttrValueEntityList() {
        return attrValueEntityList;
    }

    public void setAttrValueEntityList(List<AttrValueEntity> attrValueEntityList) {
        this.attrValueEntityList = attrValueEntityList;
    }
}
