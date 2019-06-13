package com.vcv.exception;

public class FileFormatException extends Exception {
	//构造函数
    public FileFormatException(){
        //重写
        super("文件转换异常");
    }
}
