package data.driven.erm.business.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatUserService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.wechat.WechatUserInfoEntity;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @author hejinkai
 * @date 2018/6/28
 */
@Service
public class WechatUserServiceImpl implements WechatUserService {

    private static final Map<String, String> nameMap = new HashMap<String, String>();
    static {
        nameMap.put("婧", "summer");
        nameMap.put("何晋凯", "风云天下");
        nameMap.put("天上白玉京", "天上");
        nameMap.put("敖永刚", "天下");
        nameMap.put("饮尽岁月", "苍桑岁月");
    }

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public WechatUserInfoVO getUserInfoByOpenId(String openId) {
        String sql = "select u.wechat_user_id,u.nick_name,u.gender,u.language,u.city,u.province,u.country,u.avatar_url,u.union_id,u.create_at,aum.open_id,aum.app_info_id,aum.wechat_map_id,aum.inviter from wechat_user_info u" +
                " left join wechat_app_user_mapping aum on aum.wechat_user_id = u.wechat_user_id" +
                " where aum.open_id = ?";
        List<WechatUserInfoVO> list = jdbcBaseDao.queryList(WechatUserInfoVO.class, sql, openId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public WechatUserInfoVO getUserInfoByUnionId(String unionId) {
        String sql = "select u.wechat_user_id,u.nick_name,u.gender,u.language,u.city,u.province,u.country,u.avatar_url,u.union_id,u.create_at from wechat_user_info u" +
                " where u.union_id = ?";
        List<WechatUserInfoVO> list = jdbcBaseDao.queryList(WechatUserInfoVO.class, sql, unionId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String addUserInfo(WechatUserInfoEntity userInfo) {
        String userInfoId = UUIDUtil.getUUID();
        WechatUserInfoEntity newUserInfo = new WechatUserInfoEntity();
        BeanUtils.copyProperties(userInfo, newUserInfo);
        newUserInfo.setWechatUserId(userInfoId);
        newUserInfo.setCreateAt(new Date());
        if(nameMap.containsKey(newUserInfo.getNickName())){
            newUserInfo.setNickName(nameMap.get(newUserInfo.getNickName()));
        }
        jdbcBaseDao.insert(newUserInfo, "wechat_user_info");
        return userInfoId;
    }

    @Override
    public String addUserInfo2() {
        String sql = "update wechat_user_info set nick_name='哇哈哈吃啊\uD83E\uDD84' where wechat_user_id='5b646deaf6d608065c8c9e10'";
        jdbcBaseDao.executeUpdate(sql);
        return sql;
    }

    @Override
    public String addUserAndAppMap(String appInfoId, String userInfoId, String openId) {
        String id = UUIDUtil.getUUID();
        String sql = "insert into wechat_app_user_mapping(wechat_map_id,app_info_id,wechat_user_id,open_id,create_at) values(?,?,?,?,?)";
        jdbcBaseDao.executeUpdate(sql, id,appInfoId, userInfoId, openId, new Date());
        return id;
    }

    @Override
    public JSONObject updateInviter(String appInfoId, String userInfoId, String openId, String inviter) {
        String sql = "select wechat_map_id,inviter from wechat_app_user_mapping where app_info_id = ? and wechat_user_id = ? and open_id = ? limit 1";
        List<Map<String, Object>> list = jdbcBaseDao.queryMapList(sql, appInfoId, userInfoId, openId);
        if(list != null && list.size() > 0){
            Map<String, Object> map = list.get(0);
            Object inviterObj = map.get("inviter");
            if(inviterObj == null || inviterObj.toString().length() < 1){
                Object wechatMapId = map.get("wechat_map_id");
                sql = "update wechat_app_user_mapping set inviter = ? where wechat_map_id = ?";
                jdbcBaseDao.executeUpdate(sql, inviter, wechatMapId);
                return putMsg(true, "200", "调用成功");
            }else{
                return putMsg(false, "102", "已存在邀请人");
            }
        }else{
            return putMsg(false, "101", "请先登录");
        }
    }

    @Override
    public String getInviter(String appInfoId, String userInfoId) {
        String sql = "select wechat_map_id,inviter from wechat_app_user_mapping where app_info_id = ? and wechat_user_id = ? limit 1";
        List<Map<String, Object>> list = jdbcBaseDao.queryMapList(sql, appInfoId, userInfoId);
        if(list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            Object inviterObj = map.get("inviter");
            if (inviterObj != null && inviterObj.toString().length() > 0) {
                return inviterObj.toString();
            }
        }
        return null;
    }

    @Override
    public Integer totalInviterNum(String appInfoId, String userInfoId) {
        String sql = "select count(wechat_map_id) from wechat_app_user_mapping where app_info_id = ? and inviter = ?";
        return jdbcBaseDao.getCount(sql, appInfoId, userInfoId);
    }

    @Override
    public WechatUserInfoVO getUserInfoByUserIdAndAppInfoId(String appInfoId, String userInfoId) {
        String sql = "select u.wechat_user_id,u.nick_name,u.gender,u.language,u.city,u.province,u.country,u.avatar_url,u.union_id,u.create_at,aum.open_id,aum.app_info_id,aum.wechat_map_id,aum.inviter from wechat_user_info u" +
                " left join wechat_app_user_mapping aum on aum.wechat_user_id = u.wechat_user_id" +
                " where u.wechat_user_id = ? and aum.app_info_id = ?";
        List<WechatUserInfoVO> list = jdbcBaseDao.queryList(WechatUserInfoVO.class, sql, userInfoId, appInfoId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
