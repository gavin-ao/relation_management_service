package data.driven.erm.vo.order;

import data.driven.erm.entity.order.OrderRebateEntity;

/**
 * @author hejinkai
 * @date 2018/10/8
 */
public class OrderRebateVO extends OrderRebateEntity {
    /** 返利获得者的昵称 **/
    private String nickName;
    /** 返利获得者的省份 **/
    private String province;
    /** 返利获得者的头像 **/
    private String avatarUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
