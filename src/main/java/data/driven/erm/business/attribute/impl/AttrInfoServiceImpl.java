package data.driven.erm.business.attribute.impl;

import data.driven.erm.business.attribute.AttrInfoService;
import data.driven.erm.dao.JDBCBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
