package com.blog.dto.article;

import com.blog.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改文章接口专用DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDTO {
    private Integer id;
    private String articleName;
    private String articleTitle;
    private String articleUrl;
    private String coverImage;
    private String articleSummary;
    private Integer categoryId;
    private List<Integer> tagIds;

}
