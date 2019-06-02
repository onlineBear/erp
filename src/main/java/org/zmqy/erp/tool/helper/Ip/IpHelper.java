package org.zmqy.erp.tool.helper.Ip;

import javax.servlet.http.HttpServletRequest;

public class IpHelper {
    public static String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    //获取ip加端口号
    public static String getIpAndPort(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    }

}
