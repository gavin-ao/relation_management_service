package data.driven.erm.controller.business;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRefundDetailInfoService;
import data.driven.erm.common.ApplicationSessionFactory;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.entity.user.UserInfoEntity;
import data.driven.erm.vo.order.OrderRefundDetailInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: relation_management_service
 * @description: 退款Controller
 * @author: Logan
 * @create: 2019-02-12 14:58
 **/
@Controller
@RequestMapping(path = "/refund")
public class RefundController {
    @Autowired
    private OrderRefundDetailInfoService refundService;

    @RequestMapping("/index")
    public ModelAndView refundPage(){
         ModelAndView mv = new ModelAndView("/refund/index");
         return  mv;
    }

    @RequestMapping("/findRefundPage")
    @ResponseBody
    public JSONObject getRefundPage(HttpServletRequest request, HttpServletResponse response, String keyword, Integer pageNo, Integer pageSize){

        UserInfoEntity user = ApplicationSessionFactory.getUser(request, response);
        JSONObject result = new JSONObject();
        PageBean pageBean = new PageBean();
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        pageBean.setPageNo(pageNo);
        pageBean.setPageSize(pageSize);

        Page<OrderRefundDetailInfoVO> page = refundService.findRefundPage(keyword, user.getUserId(), pageBean);
        result.put("success", true);
        result.put("page", page);
        return result;
    }
}
