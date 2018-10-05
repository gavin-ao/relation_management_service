package data.driven.erm.business.user.impl;

import data.driven.erm.business.user.InvitationInfoService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hejinkai
 * @date 2018/10/2
 */
@Service
public class InvitationInfoServiceImpl implements InvitationInfoService{

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public String getInvitationIdByUserId(String wechatUserId) {
        String sql = "select invitation_id from sys_user_invitation_info where wechat_user_id = ? order by create_at desc,invitation_id limit 1";
        Object invitationIdObj = jdbcBaseDao.getColumn(sql, wechatUserId);
        if(invitationIdObj != null){
            return invitationIdObj.toString();
        }
        String invitationId = UUIDUtil.getUUID();
        sql = "insert into sys_user_invitation_info(invitation_id,wechat_user_id,create_at) values(?,?,?)";
        jdbcBaseDao.executeUpdate(sql, invitationId, wechatUserId, new Date());
        return invitationId;
    }

    @Override
    public String getUserId(String invitationId) {
        String sql = "select wechat_user_id from sys_user_invitation_info where invitation_id = ?";
        Object wechatUserId = jdbcBaseDao.getColumn(sql, invitationId);
        if(wechatUserId != null){
            return wechatUserId.toString();
        }
        return null;
    }
}
