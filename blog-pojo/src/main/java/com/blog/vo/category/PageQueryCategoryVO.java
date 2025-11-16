package com.blog.vo.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryCategoryVO {
    private Integer id;

    private String categoryName;

    private Integer categoryStatus;
}
