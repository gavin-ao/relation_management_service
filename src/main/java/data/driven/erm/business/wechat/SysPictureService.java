package data.driven.erm.business.wechat;

import data.driven.erm.entity.wechat.SysPictureEntity;

import java.util.List;

/**
 * @Author: lxl
 * @describe 图片文件信息Service
 * @Date: 2019/2/13 10:24
 * @Version 1.0
 */
public interface SysPictureService {

    /**
     * @description 批量插入图片数据
     * @author lxl
     * @date 2019-02-13 10:25
     * @param list 图片数组
     * @return
     */
    void insertManyPicture(List<SysPictureEntity> list);
}
