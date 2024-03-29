package com.vcv.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.vcv.Application;
import com.vcv.mapper.FileMapper;
import com.vcv.mapper.ItemCategoryMapper;
import com.vcv.mapper.ItemMapper;
import com.vcv.mapper.ReItemMapper;
import com.vcv.model.LearningFile;
import com.vcv.model.ReItem;
import com.vcv.model.ResObject;
import com.vcv.model.User;
import com.vcv.service.FileService;
import com.vcv.util.Constants;
import com.vcv.util.DateUtil;
import com.vcv.util.ExcelUtil;
import com.vcv.util.FileUtils;
import com.vcv.util.MongoUtil;
import com.vcv.util.PageUtil;
import com.vcv.util.RequestContextHolderUtil;
import com.vcv.util.qiniu.QiNiuFiles;


/**
 * 商品管理
 */
@Controller
public class FileController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	public static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()<<1);

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;
    
    
    @Autowired
    private ReItemMapper reItemMapper;

    public static final String ROOT = "src/main/resources/static/img/item/";

    MongoUtil mongoUtil = new MongoUtil();

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    List<LearningFile> fileList;

    File getFile = null;

    @RequestMapping("/user/fileManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String fileManage(LearningFile learningFile, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = fileService.count(learningFile);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        learningFile.setStart((pageCurrent - 1) * pageSize);
        learningFile.setEnd(pageSize);
        fileList = fileMapper.list(learningFile);
        for (LearningFile i : fileList) {
            i.setUpdatedStr(DateUtil.getDateStr(i.getUpdated()));
        }
        LearningFile file = new LearningFile();
        file.setStart(0);
        file.setEnd(Integer.MAX_VALUE);
        Integer minPrice = learningFile.getMinPrice();
        Integer maxPrice = learningFile.getMaxPrice();
        model.addAttribute("fileList", fileList);
        String pageHTML = PageUtil.getPageContent("itemManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + learningFile.getTitle() + "&cid=" + learningFile.getCid() + "&minPrice" + minPrice + "&maxPrice" + maxPrice, pageCurrent, pageSize, pageCount);
        model.addAttribute("learningFile", learningFile);
        return "file/fileManage";
    }

    @RequestMapping("/user/file/getExcels")
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap=setFieldMap(fieldMap);
        String sheetName = "商品管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=ItemManage.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
        	List files=fileService.queryList(getFormData(request));
        	ExcelUtil.listToExcel(fileList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LinkedHashMap<String, String> setFieldMap(LinkedHashMap<String, String> fieldMap) {
    	fieldMap.put("id", "商品id");
        fieldMap.put("title", "商品标题");
        fieldMap.put("sellPoint", "商品卖点");
        fieldMap.put("price", "商品价格");
        fieldMap.put("num", "库存数量");
        fieldMap.put("image", "商品图片");
        fieldMap.put("cid", "所属类目，叶子类目");
        fieldMap.put("status", "商品状态，1-正常，2-下架，3-删除");
        fieldMap.put("created", "创建时间");
        fieldMap.put("updated", "更新时间");
		return fieldMap;
	}

	String imageName = null;

    @GetMapping("/user/fileEdit")
    public String fileEditGet(Model model, LearningFile file) {
    	LearningFile learningFile = fileMapper.findById(file);
        //learningFile.setStart(0);
        //learningFile.setEnd(Integer.MAX_VALUE);
    	if(learningFile==null) {
    		learningFile=new LearningFile();
    	}
        model.addAttribute("file", file);
        return "file/fileEdit";
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    @PostMapping("/user/fileEdit")
    @ResponseBody
    public ResObject fileEditPost(Model model, MultipartHttpServletRequest  request, @RequestParam("file") CommonsMultipartFile file, LearningFile lfile, HttpSession httpSession) {
    	ResObject result=new ResObject().successRes();
    	User user =(User) RequestContextHolderUtil.getSession().getAttribute("user");
    	if(user==null) {
    		result.setResMsg("用户未登陆");
    		result.setFlag(false);
    		return result;
    	}
    	//上传文件
    	if(file.getSize()<=0) {
    		result.setResMsg("请导入文件");
    		result.setFlag(false);
    		return result;
    	}
    	if(file.getSize()>Integer.parseInt(Constants.FILE_SIZE.getValue())) {
    		result.setResMsg("文件不能超过30M");
    		result.setFlag(false);
    		return result;
    	}
    	if(user.getRoleId()!=1) {
    		result.setResMsg("权限不足");
    		result.setFlag(false);
    		return result;
    	}
    	Thread read=new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("多线程开始上传文件:"+file.getOriginalFilename());
				/*Map<String,Object> data=fileService.uploadFile2Qiniu(file,lfile); 
				//设置参数并且保存file
				if("success".equals(data.get("result"))) {
					data=fileService.saveFile((LearningFile)data.get("lfile")); 
				}
				if("success".equals(data.get("result"))) {
					result.setFlag(true);
					result.setResMsg(data.get("msg").toString());;
				}*/
			};
		});
    	read.start();
       // return "redirect:itemManage_0_0_0";
        return result;
    }

    @GetMapping(value = "/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile() {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, imageName).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(value = "/file/downloadFromQi")
    @ResponseBody
    public String getFileFromServer(String fileName,HttpServletResponse response) {
    	User user =(User) RequestContextHolderUtil.getSession().getAttribute("user");
    	String saveKey=fileService.getSaveKeyById(fileName);
    	String msg="下载成功";
    	if(user==null) {
    		msg="用户未登录";
    		return msg;
    	}
    	if(user.getRoleId()!=1) {
    		msg="权限不足";
    		return msg;
    	}
    	try {
    		String url=QiNiuFiles.getDownloadFileUrl(saveKey);
    		QiNiuFiles.download(url,response);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return msg;
    }
    
    @GetMapping(value = "/file/download")
    @ResponseBody
    public String getFileFromQiniu(String fileId,HttpServletResponse response ) {
        String path=fileService.getPathById(fileId);
    	FileUtils.download(path, response);
    	return "下载成功";
    }

    @ResponseBody
    @PostMapping("/user/fileEditState")
    public ResObject<Object> itemEditState(LearningFile lFile) {
    	LearningFile learningFile = fileMapper.findById(lFile);
        fileMapper.delete(learningFile);
        ResObject<Object> object = new ResObject<Object>().successRes();
        return object;
    }
}
