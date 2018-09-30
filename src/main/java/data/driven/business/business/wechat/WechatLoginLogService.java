package data.driven.business.business.wechat;

/**
 * 微信登录日志
 * @author hejinkai
 * @date 2018/7/4
 */
public interface WechatLoginLogService {

    /**
     * 小程序登录日志记录
     * @param wechatUserId
     * @param appInfoId
     */
    public void insertLoginLog(String wechatUserId, String appInfoId);

}
