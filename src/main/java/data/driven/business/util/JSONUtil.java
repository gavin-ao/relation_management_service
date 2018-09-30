package data.driven.business.util;

import com.alibaba.fastjson.JSONObject;

/**
 * json工具类
 * @author hejinkai
 * @date 2018/6/15
 */
public class JSONUtil {

    /**
     * 设置常规信息返回JSONObject
     * @param success 成功标志
     * @param code  状态码
     * @param msg   信息
     * @return
     */
    public static JSONObject putMsg(boolean success, String code, String msg){
        JSONObject result = new JSONObject();
        result.put("success", success);
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

}
