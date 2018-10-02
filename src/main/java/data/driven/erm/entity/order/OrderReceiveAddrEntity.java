package data.driven.erm.entity.order;

import java.util.Date;

/**
 * 订单收件地址
 * @author hejinkai
 * @date 2018/10/2
 */
public class OrderReceiveAddrEntity {
    /** 主键 **/
    private String addrId;
    /** 用户id **/
    private String wechatUserId;
    /** 别名，标签 **/
    private String alias;
    /** 国家 **/
    private String country;
    /** 省份/直辖市 **/
    private String province;
    /** 地市 **/
    private String city;
    /** 行政区/管辖区/县级单位 **/
    private String region;
    /** 补充详情地址 **/
    private String detailAddr;
    /** 收件人 **/
    private String addressee;
    /** 手机号码 **/
    private String phoneNumber;
    /** 固定电话 **/
    private String telephone;
    /** 最新一次下单地址 0 - 否， 1 - 是 **/
    private Integer lastChoose;
    /** 默认地址 0 - 否， 1 - 是 **/
    private Integer defaultAddr;
    /** 创建日期 **/
    private Date createAt;

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getLastChoose() {
        return lastChoose;
    }

    public void setLastChoose(Integer lastChoose) {
        this.lastChoose = lastChoose;
    }

    public Integer getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(Integer defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
