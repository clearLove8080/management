package com.vcv.util.qiniu;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

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
			fileName=new String(fileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
		//download(finalUrl,"F:\\test\\save");
		System.out.println(finalUrl);
		return finalUrl;
	}
	
	public static void main(String[] args) {
	   uploadFileByPath("F:\\3.png"); 
	   System.out.println("----------------------------------------------------------------");
	   String url=  getDownloadFileUrl("JAVA并发编程实战.pdf");
	   download(url,"C:\\Users\\Administrator\\Downloads\\");
	}
	
	
	 /**
	   * 通过发送http get 请求获取文件资源
	   *
	   * @param url
	   * @param filepath
	   * @return
	   */
	  private static void download(String url, String filepath) {
	    OkHttpClient client = new OkHttpClient();
	    System.out.println(url);
	    Request req = new Request.Builder().url(url).build();
	    okhttp3.Response resp = null;
	    try {
	      resp = client.newCall(req).execute();
	      System.out.println(resp.isSuccessful());
	      if (resp.isSuccessful()) {
	        ResponseBody body = resp.body();
	        InputStream is = body.byteStream();
	        byte[] data = readInputStream(is);
	        //判断文件夹是否存在，不存在则创建
	        File file = new File(filepath);
	        if (!file.exists() && !file.isDirectory()) {
	          System.out.println("===文件夹不存在===创建====");
	          file.mkdir();
	        }
	        File imgFile = new File(filepath + "JAVA并发编程实战.pdf");
	        FileOutputStream fops = new FileOutputStream(imgFile);
	        fops.write(data);
	        fops.close();
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	      System.out.println("Unexpected code " + resp);
	    }
	  }
	  
	  /**
	   * 读取字节输入流内容
	   *
	   * @param is
	   * @return
	   */
	  private static byte[] readInputStream(InputStream is) {
	    ByteArrayOutputStream writer = new ByteArrayOutputStream();
	    byte[] buff = new byte[1024 * 2];
	    int len = 0;
	    try {
	      while ((len = is.read(buff)) != -1) {
	        writer.write(buff, 0, len);
	      }
	      is.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return writer.toByteArray();
	  }
	  
	  public static  void download(String url, HttpServletResponse response) {
		  OkHttpClient client = new OkHttpClient();
		    System.out.println(url);
		    Request req = new Request.Builder().url(url).build();
		    okhttp3.Response resp = null;
		  // String fileName = path.substring(path.lastIndexOf("/") + 1);
	      InputStream in = null;
	      BufferedOutputStream out = null;
	      try {
	    	  resp = client.newCall(req).execute();
		      System.out.println(resp.isSuccessful());
		      if (resp.isSuccessful()) {
		          ResponseBody body = resp.body();
		          in = body.byteStream();
		          response.reset();
		          response.setHeader("Accept-Ranges", "bytes");
		          response.setHeader("Pragma", "no-cache");
		          response.setHeader("Cache-Control", "no-cache");
		          response.setDateHeader("Expires", 0);
		          out = new BufferedOutputStream(response.getOutputStream());
		          byte[] buffer = new byte[16384];
		          int len;
		          while ((len = in.read(buffer)) > 0) {
		              out.write(buffer, 0, len);
		          }
		          out.flush();
		      }
	          logger.debug("下载文件【成功】：url=" + url);
	      } catch (FileNotFoundException var17) {
	          logger.debug("下载文件【文件不存在】：url=" + url);
	      } catch (Throwable var18) {
	          logger.error("下载文件【失败】：url=" + url, var18);
	      } finally {
	          try {
	              if (in != null) {
	                  in.close();
	              }
	              if (out != null) {
	                  out.close();
	              }
	          } catch (IOException var16) {
	              logger.error("关闭文件【失败】：url=" + url, var16);
	          }
	      }
	  }
}	