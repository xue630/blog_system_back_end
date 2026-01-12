package com.blog.service;

import com.blog.dto.common.UpdateAnnoDTO;
import com.blog.entities.Announcement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpRequest;

public interface CommonService {

    String getArticleContent(HttpServletRequest request, Integer articleId) throws IOException;

    String fileUpload(MultipartFile file,String fileName) throws IOException;

    void fileDelete(String fileName) throws IOException;

    Announcement getAnnouncement();

    void updateAnnouncement(UpdateAnnoDTO updateAnnoDTO);
}
