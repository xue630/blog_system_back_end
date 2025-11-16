package com.blog.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类分页查询DTO
 * 其中page和pageSize必须
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryCategoryDTO {
    private String categoryName;

    private Integer page;

    private Integer pageSize;
}
