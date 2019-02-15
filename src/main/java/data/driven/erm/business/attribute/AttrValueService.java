package data.driven.erm.business.attribute;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @Author: lxl
 * @describe 属性值Sercice
 * @Date: 2019/2/15 12:13
 * @Version 1.0
 */
public interface AttrValueService {

    /**
     * @description 批量增加属性值信息
     * @author lxl
     * @date 2019-02-15 15:49
     * @param attrValueList 属性值信息
     * @param attrId 属性表id
     * @param date 时间
     * @return
     */
    JSONObject insertAttrValueList(List<String> attrValueList, String attrId, Date date);

    /**
     * @description 新增属性值信息
     * @author lxl
     * @date 2019-02-15 15:57
     * @param attrValue 属性值
     * @param attrId 属性表id
     * @param createAt 创建时间
     * @param updateAt 修改时间
     * @return
     */
    JSONObject insertAttrValue(String attrValue,String attrId,Date createAt,Date updateAt);
}
