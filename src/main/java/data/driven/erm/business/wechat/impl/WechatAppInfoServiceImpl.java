package data.driven.erm.business.wechat.impl;

import data.driven.erm.business.wechat.WechatAppInfoService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.wechat.WechatAppInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hejinkai
 * @date 2018/6/28
 */
@Service
public class WechatAppInfoServiceImpl implements WechatAppInfoService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public WechatAppInfoEntity getAppInfo(String appid) {
        String sql = "select app_info_id,app_name,appid,secret,create_at,creator from wechat_app_info where appid = ?";
        List<WechatAppInfoEntity> list = jdbcBaseDao.queryList(WechatAppInfoEntity.class, sql, appid);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<WechatAppInfoEntity> findAppInfoListByUser(String userInfoId) {
        String sql = "select wai.app_info_id,wai.app_name,wai.appid,wai.secret,wai.create_at,wai.creator from wechat_app_info wai" +
                " left join sys_user_wechat_app_mapping suwam on suwam.app_info_id = wai.app_info_id where suwam.user_id = ?";
        List<WechatAppInfoEntity> list = jdbcBaseDao.queryList(WechatAppInfoEntity.class, sql, userInfoId);
        return list;
    }

    @Override
    public List<String> findSowingMap(String appInfoId) {
        String sql = "select p.file_path from wechat_app_sowing_map sm" +
                " left join sys_picture p on p.picture_id = sm.picture_id where sm.app_info_id = ? order by sm.ord";
        List<String> filePathList = jdbcBaseDao.getColumns(String.class, sql, appInfoId);
        return filePathList;
    }

    /**
     * @description 获取实体
     * @author lxl
     * @date 2019-01-24 17:23
     * @param appInfoId 小程序信息表Id
     * @return
     */
    @Override
    public WechatAppInfoEntity getAppInfoEntity(String appInfoId) {
        String sql = "select app_info_id,app_name,appid,secret,create_at,creator from wechat_app_info " +
                "where app_info_id = ?";
        List<WechatAppInfoEntity> wechatAppInfoEntities = jdbcBaseDao.queryList(WechatAppInfoEntity.class,sql,appInfoId);
        if (wechatAppInfoEntities != null && wechatAppInfoEntities.size() > 0){
            return wechatAppInfoEntities.get(0);
        }
        return null;
    }
}
