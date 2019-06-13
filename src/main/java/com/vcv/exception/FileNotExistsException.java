package com.vcv.exception;

public class FileNotExistsException extends Exception {
	//构造函数
    public FileNotExistsException(){
        //重写
        super("文件不存在");
    }
}
