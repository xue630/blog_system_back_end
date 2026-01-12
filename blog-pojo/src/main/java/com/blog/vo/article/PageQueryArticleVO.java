package com.blog.vo.article;

import com.blog.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryArticleVO {
    private Integer id;

    private String articleName;

    private String articleTitle;

    private String articleUrl;

    private String coverImage;

    private String articleSummary;

    private Integer articleStatus;

    private LocalDateTime createTime;

    private Integer categoryId;

    private List<Tag> tags;

    private Integer viewCount;

}
