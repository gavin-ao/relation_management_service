package data.driven.erm.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: lxl
 * @describe ip相关公共方法
 * @Date: 2019/1/21 10:45
 * @Version 1.0
 */
public class IpUtils {

    /**
     * @description 获取客户ip
     * @author lxl
     * @date 2019-01-21 10:46
     * @param request
     * @return 返回用户实际ip
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
            String localIp = "127.0.0.1";
            String localIpv6 = "0:0:0:0:0:0:0:1";
            if (ipAddress.equals(localIp) || ipAddress.equals(localIpv6)){
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try{
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况,第一个IP为客户端真实IP,多个IP按照','分割
        String ipSeparate = ",";
        int ipLength = 15;
        if (ipAddress != null && ipAddress.length()>ipLength){
            if (ipAddress.indexOf(ipSeparate)>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(ipSeparate));
            }
        }
        return ipAddress;
    }


//    public static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                return ip.substring(0,index);
//            }else{
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            return ip;
//        }
//        return request.getRemoteAddr();
//    }
}






















