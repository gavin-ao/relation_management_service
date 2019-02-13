package data.driven.erm.entity.wechat;

import java.util.Date;

/**
 * @Author: lxl
 * @describe 图片文件信息实体
 * @Date: 2019/2/13 10:22
 * @Version 1.0
 */
public class SysPictureEntity {
    /** 图片主键 **/
    private String pictureId;
    /** 图片存储路径 **/
    private String filePath;
    /** 文件真实名称 **/
    private String realName;
    /** 微信用户 **/
    private String wechatUser;
    /** 创建日期 **/
    private Date createAt;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(String wechatUser) {
        this.wechatUser = wechatUser;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
