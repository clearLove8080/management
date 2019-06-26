package com.vcv.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.vcv.model.LearningFile;

public interface FileService {

	Map<String, Object> uploadFile2Server(CommonsMultipartFile file, LearningFile lfile);

	Map<String, Object> saveFile(LearningFile lfile);

	int count(LearningFile learningFile);

	List queryList(Map<String, String> formData);

	String getPathById(String fileId);

	Map<String, Object> uploadFile2Qiniu(CommonsMultipartFile file, LearningFile lfile);

	String getSaveKeyById(String fileName);

}
