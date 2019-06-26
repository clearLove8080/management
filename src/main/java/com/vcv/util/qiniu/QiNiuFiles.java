package com.vcv.util.qiniu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vcv.util.Constants;

public class QiNiuFiles {
	public static String accessKey="cvpOGP_sMVV-LmWocB6Fl03JEnw8CVRWfIIClHrK";
	public static String secretKey="M2AfM_PvtVbAJZ76tSDf3HnKKz76Iopvde5SANGv";
	public static String bucket="manage";
	private static final Logger logger = LoggerFactory.getLogger(QiNiuFiles.class);

	/**
	 * 本地上传
	 * @param localFilePath
	 * @return
	 */
	public static String uploadFileByPath(String	localFilePath) {
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		//String localFilePath = "/home/qiniu/test.png";
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = "图片测试";
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		String flag="";
		try {
			logger.debug("开始上传文件");
		    Response response = uploadManager.put(localFilePath, key, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    logger.debug("文件上传成功-----------------------------key："+putRet.key+"-------------------hash:"+putRet.hash);
		    flag=putRet.hash;
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    logger.error(r.toString());
		    try {
		    	logger.error(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}
		return flag;
	}
	/**
	 * 根据数据流上传
	 * @param localFilePath
	 * @return
	 */
	public static String uploadFileByStream(MultipartFile file,String fileName) {
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = fileName;
		String flag="";
		try {
		    byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
		    ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
		    Auth auth = Auth.create(accessKey, secretKey);
		    String upToken = auth.uploadToken(bucket);
		    try {
		        Response response=null;
				try {
					logger.debug("开始上传文件");
					response = uploadManager.put(file.getInputStream(),key,upToken,null, null);
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		        //解析上传成功的结果
		        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		        logger.debug("文件上传成功-----------------------------key："+putRet.key+"-------------------hash:"+putRet.hash);
			    flag=putRet.key;
		    } catch (QiniuException ex) {
		        Response r = ex.response;
		        logger.error(r.toString());
		        try {
		        	logger.error(r.bodyString());
		        } catch (QiniuException ex2) {
		            //ignore
		        }
		    }
		} catch (UnsupportedEncodingException ex) {
		    //ignore
		}
		return flag;
	}
	
	public static String getDownloadFileUrl(String fileName) {
		String domainOfBucket = Constants.QINIU_URL.getValue();
		try {
			fileName=new String(fileName.getBytes("GBK"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
		System.out.println(finalUrl);
		return finalUrl;
	}
	
	public static void main(String[] args) {
	   uploadFileByPath("F:\\3.png"); 
	   System.out.println("----------------------------------------------------------------");
	   getDownloadFileUrl("JAVA并发编程实战.pdf");
	}
}	