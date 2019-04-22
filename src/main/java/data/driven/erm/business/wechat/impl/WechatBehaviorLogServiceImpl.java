package data.driven.erm.business.wechat.impl;

import data.driven.erm.business.wechat.WechatBehaviorLogService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.wechat.WechatBehaviorLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 行为日志分析
 * @author hejinkai
 * @date 2019/01/24
 */
@Service
public class WechatBehaviorLogServiceImpl implements WechatBehaviorLogService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public void insertLog(WechatBehaviorLogEntity wechatBehaviorLog) {
        jdbcBaseDao.insert(wechatBehaviorLog, "wechat_behavior_log");
    }
}
