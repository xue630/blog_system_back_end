package com.blog.service.impl;

import com.blog.constant.MessageConstant;
import com.blog.entities.Article;
import com.blog.exception.AliOSSException;
import com.blog.mapper.ArticleMapper;
import com.blog.properties.AliOssProperties;
import com.blog.service.CommonService;
import com.blog.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    AliOssProperties aliOssProperties;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public String getArticleContent(Integer articleId) throws IOException {
        Article article = articleMapper.getArticleById(articleId);

        String download = AliOssUtil.download(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                "article/" + article.getArticleName()+".md"
        );
        return download;
    }

    @Override
    public String fileUpload(MultipartFile file, String fileName) throws IOException {
        String url;
        if (fileName.isEmpty()) {
            throw new AliOSSException(MessageConstant.FILE_NAME_IS_EMPTY);
        }
        if(file.isEmpty()) {
            throw new AliOSSException(MessageConstant.FILE_ILL);
        }
        //TODO新增逻辑：如果是图片，则生成uuid为文件名上传，如果是文章则检查map中是否已存在文章名
        if(!file.getOriginalFilename().endsWith(".md")) {
            fileName = "coverImage/"+UUID.randomUUID() +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        }
        url = AliOssUtil.upLoad(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                file.getBytes(),
                "article/"+fileName);
        return url;
    }
}
