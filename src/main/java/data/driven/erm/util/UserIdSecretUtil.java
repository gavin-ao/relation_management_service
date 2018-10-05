package data.driven.erm.util;

import org.apache.commons.codec.binary.Base64;

import java.net.URLEncoder;

/**
 * 用于处理UserID作为分享的参数编码加密处理
 * @author hejinkai
 * @date 2018/10/2
 */
public class UserIdSecretUtil {

    /**
     * 编码
     * @param userId
     * @return
     */
    public static String encode(String userId){
        if(userId != null && userId.trim().length() > 0){
            //反转字符串，然后进行base64编码
            StringBuilder sb = new StringBuilder(userId);
            sb.reverse();
            byte[] temp = Base64.encodeBase64(sb.toString().getBytes());
            return new String(temp);
        }
        return null;
    }

    /**
     * 解码
     * @param userId
     * @return
     */
    public static String decode(String userId){
        if(userId != null && userId.trim().length() > 0){
            //base64解码，然后反转字符串返回正确的id
            byte[] temp = Base64.decodeBase64(userId.getBytes());
            String tempUserId = new String(temp);
            System.out.println("tempUserId="+tempUserId);
            StringBuilder sb = new StringBuilder(tempUserId);
            sb.reverse();
            return sb.toString();
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        String userId = "1";
        System.out.println("userId="+userId);
        String encodeStr = UserIdSecretUtil.encode(userId);
        System.out.println("encodeStr="+encodeStr);
        System.out.println(URLEncoder.encode(encodeStr, "UTF-8"));

        String decodeStr = UserIdSecretUtil.decode(encodeStr);
        System.out.println("decodeStr="+decodeStr);
        System.out.println("decodeStr=userId? : "+decodeStr.equals(userId));

    }

}
