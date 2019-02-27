package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRebateService;
import data.driven.erm.business.user.InvitationInfoService;
import data.driven.erm.business.wechat.WechatUserService;
import data.driven.erm.common.WechatApiSessionBean;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author hejinkai - 可删除
 * @date 2018/6/29
 */
@Controller
@RequestMapping(path = "/wechatapi/user")
public class WechatUserApiController {
    private static final Logger logger = LoggerFactory.getLogger(WechatUserApiController.class);

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private OrderRebateService orderRebateService;
    @Autowired
    private InvitationInfoService invitationInfoService;

    /**
     * 获取登录用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getUserInfoId")
    public JSONObject getUserInfoId(String sessionID){
        WechatApiSessionBean wechatApiSessionBean = WechatApiSession.getSessionBean(sessionID);
        WechatUserInfoVO userInfo = wechatApiSessionBean.getUserInfo();
        userInfo.clearSensitiveInfo();
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        result.put("userInfo", userInfo);
        return result;
    }

    /**
     * 获取返利和邀请人数
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getInviterNumAndRebateMoney")
    public JSONObject getInviterNumAndRebateMoney(String sessionID){
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        WechatUserInfoVO userInfo = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        Integer inviterNum = wechatUserService.totalInviterNum(userInfo.getAppInfoId(), userInfo.getWechatUserId());
        result.put("inviterNum", inviterNum);
        BigDecimal rebateMoney = orderRebateService.getRebateMoney(userInfo.getAppInfoId(), userInfo.getWechatUserId());
        result.put("rebateMoney", rebateMoney);
        return result;
    }

    /**
     * 获取当前用户的邀请码
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getInvitation")
    public JSONObject getInvitation(String sessionID){
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        WechatUserInfoVO userInfo = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        String invitation = invitationInfoService.getInvitationIdByUserId(userInfo.getWechatUserId());
        result.put("invitation", invitation);
        return result;
    }
}
