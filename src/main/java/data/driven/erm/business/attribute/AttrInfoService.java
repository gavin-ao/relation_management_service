package data.driven.erm.business.attribute;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.vo.attribute.AttrInfoVO;

import javax.xml.bind.util.JAXBSource;
import java.util.List;

/**
 * @Author: lxl
 * @describe 属性表Sercice
 * @Date: 2019/2/15 12:12
 * @Version 1.0
 */
public interface AttrInfoService {

    /**
     * @description 增加属性码
     * @author lxl
     * @date 2019-02-15 14:28
     * @param attrType 属性类别
     * @return 
     */
    String insertCode(Integer attrType);

    /**
     * @description 获取属性名称和属性值列表
     * @author lxl
     * @date 2019-02-15 15:09
     * @param attrType 属性类型
     * @return
     */
    AttrInfoVO getAttrInfoAndValue(Integer attrType);

    /**
     * @description 新增属性同时新增属性值
     * @author lxl
     * @date 2019-02-15 15:38
     * @param attrName 属性名称
     * @param attrValueList 属性值列表
     * @param attrType 属性类型 0 描述 1 规格
     * @return 
     */
    JSONObject insertAtrrInfoAndValue(String attrName, List<String> attrValueList,Integer attrType);

    /**
     * @description 修改属性名称
     * @author lxl
     * @date 2019-02-15 16:24
     * @param attrId 属性表id
     * @param attrName 属性名称
     * @return
     */
    JSONObject updateAttrName(String attrId,String attrName);
}























