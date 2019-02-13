package data.driven.erm.business.wechat.impl;

import data.driven.erm.business.wechat.SysPictureService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.wechat.SysPictureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lxl
 * @describe 图片文件信息Impl
 * @Date: 2019/2/13 10:26
 * @Version 1.0
 */
@Service
public class SysPictureServiceImpl implements SysPictureService{

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    /**
     * @description 批量插入图片数据
     * @author lxl
     * @date 2019-02-13 10:25
     * @param list 图片数组
     * @return
     */
    @Override
    public void insertManyPicture(List<SysPictureEntity> list) {
        String sql = "insert into sys_picture(picture_id,file_path,real_name,wechat_user,create_at)";
        String valueSql = "(:picture_id,:file_path,:real_name,:wechat_user,:create_at)";
        jdbcBaseDao.executeBachOneSql(sql, valueSql, list);
    }
}
