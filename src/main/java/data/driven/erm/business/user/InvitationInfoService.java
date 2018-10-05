package data.driven.erm.business.user;

/**
 * 邀请码信息
 * @author hejinkai
 * @date 2018/10/2
 */
public interface InvitationInfoService {

    /**
     * 根据用户获取邀请码
     * @param wechatUserId
     * @return
     */
    public String getInvitationIdByUserId(String wechatUserId);

    /**
     * 根据邀请码获取用户信息
     * @param invitationId
     * @return
     */
    public String getUserId(String invitationId);

}
