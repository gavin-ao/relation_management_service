package data.driven.erm.business.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.SysPictureService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.wechat.SysPictureEntity;
import data.driven.erm.util.FileUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @Author: lxl
 * @describe 图片文件信息Impl
 * @Date: 2019/2/13 10:26
 * @Version 1.0
 */
@Service
public class SysPictureServiceImpl implements SysPictureService{
    private final static Logger logger = LoggerFactory.getLogger(SysPictureServiceImpl.class);

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    private Base64.Decoder decoder = Base64.getDecoder();

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
    @Override
    public String insertPictures(WechatUserInfoVO wechatUserInfoVO, Date date, List<SysPictureEntity> sysPictureList,
                                 String pictureJson) {
        String fileName = UUIDUtil.getUUID();
        SysPictureEntity sysPictureEntity = new SysPictureEntity(fileName,fileName,wechatUserInfoVO.getWechatUserId(),
                date);
        try {
            fileName = sysPictureEntity.getPictureId() + ".jpg";
            String[] pictureJsonArr = pictureJson.split(",");
            JSONObject uploadResult = FileUtil.uploadFile(decoder.decode(pictureJsonArr[pictureJsonArr.length - 1]),fileName);
            sysPictureEntity.setFilePath(uploadResult.getString("relativePath"));
            sysPictureList.add(sysPictureEntity);
        }catch (Exception e){
            sysPictureList.add(sysPictureEntity);
            logger.error(e.getMessage(),e);
            return "-1";
        }
        return sysPictureEntity.getPictureId();
    }
}





















