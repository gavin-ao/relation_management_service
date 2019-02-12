package data.driven.erm.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: relation_management_service
 * @description: 退款Controller
 * @author: Logan
 * @create: 2019-02-12 14:58
 **/
@Controller
@RequestMapping(path = "/refund")
public class RefundController {

    @RequestMapping("/index")
    public ModelAndView refundPage(){
         ModelAndView mv = new ModelAndView("/refund/index");
         return  mv;
    }
}
