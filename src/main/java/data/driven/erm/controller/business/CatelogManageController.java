package data.driven.erm.controller.business;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.commodity.CommodityCatgService;
import data.driven.erm.common.ApplicationSessionFactory;
import data.driven.erm.component.Page;
import data.driven.erm.component.PageBean;
import data.driven.erm.entity.user.UserInfoEntity;
import data.driven.erm.vo.commodity.CommodityCatgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: relation_management_service
 * @description: 类目管理Controller
 * @author: Logan
 * @create: 2019-02-15 16:31
 **/
@Controller
@RequestMapping("/catalog")
public class CatelogManageController {

    @Autowired
    private CommodityCatgService commodityCatgService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/catalogManage/index");
        return  mv;
    }

    @RequestMapping(value = {"/findCatalogPage/","/findCatalogPage/{parentCode}"})
    @ResponseBody
    public JSONObject getCatalogPage(HttpServletRequest request, HttpServletResponse response, @PathVariable (required = false) String parentCode,
                                     String keyword, Integer pageNo, Integer pageSize){
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

        Page<CommodityCatgVO> page =
                commodityCatgService.findfindCommodityCatgPage(parentCode, keyword, user.getUserId(), pageBean);
        result.put("success", true);
        result.put("page", page);
        return result;
    }
}
