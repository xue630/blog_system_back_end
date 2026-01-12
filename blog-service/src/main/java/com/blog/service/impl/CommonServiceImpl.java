package com.blog.service.impl;

import com.blog.constant.MessageConstant;
import com.blog.dto.common.UpdateAnnoDTO;
import com.blog.entities.Announcement;
import com.blog.entities.Article;
import com.blog.exception.AliOSSException;
import com.blog.mapper.AnnouncementMapper;
import com.blog.mapper.ArtViewMapper;
import com.blog.mapper.ArticleMapper;
import com.blog.properties.AliOssProperties;
import com.blog.service.CommonService;
import com.blog.utils.AliOssUtil;
import com.blog.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    AliOssProperties aliOssProperties;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    AnnouncementMapper announcementMapper;
    @Autowired
    ArtViewMapper artViewMapper;

    @Override
    public String getArticleContent(HttpServletRequest request, Integer articleId) throws IOException {
        //检查httpsession对象是否存在
        HttpSession session = request.getSession();//没有则创建
        String clientIp = IpUtil.getClientIp(request);
        //检查session域是否有文章id
        if (session.getAttribute("article"+articleId)==null) {//过期时间内没访问过
            log.info("{},查看了文章{}",clientIp,articleId);
            //对应文章计数器++
            artViewMapper.addViewCount(articleId);
            //将文章编号放入session域
            session.setAttribute("article"+articleId,articleId);
        }
        log.info("重复查看，不计数");
        session.setMaxInactiveInterval(60);


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

    @Override
    public void fileDelete(String fileName) throws IOException {
        AliOssUtil.delete(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                "article/" + fileName+".md");
        log.info("删除文件{}成功",fileName);
    }

    @Override
    public Announcement getAnnouncement() {
        return announcementMapper.getAnno();
    }

    @Override
    public void updateAnnouncement(UpdateAnnoDTO updateAnnoDTO) {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(updateAnnoDTO,announcement);
        announcement.setCreateTime(LocalDateTime.now());
        announcementMapper.updateContent(announcement);
    }

}
