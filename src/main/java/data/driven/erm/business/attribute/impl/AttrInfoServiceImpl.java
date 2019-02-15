package data.driven.erm.business.attribute.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.attribute.AttrInfoService;
import data.driven.erm.business.attribute.AttrValueService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.attribute.AttrInfoEntity;
import data.driven.erm.entity.attribute.AttrValueEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.attribute.AttrInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Author: lxl
 * @describe 属性表Impl
 * @Date: 2019/2/15 12:13
 * @Version 1.0
 */
@Service
public class AttrInfoServiceImpl implements AttrInfoService{
    /**日志**/
    private static final Logger logger = LoggerFactory.getLogger(AttrInfoServiceImpl.class);

    /**数据库**/
    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Autowired
    private AttrValueService attrValueService;

    /**
     * @description 增加属性码
     * @author lxl
     * @date 2019-02-15 14:28
     * @param attrType 属性类别
     * @return
     */
    @Override
    public String insertCode(Integer attrType) {
        String codeString = getCode(attrType);
        int codeNum = 1;
        String attrCode = "00000001";
        if (codeString != null){
            codeNum = Integer.parseInt(codeString.split("-")[1]) + 1;
            attrCode = String.format("%08d",codeNum);
        }
        if (attrType == 0){
            return "A-"+attrCode;
        }else{
            return "B-"+attrCode;
        }
    }

    /**
     * @description 获取属性名称和属性值列表
     * @author lxl
     * @date 2019-02-15 15:09
     * @param attrType 属性类型
     * @return
     */
    @Override
    public AttrInfoVO getAttrInfoAndValue(Integer attrType) {
        String attrInfoSql = "select attr_id,attr_name,attr_type,attr_code,create_at,update_at from attr_info" +
                " where attr_type = ?";
        List<AttrInfoVO> AttrInfoVOList = jdbcBaseDao.queryList(AttrInfoVO.class,attrInfoSql,attrType);
        if (AttrInfoVOList != null && AttrInfoVOList.size() > 0){
            AttrInfoVO attrInfoVo =  AttrInfoVOList.get(0);
            attrInfoVo.setAttrValueEntityList(getAttrValueList(attrInfoVo.getAttrId()));
            return attrInfoVo;
        }
        return null;
    }

    /**
     * @description 新增属性同时新增属性值
     * @author lxl
     * @date 2019-02-15 15:38
     * @param attrName 属性名称
     * @param attrValueList 属性值列表
     * @param attrType 属性类型 0 描述 1 规格
     * @return
     */
    @Override
    public JSONObject insertAtrrInfoAndValue(String attrName, List<String> attrValueList, Integer attrType) {
        String attrId = UUIDUtil.getUUID();
        Date create = new Date();
        String attrCode = insertCode(attrType);
        AttrInfoEntity attrInfoEntity = new AttrInfoEntity(attrId,attrName,attrType,attrCode,create,create);
        try {
            jdbcBaseDao.insert(attrInfoEntity,"attr_info");
            attrValueService.insertAttrValueList(attrValueList,attrId,create);
            return JSONUtil.putMsg(true,"200","插入属性信息和属性值成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"101","插入属性信息和属性值失败");
    }

    /**
     * @description 修改属性名称
     * @author lxl
     * @date 2019-02-15 16:24
     * @param attrId 属性表id
     * @param attrName 属性名称
     * @return
     */
    @Override
    public JSONObject updateAttrName(String attrId,String attrName) {
        String sql = "update attr_info set attr_name= ? where attr_id = ?";
        try {
            jdbcBaseDao.executeUpdate(sql,attrName,attrId);
            return JSONUtil.putMsg(true,"200","修改属性名称成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONUtil.putMsg(false,"101","修改属性名称失败");
    }

    /**
     * @description 得到当前数据库中最新的code码
     * @author lxl
     * @date 2019-02-15 14:30
     * @param attrType
     * @return
     */
    private String getCode(Integer attrType){
        String sql = "select attr_code from attr_info where attr_type = ? ORDER BY create_at DESC limit 1";
        Object object = jdbcBaseDao.getColumn(sql,attrType);
        if (object !=null){
            return object.toString();
        }else{
            return null;
        }
    }

    /**
     * @description 获取属性对应的所有属性值信息
     * @author lxl
     * @date 2019-02-15 15:16
     * @param attrId 属性表id
     * @return
     */
    private List<AttrValueEntity> getAttrValueList(String attrId){
        String sql = "select attr_value_id,attr_value,attr_id,create_at,update_at from attr_value where attr_id = ?";
        return jdbcBaseDao.queryList(AttrValueEntity.class,sql,attrId);
    }
}
