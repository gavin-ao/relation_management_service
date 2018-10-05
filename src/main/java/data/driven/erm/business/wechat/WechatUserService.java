package data.driven.erm.business.wechat;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.entity.wechat.WechatUserInfoEntity;
import data.driven.erm.vo.wechat.WechatUserInfoVO;

/**
 * 微信用户信息service
 * @author hejinkai
 * @date 2018/6/27
 */
public interface WechatUserService {

    /**
     * 根据openId 查询用户信息
     * @param openId
     * @return
     */
    public WechatUserInfoVO getUserInfoByOpenId(String openId);

    /**
     * 根据unionId查询用户信息
     * @param unionId
     * @return
     */
    public WechatUserInfoVO getUserInfoByUnionId(String unionId);

    /**
     * 新增用户- 返回userId
     * @param userInfo
     * @return
     */
    public String addUserInfo(WechatUserInfoEntity userInfo);

    /**
     * 新增用户- 返回userId
     * @return
     */
    public String addUserInfo2();

    /**
     * 新增app与用户的关联
     * @param appInfoId
     * @param userInfoId
     * @param openId
     * @return
     */
    public String addUserAndAppMap(String appInfoId, String userInfoId, String openId);

    /**
     * 同步邀请人
     * @param appInfoId
     * @param userInfoId
     * @param openId
     * @param inviter
     * @return
     */
    public JSONObject updateInviter(String appInfoId, String userInfoId, String openId, String inviter);

    /**
     * 查询邀请人ID
     * @param appInfoId
     * @param userInfoId
     * @return
     */
    public String getInviter(String appInfoId, String userInfoId);

    /**
     * 统计邀请人数
     * @param appInfoId
     * @param userInfoId
     * @return
     */
    public Integer totalInviterNum(String appInfoId, String userInfoId);

    /**
     * 根据小程序和用户查询用户详情
     * @param appInfoId
     * @param userInfoId
     * @return
     */
    public WechatUserInfoVO getUserInfoByUserIdAndAppInfoId(String appInfoId, String userInfoId);

}
