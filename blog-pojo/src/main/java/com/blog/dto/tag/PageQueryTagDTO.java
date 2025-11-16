package com.blog.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签分页查询DTO
 * 其中page和pageSize必须
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryTagDTO {
    private String tagName;

    private Integer page;

    private Integer pageSize;
}
