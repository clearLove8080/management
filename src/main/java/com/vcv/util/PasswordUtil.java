package com.vcv.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PasswordUtil {
	//加密
	public static String  Base64Encode(String src){
		String encode=null;
        try {
            BASE64Encoder encoder=new BASE64Encoder();
            encode = encoder.encode(src.getBytes());
            System.out.println("encode: "+encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }
	//解密
	public static String  Base64Decode(String src){
		String decode=null;
        try {
            BASE64Decoder decoder=new BASE64Decoder();
             decode=new String(decoder.decodeBuffer(src));
            System.out.println("decode: "+decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decode;
    }
	/*public static void main(String[] args) {
		Base64Decode(Base64Encode(src));
	}*/
}
