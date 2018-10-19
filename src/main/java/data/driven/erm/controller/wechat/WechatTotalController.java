package data.driven.erm.controller.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hejinkai
 * @date 2018/7/4
 */
@Controller
@RequestMapping("/wechat/total")
public class WechatTotalController {

    private static final Logger logger = LoggerFactory.getLogger(WechatTotalController.class);

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
}
