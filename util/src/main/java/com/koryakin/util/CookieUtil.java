package com.koryakin.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil{
    public static String getAuthToken(HttpServletRequest request){
        String authToken = null;
        Cookie[]cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authCookie")) {
                    authToken = cookie.getValue();
                }
            }
        }
        return authToken;
    }
}
