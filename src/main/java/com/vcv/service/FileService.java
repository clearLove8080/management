package com.vcv.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.vcv.model.LearningFile;

public interface FileService {

	Map<String, Object> uploadFile(MultipartFile file, LearningFile lfile);

	Map<String, Object> saveFile(LearningFile lfile);

	int count(LearningFile learningFile);

	List queryList(Map<String, String> formData);

}
