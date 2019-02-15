package data.driven.erm.business.attribute;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.controller.wechatapi.UnitTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: lxl
 * @describe 属性值测试类
 * @Date: 2019/2/15 16:40
 * @Version 1.0
 */
public class AttrValueServiceTest extends UnitTestBase{

    @Autowired
    private AttrValueService attrValueService;

    @Test
    public void updateAttrValue(){
        JSONObject resultJson = attrValueService.updateAttrValue("5c6675627053bb2d309cf258","粉色");
        System.out.println(resultJson.getString("msg"));
    }
}
