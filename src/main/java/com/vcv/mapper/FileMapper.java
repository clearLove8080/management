package com.vcv.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.LearningFile;

/**
 * Created by jiFileunxiong on 2018/3/10.
 */
@Mapper
public interface FileMapper {

    LearningFile findById(LearningFile file);

    void delete(LearningFile file);

    List<LearningFile> list(LearningFile file);

    List<LearningFile> listS(LearningFile file);

    int count(LearningFile file);

    int insert(LearningFile file);

    int update (LearningFile file);


    List<LearningFile> selectAll();

	List<LearningFile> queryList(Map<String, String> formData);

	String getPathById(String fileId);
}
