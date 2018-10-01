package data.driven.erm.vo.wechat;

import data.driven.erm.entity.wechat.WechatUserInfoEntity;

/**
 * 用户信息vo，包含openId
 * @author hejinkai
 * @date 2018/6/27
 */
public class WechatUserInfoVO extends WechatUserInfoEntity {
    /** 用户对应小程序在微信中的唯一标识 **/
    private String openId;
    /** 小程序id **/
    private String appInfoId;
    /** 微信用户与小程序关联表id **/
    private String wechatMapId;
    /** 邀请人 **/
    private String inviter;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public String getWechatMapId() {
        return wechatMapId;
    }

    public void setWechatMapId(String wechatMapId) {
        this.wechatMapId = wechatMapId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    /**
     * 清楚敏感信息
     */
    public void clearSensitiveInfo(){
        setOpenId(null);
        setUnionId(null);
    }
}
