package data.driven.erm.business.shop.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.attribute.impl.AttrBrandServiceImpl;
import data.driven.erm.business.commodity.CommodityService;
import data.driven.erm.business.shop.ShopService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.entity.commodity.CommodityEntity;
import data.driven.erm.entity.wechat.SysPictureEntity;
import data.driven.erm.util.FileUtil;
import data.driven.erm.util.UUIDUtil;
import data.driven.erm.vo.commodity.CommodityVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * @Author: lxl
 * @describe
 * @Date: 2019/2/26 17:20
 * @Version 1.0
 */
@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LoggerFactory.getLogger(AttrBrandServiceImpl.class);
    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Autowired
    private CommodityService commodityService;

    /**
     * @description 通过级别得到最大的排序信息
     * @author lxl
     * @date 2019-02-26 17:22
     * @param level 级别
     * @return
     */
    @Override
    public Integer getCommodityCatgOrd(Integer level) {
        String sql = "select max(ord) as ord from commodity_catg_info where catg_level=?";
        Object ord = jdbcBaseDao.getColumn(sql, level);
        if (ord != null){
            return Integer.parseInt(ord.toString());
        }
        return null;
    }

    /**
     * @description 保存与修改类目信息
     * @author lxl
     * @date 2019-02-26 17:35
     * @param catgId 主键id
     * @param catgName 分类名称
     * @param catgCode 分类层级码
     * @param catgLevel 层级
     * @param saveType 保存类型 insert 新增 修改 update
     * @return
     */
    @Override
    public JSONObject updateCommodityCatgOrd(String catgId, String catgName, String catgCode, Integer catgLevel, String saveType) {
        try{
            if ("insert".equals(saveType)){
                logger.info("进入新增类目");
                CommodityCatgEntity commodityCatgEntitye = new CommodityCatgEntity();
                commodityCatgEntitye.setCatgId(catgId);
                commodityCatgEntitye.setCatgName(catgName);
                commodityCatgEntitye.setCatgCode(catgCode);
                commodityCatgEntitye.setCatgLevel(catgLevel);
                Integer ord = getCommodityCatgOrd(catgLevel);
                commodityCatgEntitye.setOrd(ord);
                Date createAt = new Date();
                commodityCatgEntitye.setCreateAt(createAt);
                commodityCatgEntitye.setCreator("system");
                jdbcBaseDao.insert(commodityCatgEntitye, "commodity_catg_info");
                logger.info("新增类目成功");
                return putMsg(true,"200","保存成功");
            }else if("update".equals(saveType)){
                logger.info("进入修改类目");
                String sql = "update commodity_catg_info set catg_name = ? where catg_id = ?";
                jdbcBaseDao.executeUpdate(sql, catgName,catgId);
                logger.info("新增类目成功");
                return putMsg(true,"200","保存成功");
            }
            return putMsg(false, "103", "保存失败");
        }catch (Exception e){
            return putMsg(false, "103", "保存失败");
        }
    }

    /**
     * @description 保存商品信息
     * @author lxl
     * @date 2019-02-26 18:13
     * @param commodityId 商品id
     * @param catgId 类目id
     * @param commodityName 商品名称
     * @param suggestPrices 建议价格
     * @param prices 零售价格
     * @param saveType 保存类型 insert 新增 update 修改
     * @param url 图片地址
     * @param isMarkeTable 上架状态 0 未上架 1 上架
     * @return
     */
    @Override
    public JSONObject saveCommodityInfo(String url, String commodityId, String catgId,String catg_code,
                                        String commodityName, BigDecimal suggestPrices, BigDecimal prices,
                                        String saveType,Integer isMarkeTable) {
        try{
            if ("insert".equals(saveType)){
                logger.info("新增商品");
                CommodityEntity commodityEntity = new CommodityEntity();
                commodityEntity.setCommodityId(commodityId);
                commodityEntity.setCatgId(catgId);
                commodityEntity.setCommodityName(commodityName);
                commodityEntity.setSuggestPrices(suggestPrices);
                commodityEntity.setPrices(prices);
                commodityEntity.setOrd(1);
                commodityEntity.setCatgCode(catg_code);
                commodityEntity.setCreateAt(new Date());
                commodityEntity.setCreator("system");
                commodityEntity.setIsMarkeTable(isMarkeTable);
                commodityEntity.setPictureId(insertPictures(url));
                jdbcBaseDao.insert(commodityEntity, "commodity_info");
                logger.info("新增商品成功");
                return putMsg(true,"200","保存成功");
            }else if("update".equals(saveType)){
                logger.info("修改商品");
                CommodityVO commodityVO = commodityService.getCommodityById(commodityId);
                commodityVO.getPictureId();
                //删除图片数据库信息
                String sqldele = "DELETE FROM sys_picture WHERE picture_id = ?";
                jdbcBaseDao.executeUpdate(sqldele,commodityVO.getPictureId());
                String pictureId = insertPictures(url);
                String sql = "update commodity_info set catg_id = ?,catg_code=?,commodity_name=?,suggest_prices=?," +
                        "prices=?,picture_id=? ,is_Marke_Table = ? where commodity_id = ?";
                jdbcBaseDao.executeUpdate(sql, catgId,catg_code,commodityName,suggestPrices,prices,pictureId,
                        commodityId,isMarkeTable);
                logger.info("修改商品成功");
                return putMsg(true,"200","保存成功");
            }
            return putMsg(false, "103", "保存失败");
        }catch (Exception e){
            return putMsg(false, "103", "保存失败");
        }
    }




    /**
     * @description 上传图片
     * @author lxl
     * @date 2019-02-26 19:17
     * @return
     */
    private String insertPictures(String url) {
            String fileName = UUIDUtil.getUUID();
            SysPictureEntity sysPictureEntity = new SysPictureEntity(fileName,fileName,"system",
                    new Date());
            JSONObject uploadResult = upload(url,fileName);
            sysPictureEntity.setFilePath(uploadResult.getString("relativePath"));
            jdbcBaseDao.insert(sysPictureEntity,"sys_picture");
            return fileName;
    }

    private JSONObject upload(String urlPath,String fileName) {
        URL url = null;
        try {
            url = new URL(urlPath);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);

            JSONObject uploadResult = FileUtil.uploadFile(data, fileName+".jpg");
            return uploadResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
