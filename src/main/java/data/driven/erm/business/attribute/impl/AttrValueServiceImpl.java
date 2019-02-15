package data.driven.erm.business.attribute.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.attribute.AttrValueService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.attribute.AttrValueEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: lxl
 * @describe 属性值Impl
 * @Date: 2019/2/15 12:14
 * @Version 1.0
 */
@Service
public class AttrValueServiceImpl implements AttrValueService {
    /**
     * 日志
     **/
    private static final Logger logger = LoggerFactory.getLogger(AttrValueServiceImpl.class);

    /**
     * 数据库
     **/
    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    /**
     * @param attrValueList 属性值信息
     * @param attrId        属性表id
     * @param date          时间
     * @return
     * @description 批量增加属性值信息
     * @author lxl
     * @date 2019-02-15 15:49
     */
    @Override
    public JSONObject insertAttrValueList(List<String> attrValueList, String attrId, Date date) {
        try {
            for (String attrValue : attrValueList) {
                insertAttrValue(attrValue,attrId,date,date);
            }
            return JSONUtil.putMsg(true,"200","批量插入属性值成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"101","批量插入属性值失败");
    }

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
    @Override
    public JSONObject insertAttrValue(String attrValue, String attrId, Date createAt, Date updateAt) {
        AttrValueEntity attrValueEntity = new AttrValueEntity(UUIDUtil.getUUID(), attrValue, attrId, createAt, updateAt);
        try{
            jdbcBaseDao.insert(attrValueEntity,"attr_value");
            return JSONUtil.putMsg(true,"200","插入属性值信息成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"101","插入属性值信息失败");
    }

    /**
     * @description 修改属性值信息
     * @author lxl
     * @date 2019-02-15 16:36
     * @param attrValueId 属性值表id
     * @param attrValue 属性值
     * @return
     */
    @Override
    public JSONObject updateAttrValue(String attrValueId, String attrValue) {
        String sql = "update attr_value set attr_value = ? where attr_value_id = ?";
        try {
            jdbcBaseDao.executeUpdate(sql,attrValue,attrValueId);
            return JSONUtil.putMsg(true,"200","修改属性值信息成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"101","修改属性值信息失败");
    }
}
