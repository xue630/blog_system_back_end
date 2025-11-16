package com.blog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;

    private String articleName;

    private String articleTitle;

    private String articleUrl;

    private String coverImage;

    private String articleSummary;

    private Integer articleStatus;

    private LocalDateTime createTime;

    private Integer categoryId;
}
