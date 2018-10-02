package data.driven.erm.controller.wechatapi;

import data.driven.erm.business.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hejinkai
 * @date 2018/10/3
 */
@Controller
@RequestMapping("/wechatapi/order")
public class WechatOrderController {

    private static final Logger logger = LoggerFactory.getLogger(WechatOrderController.class);

    @Autowired
    private OrderService orderService;













}
