package com.blog.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryTagVO {
    private Integer id;

    private String tagName;

    private Integer tagStatus;
}
