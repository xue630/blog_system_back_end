package com.blog.controller;

import com.blog.constant.MessageConstant;
import com.blog.exception.AliOSSException;
import com.blog.result.Result;
import com.blog.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @GetMapping("/articleContent")
    public Result<String> getArticleContent(@RequestParam("id") Integer articleId) throws IOException {
        if(articleId == null||articleId<=0){
            throw new AliOSSException(MessageConstant.ARTID_ILL);
        }
        String articleContent = commonService.getArticleContent(articleId);
        return Result.success(articleContent);
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,@RequestParam("fileName") String fileName ,@RequestParam("type") String type) throws IOException {
        //全流程：前端发来文件和文件名，上传到OSS返回url
        String url = commonService.fileUpload(file, fileName+"."+type);
        return Result.success(url);
    }

}
