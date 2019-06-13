package com.vcv.exception;

public class SizeException extends Exception {
	 //构造函数
    public SizeException(){
        //重写
        super("尺寸大小不合适");
    }
}
