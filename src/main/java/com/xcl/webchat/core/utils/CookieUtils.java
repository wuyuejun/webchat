package com.xcl.webchat.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :xiaochanglu
 * @Description :Cookie工具类
 * @date :2019/1/8 19:05
 */
@Slf4j
public class CookieUtils {
    /**
     * 协议
     */
    public static final String PROTOCOL = "https";

    /**
     * Cookie默认 时间  半小时
     */
    public static final long COOKIE_HALF_HOUR = 1800000;
    /**
     * @param : [request, name]
     * @return : java.lang.String
     * @Description ：根据Cookie名称从请求中获取Cookie对象 不存在该对象则返回Null
     * @author : xcl
     * @date : 2018/12/13 19:46
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Cookie名称为空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
    /**
     * @param : [request, name]
     * @return : java.lang.String
     * @Description ：根据Cookie名称直接得到Cookie值
     * @author : xcl
     * @date : 2018/12/13 19:53
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
//            //解码
//            try {
//                String str = cookie.getValue();
//                System.out.println("^^^^^"+str);
//                return URLDecoder.decode(str,"utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            return cookie.getValue();
        }
        return null;
    }
    /**
     * @Description  ：移除cookie
     * @author       : xcl
     * @param        : [request, response, name]
     * @date         : 2018/12/13 20:03
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response,
        String name, String path, String domain) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Cookie名称为空");
        }
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            if (path != null) {
                cookie.setPath(path);
            }
            if (domain != null) {
                cookie.setDomain(domain);
            }
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
    /**
     * @Description  ：添加一条新的Cookie，可以指定过期时间(单位：秒)
     * 如果maxValue为0,则cookie默认时间为30分钟过期
     * @author       : xcl
     * @param        : [response, name, value, times]
     * @date         : 2018/12/13 20:11
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
        String name, String value,int times) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Cookie名称为空");
        }
        if (null == value) {
            value = "";
        }
//        //cookie包含的编码方式是ASCII吗
//        try {
//            System.out.println("%%%%%"+value);
//            value = URLEncoder.encode(value, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (PROTOCOL.equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        if (times != 0) {
            cookie.setMaxAge(times);
        } else {
            cookie.setMaxAge((int) COOKIE_HALF_HOUR);
        }
        response.addCookie(cookie);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description  ：将cookie封装到Map里面
     * @author       : xcl
     * @param        : [request]
     * @return       : java.util.Map<java.lang.String,javax.servlet.http.Cookie>
     * @date         : 2018/12/13 20:14
     */
    public static Map<String, Cookie> getCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
