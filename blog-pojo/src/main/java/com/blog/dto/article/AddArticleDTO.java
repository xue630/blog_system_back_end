package com.blog.dto.article;

import com.blog.entities.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加文章接口所用DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDTO {

    @NotBlank(message = "文章名为空")
    private String articleName;
    @NotBlank(message = "封面地址为空")
    private String coverImage;
    @NotBlank(message = "文章地址为空")
    private String articleUrl;
    @NotBlank(message = "摘要为空")
    private String articleSummary;
    @NotNull(message = "分类id为空")
    private Integer categoryId;
    @NotEmpty(message = "标签列表为空")
    private List<Tag> articleTag;
    @NotBlank(message = "文章标题为空")
    private String articleTitle;
}
