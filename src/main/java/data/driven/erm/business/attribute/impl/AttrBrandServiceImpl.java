package data.driven.erm.business.attribute.impl;

import data.driven.erm.business.attribute.AttrBrandService;
import data.driven.erm.dao.JDBCBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lxl
 * @describe 属性品牌Impl
 * @Date: 2019/2/15 12:11
 * @Version 1.0
 */
@Service
public class AttrBrandServiceImpl implements AttrBrandService{
    /**日志**/
    private static final Logger logger = LoggerFactory.getLogger(AttrBrandServiceImpl.class);

    /**数据库**/
    @Autowired
    private JDBCBaseDao jdbcBaseDao;
}
