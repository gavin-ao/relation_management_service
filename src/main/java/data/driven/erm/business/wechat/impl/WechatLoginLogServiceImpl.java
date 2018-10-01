package data.driven.erm.business.wechat.impl;

import data.driven.erm.business.wechat.WechatLoginLogService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hejinkai
 * @date 2018/7/4
 */
@Service
public class WechatLoginLogServiceImpl implements WechatLoginLogService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public void insertLoginLog(String wechatUserId, String appInfoId) {
        String id = UUIDUtil.getUUID();
        Date loginAt = new Date();
        String sql = "insert into wechat_login_log(log_id,wechat_user_id,app_info_id,login_at) values(?,?,?,?)";
        jdbcBaseDao.executeUpdate(sql, id, wechatUserId, appInfoId, loginAt);
    }
}
