package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.commodity.CommodityCatgService;
import data.driven.erm.business.commodity.CommodityService;
import data.driven.erm.business.order.OrderRebateService;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.business.wechat.WechatAppInfoService;
import data.driven.erm.common.Constant;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.commodity.CommodityVO;
import data.driven.erm.vo.order.OrderRebateVO;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hejinkai
 * @date 2018/10/1
 */
@Controller
@RequestMapping("/wechatapi/commodity")
public class WechatCommodityController {

    private static final Logger logger = LoggerFactory.getLogger(WechatCommodityController.class);

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityCatgService commodityCatgService;
    @Autowired
    private WechatAppInfoService wechatAppInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRebateService orderRebateService;

    @ResponseBody
    @RequestMapping("/findCommodityCatgList")
    public JSONObject findCommodityCatgList(String sessionID){
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        List<CommodityCatgEntity> catgList = commodityCatgService.findCommodityCatgList();
        result.put("catgList", JSONUtil.replaceNull(catgList));
        List<String> filePathList = wechatAppInfoService.findSowingMap(wechatUserInfoVO.getAppInfoId());
        if(filePathList != null && filePathList.size() > 0){
            List<String> changePathList = new ArrayList<String>();
            for (String filePath : filePathList){
                changePathList.add(Constant.STATIC_FILE_PATH + filePath);
            }
            result.put("filePathList", changePathList);
        }
        List<OrderRebateVO> rebateList = orderRebateService.findTopRebateList(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId());
        result.put("rebateList", JSONUtil.replaceNull(rebateList));
        return result;
    }

    @ResponseBody
    @RequestMapping("/findCommodityListByCatgId")
    public JSONObject findCommodityListByCatgId(String catgId){
        List<CommodityVO> commodityList = commodityService.findCommodityListByCatgId(catgId);
        if(commodityList != null && commodityList.size() > 0){
            for (CommodityVO commodityVO : commodityList){
                if(commodityVO.getFilePath() != null){
                    commodityVO.setFilePath(Constant.STATIC_FILE_PATH + commodityVO.getFilePath());
                }
            }
        }
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        result.put("commodityList", JSONUtil.replaceNull(commodityList));
        return result;
    }

    @ResponseBody
    @RequestMapping("/getCommodityById")
    public JSONObject getCommodityById(String sessionID, String commodityId){
        CommodityVO commodityVO = commodityService.getCommodityById(commodityId);
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        if(commodityVO != null){
            if(commodityVO.getCommodityImageTextList() != null && commodityVO.getCommodityImageTextList().size() > 0){
                List<String> changePathList = new ArrayList<String>();
                for (String filePath : commodityVO.getCommodityImageTextList()){
                    changePathList.add(Constant.STATIC_FILE_PATH + filePath);
                }
                commodityVO.setCommodityImageTextList(changePathList);
            }
            Integer salesVolume = orderService.totalSalesVolume(commodityVO.getCommodityId());
            result.put("salesVolume", salesVolume);
        }
        WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        result.put("share", orderService.haveOrder(wechatUserInfoVO.getAppInfoId(), wechatUserInfoVO.getWechatUserId()));
        result.put("commodityVO", JSONUtil.replaceNull(commodityVO));
        return result;
    }

}
