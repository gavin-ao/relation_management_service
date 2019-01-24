package data.driven.erm.controller.wechatapi;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.vo.pay.PayPrepayVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
* 单元测试
* @author Logan
* @date 2019-01-24 11:12

* @return
*/
public class WechatOrderControllerTest extends UnitTestBase {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        //单个类,项目拦截器无效
//      mvc = MockMvcBuilders.standaloneSetup(new ProductController()).build();
        //项目拦截器有效
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void getPrepayInfo() {
        PayPrepayVO prepayVO = new PayPrepayVO();
        prepayVO.setAppId("wx0e8660984c4eb63b");
        prepayVO.setOutTradeNo("20181agv2184946");
        prepayVO.setStoreId("1");
        String param = JSONObject.toJSONString(prepayVO);
        RequestBuilder request = MockMvcRequestBuilders.get("/wechatapi/order/prepay")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(param);

        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(request).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int status = mvcResult.getResponse().getStatus();
        String content = null;
        try {
            content = mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("正确", status == 200);
        Assert.assertFalse("错误", status != 200);
        System.out.println("返回结果："+status);
        System.out.println(content);
    }
}