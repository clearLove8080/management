package com.vcv.exception;

public class NullFileException extends Exception {
	 //构造函数
    public NullFileException(){
        //重写
        super("文件内容为空");
    }
}
