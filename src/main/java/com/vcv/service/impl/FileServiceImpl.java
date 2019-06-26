package com.vcv.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Maps;
import com.vcv.controller.FileController;
import com.vcv.mapper.FileMapper;
import com.vcv.model.LearningFile;
import com.vcv.service.FileService;
import com.vcv.util.Constants;
import com.vcv.util.FileUtils;
import com.vcv.util.qiniu.QiNiuFiles;

@Service
@Transactional
public class FileServiceImpl implements FileService{
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private FileMapper fileMapper;
	@Override
	public Map<String, Object> uploadFile2Server(CommonsMultipartFile file, LearningFile lfile) {
		String fileName=file.getOriginalFilename();
		String filePrefix=fileName.substring(0,fileName.indexOf('.'));
		String fileSuffix=fileName.substring(fileName.indexOf('.'), fileName.length());
		Map<String,Object>result=Maps.newHashMap();
		 if (file.isEmpty()) {
	        	result.put("result", "fail");
	        	result.put("msg", "上传文件为空");
	        } else {
	            try {
	            	String flag=QiNiuFiles.uploadFileByStream(file,filePrefix);
	                String path = Constants.PRO_SAVE_PATH.getValue()+file.getName()+System.currentTimeMillis()+fileSuffix;
	                File tempFile = new File(path);
	                if (!tempFile.exists()) {
	                   FileUtils.upload2(path, file.getName(), file);
	                   lfile.setFileUrl(path);
	                }
	                result.put("result", "success");
	                result.put("msg", "上传文件成功");
	                result.put("lfile", lfile);
	            } catch (Exception e) {
	            	logger.error("上传文件异常:"+e.getMessage());
	            }
	        }
		return result;
	}

	@Override
	public Map<String, Object> saveFile(LearningFile lfile) {
		Map<String,Object>result=Maps.newHashMap();
		//根据时间和随机数生成id
        Date date = new Date();
        lfile.setCreated(date);
        lfile.setUpdated(date);
        if (lfile.getId()>0) {
        	int flag=0;
        	try {
        		flag =fileMapper.update(lfile);
			} catch (Exception e) {
				logger.error("修改文件时异常："+e.getMessage());
			}
            if(flag>0) {
            	result.put("result", "success");
                result.put("msg", "文件修改成功");
            }else {
            	result.put("result", "fail");
	        	result.put("msg", "文件修改失败");
            }
        } else {
        	int flag=0;
        	try {
        		flag=fileMapper.insert(lfile);
			} catch (Exception e) {
				logger.error("新增文件时异常："+e.getMessage());
			}
        	if(flag>0) {
            	result.put("result", "success");
                result.put("msg", "文件新增成功");
            }else {
            	result.put("result", "fail");
	        	result.put("msg", "文件新增失败");
            }
        };
        return result;
	}

	@Override
	public int count(LearningFile learningFile) {
		return fileMapper.count(learningFile);
	}

	@Override
	public List<LearningFile> queryList(Map<String, String> formData) {
		return fileMapper.queryList(formData);
	}

	@Override
	public String getPathById(String fileId) {
		 
		return fileMapper.getPathById(fileId);
	}

	@Override
	public Map<String, Object> uploadFile2Qiniu(CommonsMultipartFile file, LearningFile lfile) {
		String fileName=file.getOriginalFilename();
		String saveKey=QiNiuFiles.uploadFileByStream(file,fileName);
		Map<String,Object>result=Maps.newHashMap();
		if(saveKey!=null) {
			lfile.setSaveKey(saveKey);
			result.put("result", "success");
	        result.put("msg", "上传文件成功");
	        result.put("lfile", lfile);
		}else {
			result.put("result", "fail");
        	result.put("msg", "上传文件失败");
		}
		return  result;
	}

	@Override
	public String getSaveKeyById(String fileId) {
		// TODO Auto-generated method stub
		return fileMapper.getSaveKeyById(fileId);
	}

}
