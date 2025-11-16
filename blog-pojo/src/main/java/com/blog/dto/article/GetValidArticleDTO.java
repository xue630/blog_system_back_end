package com.blog.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetValidArticleDTO {
    private String articleTitle;

    private List<Integer> tagIds;

    private Integer CategoryId;
}
