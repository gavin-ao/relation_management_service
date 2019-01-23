package data.driven.erm.entity.wechat;

import java.util.Date;

/**
 * 小程序信息
 * @author hejinkai
 * @date 2018/6/27
 */
public class WechatAppInfoEntity {
    /** 主键 **/
    private String appInfoId;
    /** 小程序名称 **/
    private String appName;
    /** 小程序微信id  **/
    private String appid;
    /** 小程序secret  **/
    private String secret;
    /** 创建时间 **/
    private Date createAt;
    /** 创建人 **/
    private String creator;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String securityId;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
