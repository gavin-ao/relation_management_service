package data.driven.erm.entity.wechat;

import data.driven.erm.util.UUIDUtil;

import java.util.Date;

/**
 * 微信行为日志
 * @author hejinkai
 * @date 2019/01/24
 */
public class WechatBehaviorLogEntity {
    /** 微信行为日志主键 **/
    private String logId;
    /** 功能编码 **/
    private String funcCode;
    /** 微信行为日志主键 **/
    private String travelsId;
    /** 景区主键 **/
    private String scenicSpotId;
    /** 活动id **/
    private String actId;
    /** 门店id **/
    private String storeId;
    /** 微信用户 **/
    private String wechatUserId;
    /** 微信小程序id **/
    private String appInfoId;
    /** 设备品牌 **/
    private String equipmentBrand;
    /** 设备型号 **/
    private String equipmentModel;
    /** 微信版本号 **/
    private String equipmentVersion;
    /** 操作系统及版本 **/
    private String equipmentSystem;
    /** 客户端平台 **/
    private String equipmentPlatform;
    /** 客户端基础库版本 **/
    private String equipmentSdkversion;
    /** 城市 **/
    private String city;
    /** 省份 **/
    private String province;
    /** 国家 **/
    private String country;
    /** 区 **/
    private String district;
    /** 街道 **/
    private String street;
    /** 记录时间 **/
    private Date logAt;

    /**
     * 初始化字段
     * @param wechatUserId
     * @param appInfoId
     */
    public void initFiled(String wechatUserId, String appInfoId){
        this.logId = UUIDUtil.getUUID();
        this.wechatUserId = wechatUserId;
        this.appInfoId = appInfoId;
        this.logAt = new Date();
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getTravelsId() {
        return travelsId;
    }

    public void setTravelsId(String travelsId) {
        this.travelsId = travelsId;
    }

    public String getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(String scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public String getEquipmentBrand() {
        return equipmentBrand;
    }

    public void setEquipmentBrand(String equipmentBrand) {
        this.equipmentBrand = equipmentBrand;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentVersion() {
        return equipmentVersion;
    }

    public void setEquipmentVersion(String equipmentVersion) {
        this.equipmentVersion = equipmentVersion;
    }

    public String getEquipmentSystem() {
        return equipmentSystem;
    }

    public void setEquipmentSystem(String equipmentSystem) {
        this.equipmentSystem = equipmentSystem;
    }

    public String getEquipmentPlatform() {
        return equipmentPlatform;
    }

    public void setEquipmentPlatform(String equipmentPlatform) {
        this.equipmentPlatform = equipmentPlatform;
    }

    public String getEquipmentSdkversion() {
        return equipmentSdkversion;
    }

    public void setEquipmentSdkversion(String equipmentSdkversion) {
        this.equipmentSdkversion = equipmentSdkversion;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Date getLogAt() {
        return logAt;
    }

    public void setLogAt(Date logAt) {
        this.logAt = logAt;
    }
}
