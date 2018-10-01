package data.driven.erm.common;

import data.driven.erm.vo.wechat.WechatUserInfoVO;

/**
 * redis缓存的session实体内容
 * @author hejinkai
 * @date 2018/6/27
 */
public class WechatApiSessionBean {
    /** sessionKey **/
    private String sessionKey;
    /** userInfo **/
    private WechatUserInfoVO userInfo;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public WechatUserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(WechatUserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

}
