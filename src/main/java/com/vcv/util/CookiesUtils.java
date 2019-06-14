package com.vcv.util;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtils {
	private static final String KEY = "cookie key";
    /**
     * 指令浏览器创建cookie的方法
     * @param username 放到cookie的用户名
     * @param req
     * @param resp 调用addcookie方法的response对象
     * @param sec 失效时间 单位：秒
     */
    public static void createCookie(String username, HttpServletRequest req, HttpServletResponse resp, int sec) {
        //写入userKey和ssid信息，方便下次登陆查询
        Cookie userCookie = new Cookie("userKey", username);
        Cookie ssidCookie = new Cookie("ssid", md5Encrypt(username));
        userCookie.setMaxAge(sec);
        ssidCookie.setMaxAge(sec);
        resp.addCookie(userCookie);
        resp.addCookie(ssidCookie);
    }
    /**
     * 把一个明文字符串加密成密文
     * @param str 要加密的明文
     * @return
     */
    public static String md5Encrypt(String str) {
        str= (str==null)?"":(str+KEY);
        char[] md5Digst = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};//字典
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//MD5  sha1
            byte[] strArr = str.getBytes();
            md.update(strArr);//把明文放到MessageDigest的对象中，更新数据
            byte[] msg = md.digest();//加密操作
            //进一步加密
            int len = msg.length;

            char[] result = new char[len*2];
            int k = 0;

            for(int i = 0; i < len; i++) {
                byte b = msg[i];
                result[k++] = md5Digst[b>>4 & 0xf];
                result[k++] = md5Digst[b & 0xf];
            }
            System.out.println(result);
            return new String(result);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
