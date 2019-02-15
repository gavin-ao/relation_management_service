package data.driven.erm.business.attribute;

/**
 * @Author: lxl
 * @describe 属性表Sercice
 * @Date: 2019/2/15 12:12
 * @Version 1.0
 */
public interface AttrInfoService {

    /**
     * @description 增加属性码
     * @author lxl
     * @date 2019-02-15 14:28
     * @param attrType 属性类别
     * @return 
     */
    String insertCode(Integer attrType);
}
