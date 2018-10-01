package data.driven.erm.business.wechat;

import data.driven.erm.entity.wechat.WechatAppInfoEntity;

import java.util.List;

/**
 * 小程序信息service
 * @author hejinkai
 * @date 2018/6/27
 */
public interface WechatAppInfoService {

    /**
     * 根据appid查询对象
     * @param appid
     * @return
     */
    public WechatAppInfoEntity getAppInfo(String appid);

    /**
     * 根据用户id查询小程序
     * @param userInfoId
     * @return
     */
    public List<WechatAppInfoEntity> findAppInfoListByUser(String userInfoId);

    /**
     * 根据微信小程序查询轮播图
     * @param appInfoId
     * @return
     */
    public List<String> findSowingMap(String appInfoId);
}
