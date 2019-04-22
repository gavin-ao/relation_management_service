package data.driven.erm.controller.wechatapi;

import data.driven.erm.business.wechat.WechatBehaviorLogService;
import data.driven.erm.common.WechatApiSession;
import data.driven.erm.entity.wechat.WechatBehaviorLogEntity;
import data.driven.erm.vo.wechat.WechatUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hejinkai
 * @date 2019/01/24
 */
@Controller
@RequestMapping(path = "/wechatapi/log")
public class WechatBehaviorLogController {

    private static final Logger logger = LoggerFactory.getLogger(WechatBehaviorLogController.class);

    @Autowired
    private WechatBehaviorLogService wechatBehaviorLogService;

    /**
     * 记录日志
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/addLog")
    public void addLog(String sessionID, String funcCode, String travelsId, WechatBehaviorLogEntity wechatBehaviorLogEntity){
        if(wechatBehaviorLogEntity != null){
            WechatUserInfoVO wechatUserInfoVO = WechatApiSession.getSessionBean(sessionID).getUserInfo();
            wechatBehaviorLogEntity.initFiled(wechatUserInfoVO.getWechatUserId(), wechatUserInfoVO.getAppInfoId());
            //记录日志
            wechatBehaviorLogService.insertLog(wechatBehaviorLogEntity);
        }
    }

}
