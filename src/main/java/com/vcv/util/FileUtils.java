package com.vcv.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Administrator on 2017/9/13.
 */
public class FileUtils {
    /**
     * 下载文件
     *
     * @param path
     *            文件路径
     */
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static void download(String path,HttpServletResponse response) {
        String os = System.getProperty("os.name");
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        if(os.toLowerCase().startsWith("win") && path.indexOf("\\") != -1){
            fileName = path.substring(path.lastIndexOf("\\") + 1);
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            logger.debug("下载文件【开始】：path=" + path);
            in = new FileInputStream(path); // 文件流
            // 设置response的Header
            response.reset();
            response.setCharacterEncoding("GBK");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            response.setContentType(FileContentTypes.getContentType(fileName));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[16 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            logger.debug("下载文件【成功】：path=" + path);

        } catch (FileNotFoundException e) {
            logger.debug("下载文件【文件不存在】：path=" + path);
        } catch (Throwable e) {
            logger.error("下载文件【失败】：path=" + path, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件【失败】：path=" + path, e);
            }
        }
    }
    public static void upload2(String path, String fileName, CommonsMultipartFile file){
        if (!file.isEmpty()) {
            // 取文件格式后缀名
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            fileName = fileName + "." + fileType;

            File destFile = new File(path);

            try {
                // 复制临时文件到指定目录下
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            } catch (IOException e) {
                logger.error("【上次文件时异常】", e);
            }

        } else {
            logger.error("【导入智能模板失败】Excel内容为空");
        }
    }
    
    public static void uploadByFileName(String path, String fileName, CommonsMultipartFile file){
        if (!file.isEmpty()) {
            // 取文件格式后缀名
			/*
			 * String fileType =
			 * file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."))
			 * ; fileName = fileName + "." + fileType;
			 */
            File destFile = new File(path+fileName);

            try {
                // 复制临时文件到指定目录下
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            } catch (IOException e) {
                logger.error("【上次文件时异常】", e);
            }

        } else {
            logger.error("【导入智能模板失败】Excel内容为空");
        }
    }
    
    public static boolean delete(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(path);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(path);
            }
        }
    }
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag = false;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
	/**
	 * 下载文件
	 * 
	 * @param path
	 *            文件路径
	 */
	public static void download(String path) {
		String os = System.getProperty("os.name");
		int index = path.lastIndexOf("$$$");

		String fileName = path.substring(path.lastIndexOf("/") + 1);
		if(os.toLowerCase().startsWith("win") && path.indexOf("\\") != -1){
			if (index >= 0){
				//截取文件名称
				fileName = path.substring(path.lastIndexOf("\\") + 1,path.lastIndexOf("$$$"));
			}else {
				fileName = path.substring(path.lastIndexOf("\\") + 1);
			}
		}else{
			if (index >= 0){
				fileName = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("$$$"));
			}else {
				fileName = path.substring(path.lastIndexOf("/") + 1);
			}
		}

		InputStream in = null;
		OutputStream out = null;
		try {
			logger.debug("下载文件【开始】：path=" + path);
			in = new FileInputStream(path); // 文件流
			// 设置response的Header
			HttpServletResponse response = RequestContextHolderUtil.getResponse();
			response.reset();
			response.setCharacterEncoding("GBK");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			response.setContentType(FileContentTypes.getContentType(fileName));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[16 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			logger.debug("下载文件【成功】：path=" + path);

		} catch (FileNotFoundException e) {
			logger.debug("下载文件【文件不存在】：path=" + path);
		} catch (Throwable e) {
			logger.error("下载文件【失败】：path=" + path, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件【失败】：path=" + path, e);
			}
		}
	}
}
