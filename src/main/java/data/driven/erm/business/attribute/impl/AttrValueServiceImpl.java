package data.driven.erm.business.attribute.impl;

import data.driven.erm.business.attribute.AttrValueService;
import data.driven.erm.dao.JDBCBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lxl
 * @describe 属性值Impl
 * @Date: 2019/2/15 12:14
 * @Version 1.0
 */
@Service
public class AttrValueServiceImpl implements AttrValueService{
    /**日志**/
    private static final Logger logger = LoggerFactory.getLogger(AttrValueServiceImpl.class);

    /**数据库**/
    @Autowired
    private JDBCBaseDao jdbcBaseDao;
}
