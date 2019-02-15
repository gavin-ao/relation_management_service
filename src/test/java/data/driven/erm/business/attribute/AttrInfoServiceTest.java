package data.driven.erm.business.attribute;

import data.driven.erm.controller.wechatapi.UnitTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: lxl
 * @describe 属性表Sercice
 * @Date: 2019/2/15 14:47
 * @Version 1.0
 */
public class AttrInfoServiceTest extends UnitTestBase {

    @Autowired
    private AttrInfoService attrInfoService;

    /**
     * @description 测试获取属性码
     * @author lxl
     * @date 2019-02-15 14:49
     * @return
     */
    @Test
    public void insertCode(){
        String attrCode = attrInfoService.insertCode(1);
        System.out.println(attrCode);
    }
}
