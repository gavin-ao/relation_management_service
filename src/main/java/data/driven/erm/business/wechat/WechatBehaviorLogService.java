package data.driven.erm.business.wechat;

import data.driven.erm.entity.wechat.WechatBehaviorLogEntity;

/**
 * 微信行为日志Service
 * @author hejinkai
 * @date 2019/01/24
 */
public interface WechatBehaviorLogService {

    /**
     * 新建日志
     * @param wechatBehaviorLog
     */
    public void insertLog(WechatBehaviorLogEntity wechatBehaviorLog);
}
