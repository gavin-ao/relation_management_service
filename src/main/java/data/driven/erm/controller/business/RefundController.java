package data.driven.erm.controller.business;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.order.OrderRefundDetailInfoService;
import data.driven.erm.business.order.OrderService;
import data.driven.erm.common.ApplicationSessionFactory;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.entity.order.OrderRefundDetailInfoEntity;
import data.driven.erm.entity.user.UserInfoEntity;
import data.driven.erm.vo.order.OrderRefundDetailInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private static Logger logger = LoggerFactory.getLogger(RefundController.class);
    @Autowired
    private OrderRefundDetailInfoService refundService;
    @Autowired
    private OrderService orderService;

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

    @RequestMapping(path="/detail/{storeId}/{outRefundNo}")
    public ModelAndView getRefundDetail(HttpServletRequest request, @PathVariable String storeId, @PathVariable String outRefundNo){
        ModelAndView mv = new ModelAndView("/refund/refundDetail");
        OrderRefundDetailInfoVO orderRefundDetailInfo = refundService.getRefundDetailInfo(storeId,outRefundNo);
        mv.addObject("data",orderRefundDetailInfo);
        List<String> picUrls = refundService.getPicUrls(request,orderRefundDetailInfo.getOrderRefundDetailInfoId());
        mv.addObject("picUrls",picUrls);
        return mv;
    }


    @RequestMapping(path="/agreeRefund/{agree}/{storeId}/{outRefundNo}")
    @ResponseBody
    public JSONObject agreeRefund(@PathVariable Boolean agree, @PathVariable String storeId, @PathVariable String outRefundNo){
        JSONObject result = new JSONObject();
        refundService.agreeRefund(agree,storeId,outRefundNo);
        if(agree){
            OrderRefundDetailInfoEntity orderRefundDetailInfo = refundService.getRefundDetailInfo(storeId,outRefundNo);
            JSONObject refundJson = orderService.orderRefund(orderRefundDetailInfo.getAppid(),storeId,"",orderRefundDetailInfo.getOutTradeNo(),
                    outRefundNo,orderRefundDetailInfo.getTotalFee(),orderRefundDetailInfo.getRefundFee());
            logger.info("调用申请退款接口返回的信息： "+refundJson.toString());
            if (refundJson.getBoolean("success")){
                //修改订单状态3为退款成功
                logger.info("进入修改订单状态中");
                orderService.updateOrderState(orderRefundDetailInfo.getOutTradeNo(), 3);
            }
            return refundJson;
        }
        result.put("success",true);
        return result;
    }


}
