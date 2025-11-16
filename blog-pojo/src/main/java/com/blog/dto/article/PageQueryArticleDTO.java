package com.blog.dto.article;

import com.blog.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章分页查询DTO
 * 其中page和pageSize必须
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryArticleDTO {
    private String articleTitle;

    private List<Integer> tagIds;

    private Integer page;

    private Integer pageSize;

    private Integer CategoryId;
}
