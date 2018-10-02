package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderReceiveAddrService;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.entity.order.OrderReceiveAddrEntity;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static data.driven.erm.util.JSONUtil.putMsg;
import static data.driven.erm.util.JSONUtil.replaceNull;

/**
 * @author hejinkai
 * @date 2018/10/2
 */
@Controller
@RequestMapping(path = "/wechatapi/order/addr")
public class WechatOrderReceiveAddrController {

    private static final Logger logger = LoggerFactory.getLogger(WechatOrderReceiveAddrController.class);

    @Autowired
    private OrderReceiveAddrService orderReceiveAddrService;

    @ResponseBody
    @RequestMapping(path = "/findAddrList")
    public JSONObject findAddrList(String sessionID){
        WechatUserInfoVO userInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        List<OrderReceiveAddrEntity> list =  orderReceiveAddrService.findAddrList(userInfoVO.getWechatUserId());
        JSONObject result = putMsg(true, "200", "调用成功");
        result.put("data", replaceNull(list));
        return result;
    }

    @ResponseBody
    @RequestMapping(path = "/getAddr")
    public JSONObject getAddr(String addrId){
        if(addrId == null || addrId.trim().length() < 1){
            return putMsg(false, "101", "参数为空");
        }
        OrderReceiveAddrEntity addrEntity =  orderReceiveAddrService.getAddr(addrId);
        JSONObject result = putMsg(true, "200", "调用成功");
        result.put("data", replaceNull(addrEntity));
        return result;
    }

    @ResponseBody
    @RequestMapping(path = "/updateAddr")
    public JSONObject updateAddr(String sessionID, OrderReceiveAddrEntity addr){
        if(addr == null){
            return putMsg(false, "101", "参数为空");
        }
        WechatUserInfoVO userInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        addr.setWechatUserId(userInfoVO.getWechatUserId());
        orderReceiveAddrService.updateAddr(addr);
        JSONObject result = putMsg(true, "200", "调用成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(path = "/deleteAddr")
    public JSONObject deleteAddr(String sessionID, String ids){
        if(ids == null || ids.trim().length() < 1){
            return putMsg(false, "101", "参数为空");
        }
        WechatUserInfoVO userInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
        orderReceiveAddrService.deleteAddr(ids, userInfoVO.getWechatUserId());
        JSONObject result = putMsg(true, "200", "调用成功");
        return result;
    }

}
