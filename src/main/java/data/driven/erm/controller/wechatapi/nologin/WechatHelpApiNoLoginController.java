package data.driven.erm.controller.wechatapi.nologin;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatAppInfoService;
import data.driven.erm.business.wechat.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * 助力
 * @author hejinkai
 * @date 2018/7/13
 */
@Controller
@RequestMapping(path = "/wechatapi/nologin/help")
public class WechatHelpApiNoLoginController {
    private static final Logger logger = LoggerFactory.getLogger(WechatHelpApiNoLoginController.class);

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatAppInfoService wechatAppInfoService;

    /**
     * 发送消息到前端页面
     * @param response
     * @param msg
     */
    private void printMsg(HttpServletResponse response, String msg){
        try{
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(msg);
        }catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 测试
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/addUserInfo2")
    public JSONObject addUserInfo2(){
        try{
            return putMsg(true, "200", wechatUserService.addUserInfo2());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return putMsg(false, "102", "助力用户查询失败");
        }
    }

}
