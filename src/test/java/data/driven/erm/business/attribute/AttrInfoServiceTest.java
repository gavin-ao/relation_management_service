package data.driven.erm.business.attribute;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.controller.wechatapi.UnitTestBase;
import data.driven.erm.entity.attribute.AttrValueEntity;
import data.driven.erm.vo.attribute.AttrInfoVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
//    @Test
//    public void insertCode(){
//        String attrCode = attrInfoService.insertCode(1);
//        System.out.println(attrCode);
//    }

    /**
     * @description 获取属性名称和属性值
     * @author lxl
     * @date 2019-02-15 15:27
     * @return
     */
//    @Test
//    public void getAttrInfoAndValue(){
//        AttrInfoVO attrInfoVO = attrInfoService.getAttrInfoAndValue(0);
//        System.out.println("属性表ID："+attrInfoVO.getAttrId());
//        System.out.println("属性名称："+attrInfoVO.getAttrName());
//        System.out.println("属性类型："+attrInfoVO.getAttrType());
//        System.out.println("属性码："+attrInfoVO.getAttrCode());
//        List<AttrValueEntity> attrValueEntityList = attrInfoVO.getAttrValueEntityList();
//        for (AttrValueEntity attrValueEntity : attrValueEntityList){
//            System.out.println("属性值表ID：" + attrValueEntity.getAttrValueId());
//            System.out.println("属性值：" + attrValueEntity.getAttrValue());
//        }
//    }

    /**
     * @description 新增属性同时新增属性值
     * @author lxl
     * @date 2019-02-15 16:04
     * @return
     */
//    @Test
//    public void insertAtrrInfoAndValue(){
//        //增加描述属性及属性值 start
////        List<String> attrValueList = new ArrayList<>();
////        attrValueList.add("6个月");
////        attrValueList.add("12个月");
////        attrValueList.add("18个月");
////        JSONObject resultJson = attrInfoService.insertAtrrInfoAndValue("保质期",attrValueList,0);
//        //增加描述属性及属性值 end
//
//        //增加规格属性及属性值 start
//        List<String> attrValueList = new ArrayList<>();
//        attrValueList.add("红色");
//        attrValueList.add("黑色");
//        attrValueList.add("蓝色");
//        JSONObject resultJson = attrInfoService.insertAtrrInfoAndValue("颜色",attrValueList,1);
//
//
//        System.out.println(resultJson.getString("msg"));
//    }

    /**
     * @description 修改属性名称
     * @author lxl
     * @date 2019-02-15 16:24
     * @return
     */
    @Test
    public void updateAttrName(){
        JSONObject resultJson = attrInfoService.updateAttrName("5c66744c7053bb1fd80bffc9","产地");
        System.out.println(resultJson.getString("msg"));
    }
}
