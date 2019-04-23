package data.driven.erm.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatTotalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hejinkai
 * @date 2018/7/4
 */
@Controller
@RequestMapping("/wechat/total")
public class WechatTotalController {

    private static final Logger logger = LoggerFactory.getLogger(WechatTotalController.class);

    @Autowired
    private WechatTotalService wechatTotalService;

    @RequestMapping(value = "/dataStatistics")
    public ModelAndView productManage(){
        ModelAndView mv = new ModelAndView("/data-statistics/index");
        return mv;
    }

    @RequestMapping(path = "/bossKanban")
    public ModelAndView bossKanban(String appInfoId){
        ModelAndView mv = new ModelAndView("/data-statistics/bossKanban");
        return mv;
    }

    @RequestMapping(path = "/salesPer")
    public ModelAndView salesPer(String appInfoId){
        ModelAndView mv = new ModelAndView("/data-statistics/salesPer");
        return mv;
    }

    @RequestMapping(path = "/smallProgram")
    public ModelAndView smallProgram(String appInfoId){
        ModelAndView mv = new ModelAndView("/data-statistics/smallProgram");
        return mv;
    }

    /**
     * 统计上面五个指标
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(path = "/coreData")
    public ModelAndView coreData(String startDate, String endDate){
        ModelAndView mv = new ModelAndView("/data-statistics/smallProgram-coreData");
        dealTotalAll("activityNum", mv, wechatTotalService.totalActivityNum(startDate, endDate));
        dealTotalAll("spreadRangeNum", mv, wechatTotalService.totalSpreadRangeNum(startDate, endDate));
        dealTotalAll("fissionEffectNewPeopleNum", mv, wechatTotalService.totalFissionEffectNewPeopleNum(startDate, endDate));
        dealTotalAll("sharePeopleNum", mv, wechatTotalService.totalSharePeopleNum(startDate, endDate));
        return mv;
    }

    /**
     * 处理json - totalAll
     * @param key
     * @param mv
     * @param temp
     */
    private void dealTotalAll(String key,  ModelAndView mv, JSONObject temp){
        if(temp.getBoolean("success")){
            mv.addObject(key, temp.getInteger("countNum"));
        }else{
            mv.addObject(key, 0);
        }
    }

    /**
     * 根据时间范围统计活跃度，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalActivityNumView")
    public JSONObject totalActivityNumView(String startDate, String endDate){
        return wechatTotalService.totalActivityNumView(startDate, endDate);
    }
    /**
     * 根据时间范围统计传播范围，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalSpreadRangeNumView")
    public JSONObject totalSpreadRangeNumView(String startDate, String endDate){
        return wechatTotalService.totalSpreadRangeNumView(startDate, endDate);
    }

    /**
     * 根据时间范围统计裂变效果新增人数，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalFissionEffectNewPeopleNumView")
    public JSONObject totalFissionEffectNewPeopleNumView(String startDate, String endDate){
        return wechatTotalService.totalFissionEffectNewPeopleNumView(startDate, endDate);
    }

    /**
     * 根据时间范围统计分享人数，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalSharePeopleNumView")
    public JSONObject totalSharePeopleNumView(String startDate, String endDate){
        return wechatTotalService.totalSharePeopleNumView(startDate, endDate);
    }

    /**
     * 根据时间范围统计新老用户占比
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalOldAndNewUser")
    public JSONObject totalOldAndNewUser(String startDate, String endDate){
        return wechatTotalService.totalOldAndNewUser(startDate, endDate);
    }

    /**
     * 用户转化漏斗图
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalFunnelView")
    public JSONObject totalFunnelView(String startDate, String endDate){
        return wechatTotalService.totalFunnelView(startDate, endDate);
    }

    /**
     * 用户邀请排行
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalInviteRankView")
    public JSONObject totalInviteRankView(String startDate, String endDate){
        return wechatTotalService.totalInviteRankView(startDate, endDate);
    }

    /**
     * 留存率
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalUserRetainView")
    public JSONObject totalUserRetainView(String startDate, String endDate){
        return wechatTotalService.totalUserRetainView(startDate, endDate);
    }

    /*********************************************老板页面********************************************************/
    /**
     * 统计上面四个指标
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(path = "/bossCoreData")
    public ModelAndView bossCoreData(String startDate, String endDate){
        ModelAndView mv = new ModelAndView("/data-statistics/smallProgram-coreData");
        //活跃度
        dealTotalAll("activityNum", mv, wechatTotalService.totalActivityNum(startDate, endDate));
        //新增人数
        dealTotalAll("fissionEffectNewPeopleNum", mv, wechatTotalService.totalFissionEffectNewPeopleNum(startDate, endDate));
        //订单量
        dealTotalAll("orderNum", mv, wechatTotalService.totalBuyNum(startDate, endDate));
        //成交额
        dealTotalAll("turnover", mv, wechatTotalService.totalTurnover(startDate, endDate));
        return mv;
    }

    /**
     * 平均客单价
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalAverageUnitPrice")
    public JSONObject totalAverageUnitPrice(String startDate, String endDate){
        return wechatTotalService.totalAverageUnitPrice(startDate, endDate);
    }

    /**
     * 统计畅销分类
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalSalableCatg")
    public JSONObject totalSalableCatg(String startDate, String endDate){
        return wechatTotalService.totalSalableCatg(startDate, endDate);
    }

    /**
     * 地区分布
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalAreaDistribution")
    public JSONObject totalAreaDistribution(String startDate, String endDate){
        return wechatTotalService.totalAreaDistribution(startDate, endDate);
    }

    /**
     * 返利排行 - top10
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/totalRebateBanking")
    public JSONObject totalRebateBanking(String startDate, String endDate){
        return wechatTotalService.totalRebateBanking(startDate, endDate);
    }




}
