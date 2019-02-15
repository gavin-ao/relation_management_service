package data.driven.erm.controller.attribute;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: lxl
 * @describe 属性管理Controller
 * @Date: 2019/2/15 17:40
 * @Version 1.0
 */
@Controller
@RequestMapping("/attribute")
public class AttributeController {
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/attribute/index");
        return  mv;
    }
}
