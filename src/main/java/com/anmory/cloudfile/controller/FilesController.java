package com.anmory.cloudfile.controller;

import com.anmory.cloudfile.ai.*;
import com.anmory.cloudfile.model.Files;
import com.anmory.cloudfile.model.Users;
import com.anmory.cloudfile.service.AiHistoryService;
import com.anmory.cloudfile.service.FilesService;
import com.anmory.cloudfile.template.AiRet;
import com.anmory.cloudfile.template.Download;
import com.anmory.cloudfile.template.Upload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午10:12
 */

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {
    private FilesService fileService;
    Upload upload = new Upload();
    Download download = new Download();
    AiRet aiRet = new AiRet();
    @Autowired
    AiAss aiAss;
    @Autowired
    AiHistoryService aiHistoryService;
    @Autowired
    FilesController (FilesService fileService) {
        this.fileService = fileService;
        FilesService.getInstance();
    }
    @RequestMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file,
                          HttpServletRequest request, int userIdn) throws IOException {
        String name = file.getOriginalFilename();
        String size = file.getSize() + "";
        String type = file.getContentType();
        String path = "/usr/local/nginx/files/cloud-files/" + name;
        System.out.println(upload.primitiveOperation1());
        File dir  = new File("/usr/local/nginx/files/cloud-files/");
        if(!dir.exists()){
            dir.mkdirs();
        }
        System.out.println(upload.primitiveOperation2());
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(file.getBytes());
        log.info("文件上传成功");
        System.out.println(upload.primitiveOperation3());
        fos.close();
        Users users = (Users) request.getSession().getAttribute("session_user_key");
        if(users == null) {
            fileService.insert(name, size, type, path, userIdn);
        }else {
            fileService.insert(name, size, type, path, users.getId());
        }
        System.out.println(upload.primitiveOperation4());
        return false;
    }

    @RequestMapping("/getFilesList")
    public List<Files> getFilesList(HttpServletRequest request,int userIdn) {
        Users users = (Users) request.getSession().getAttribute("session_user_key");
        if(users == null) {
            return fileService.findByUserId(userIdn);
        }else {
            return fileService.findByUserId(users.getId());
        }
    }

    @RequestMapping("/download")
    public String download(int id, HttpServletRequest request, int userIdn) {
        System.out.println(download.primitiveOperation1());
        Files files = fileService.findById(id);
        Users users = (Users) request.getSession().getAttribute("session_user_key");
        if(users == null) {
            if(files.getUserId() == userIdn) {
                return files.getPath();
            }
        }
        else {
            if(files.getUserId() == users.getId()) {
                return files.getPath();
            }
        }
        System.out.println(download.primitiveOperation2());
        System.out.println(download.primitiveOperation3());
        System.out.println(download.primitiveOperation4());
        return null;
    }

    @RequestMapping("/delete")
    public boolean delete(int id, HttpServletRequest request, int userIdn) {
        Users users = (Users) request.getSession().getAttribute("session_user_key");
        if(users == null ) {
            Files files = fileService.findById(id);
            if(files.getUserId() == userIdn){
                if(fileService.deleteById(id) > 0){
                    new File(files.getPath()).delete();
                    log.info("文件删除成功");
                    return true;
                }
            }
        }else {
            Files files = fileService.findById(id);
            if(files.getUserId() == users.getId()){
                if(fileService.deleteById(id) > 0){
                    new File(files.getPath()).delete();
                    log.info("文件删除成功");
                    return true;
                }
            }
        }
        log.error("文件删除失败");
        return false;
    }
    @Autowired
    TextAi textAi;

    @RequestMapping("/ai")
    public String ai(@RequestBody Map<String, String> requestBody,
                     HttpServletRequest request, int userIdn) {
        String content = requestBody.get("content");
        String type = requestBody.get("type");
        System.out.println(aiRet.primitiveOperation1());
        System.out.println(content + "/" + type);
        Users users = (Users) request.getSession().getAttribute("session_user_key");
        switch (type) {
            case "douban":
                // 获取文件的地址
                if(users == null ) {
                    List<Files> files = fileService.findByUserId(userIdn);
                    String path = aiAss.getAss(content, files);
                    System.out.println(path);
                    DocAiFactory docAiFactory = new DocAiFactory();
                    DocAi docAi = docAiFactory.getAi(type);
                    String result = String.valueOf(docAi.getAiResult(path));
                    if(users == null ){
                        aiHistoryService.insert(userIdn, content, result);
                    }
                    return result;
                }
                else {
                    List<Files> files = fileService.findByUserId(users.getId());
                    String path = aiAss.getAss(content, files);
                    System.out.println(path);
                    DocAiFactory docAiFactory = new DocAiFactory();
                    DocAi docAi = docAiFactory.getAi(type);
                    String result = String.valueOf(docAi.getAiResult(path));
                    aiHistoryService.insert(users.getId(), content, result);
                    return result;
                }

            case "deepseek":
                String result2 = String.valueOf(textAi.getAiResult(content));
                if(users == null) {
                    aiHistoryService.insert(userIdn, content, result2);
                }else {
                    aiHistoryService.insert(users.getId(), content, result2);
                }
                return result2;
            case "tongyi":
                if(users == null ) {
                    List<Files> files2 = fileService.findByUserId(userIdn);
                    ImgAiFactory imgAiFactory = new ImgAiFactory();
                    ImgAi imgAi = imgAiFactory.getAi(type);
                    String path2 = aiAss.getAssImg(content, files2);
                    System.out.println(path2);
                    String ret = String.valueOf(imgAi.getAiResult(path2));
                    System.out.println(ret);
                    aiHistoryService.insert(userIdn, content, ret);
                    return String.valueOf(imgAi.getAiResult(path2));
                }else {
                    List<Files> files2 = fileService.findByUserId(users.getId());
                    ImgAiFactory imgAiFactory = new ImgAiFactory();
                    ImgAi imgAi = imgAiFactory.getAi(type);
                    String path2 = aiAss.getAssImg(content, files2);
                    System.out.println(path2);
                    String ret = String.valueOf(imgAi.getAiResult(path2));
                    System.out.println(ret);
                    aiHistoryService.insert(users.getId(), content, ret);
                    return String.valueOf(imgAi.getAiResult(path2));
                }

        }
        System.out.println(aiRet.primitiveOperation2());
        System.out.println(aiRet.primitiveOperation3());
        System.out.println(aiRet.primitiveOperation4());
        return null;
    }
}
