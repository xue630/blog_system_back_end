package com.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommonService {

    String getArticleContent(Integer articleId) throws IOException;

    String fileUpload(MultipartFile file,String fileName) throws IOException;

    void fileDelete(String fileName) throws IOException;
}
