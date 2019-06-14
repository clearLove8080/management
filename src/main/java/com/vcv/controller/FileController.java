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
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.mongodb.gridfs.GridFSDBFile;
import com.vcv.mapper.FileMapper;
import com.vcv.mapper.ItemCategoryMapper;
import com.vcv.mapper.ItemMapper;
import com.vcv.mapper.ReItemMapper;
import com.vcv.model.LearningFile;
import com.vcv.model.ReItem;
import com.vcv.model.ResObject;
import com.vcv.util.DateUtil;
import com.vcv.util.ExcelUtil;
import com.vcv.util.MongoUtil;
import com.vcv.util.PageUtil;


/**
 * 商品管理
 */
@Controller
public class FileController {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;
    
    @Autowired
    private FileMapper fileMapper;

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

        int rows = fileMapper.count(learningFile);
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

    @RequestMapping("/user/file/download1")
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
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
        String sheetName = "商品管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=ItemManage.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(fileList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String imageName = null;

    @GetMapping("/user/fileEdit")
    public String fileEditGet(Model model, LearningFile file) {
        LearningFile learningFile = new LearningFile();
        learningFile.setStart(0);
        learningFile.setEnd(Integer.MAX_VALUE);
        List<LearningFile> fileList = fileMapper.list(learningFile);
        if (file.getId() != 0) {
        	LearningFile file1 = fileMapper.findById(file);
            String id = String.valueOf(file.getId());
            GridFSDBFile fileById = mongoUtil.getFileById(id);
            if (fileById != null) {
                StringBuilder sb = new StringBuilder(ROOT);
                imageName = fileById.getFilename();
                sb.append(imageName);
                try {
                    getFile = new File(sb.toString());
                    fileById.writeTo(getFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                file1.setImage(imageName);
            }
            model.addAttribute("file", file1);
        }
        return "file/fileEdit";
    }

    @PostMapping("/user/fileEdit")
    public String fileEditPost(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file, LearningFile lfile, HttpSession httpSession) {
        //根据时间和随机数生成id
        Date date = new Date();
        lfile.setCreated(date);
        lfile.setUpdated(date);
        lfile.setBarcode("");
        lfile.setImage("");
        int rannum = 0;
        if (file.isEmpty()) {
            System.out.println("图片未上传");
        } else {
            try {
                Path path = Paths.get(ROOT, file.getOriginalFilename());
                File tempFile = new File(path.toString());
                if (!tempFile.exists()) {
                    Files.copy(file.getInputStream(), path);
                }
                LinkedHashMap<String, Object> metaMap = new LinkedHashMap<String, Object>();
                String id = null;
                if (lfile.getId() != 0) {
                    id = String.valueOf(lfile.getId());
                } else {
                    Random random = new Random();
                    rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 1000;// 获取5位随机数
                    id = String.valueOf(rannum);
                }
                metaMap.put("contentType", "jpg");
                metaMap.put("_id", id);
                mongoUtil.uploadFile(tempFile, id, metaMap);
                tempFile.delete();
                getFile.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("get File by Id Success");
        }

        if (lfile.getId() != 0) {
            fileMapper.update(lfile);
        } else {

        	lfile.setId(rannum);
        	fileMapper.insert(lfile);
        }
        return "redirect:itemManage_0_0_0";
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

    @ResponseBody
    @PostMapping("/user/fileEditState")
    public ResObject<Object> itemEditState(LearningFile lFile) {
    	LearningFile learningFile = fileMapper.findById(lFile);
        ReItem reItem = new ReItem();
        reItem.setId(lFile.getId());
        reItem.setBarcode(lFile.getBarcode());
        reItem.setCid(lFile.getCid());
        reItem.setImage(lFile.getImage());
        reItem.setPrice(lFile.getPrice());
        reItem.setNum(lFile.getNum());
        reItem.setSellPoint(lFile.getScanPoint());
        reItem.setStatus(lFile.getStatus());
        reItem.setTitle(lFile.getTitle());
        reItem.setRecovered(new Date());
        reItemMapper.insert(reItem);
        fileMapper.delete(lFile);
        ResObject<Object> object = new ResObject<Object>().successRes();
        return object;
    }
}
