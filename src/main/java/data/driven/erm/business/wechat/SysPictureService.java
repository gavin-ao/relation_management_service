package data.driven.erm.business.wechat;

import data.driven.erm.entity.wechat.SysPictureEntity;
import data.driven.erm.vo.wechat.WechatUserInfoVO;

import java.util.Date;
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

    /**
     * @description 上传图片
     * @author lxl
     * @date 2019-02-13 10:37
     * @param wechatUserInfoVO 微信用户信息
     * @param date 时间
     * @param sysPictureList 图片对像数组
     * @param pictureJson 图片Json
     * @return
     */
    String insertPictures(WechatUserInfoVO wechatUserInfoVO, Date date,List<SysPictureEntity> sysPictureList,
                          String pictureJson);
}
