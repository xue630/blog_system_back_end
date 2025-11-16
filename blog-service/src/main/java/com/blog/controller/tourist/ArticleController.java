package com.blog.controller.tourist;

import com.blog.dto.article.GetValidArticleDTO;
import com.blog.entities.Article;
import com.blog.result.Result;
import com.blog.service.ArticleService;
import com.blog.vo.article.PageQueryArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("touristArticleController")
@RequestMapping("/tourist/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public Result<List<PageQueryArticleVO>> getValidAllArticle(GetValidArticleDTO getValidArticleDTO) {
        log.info("ArticleController pageArticle(处理分页查询文章事件):{}", getValidArticleDTO);
        List<PageQueryArticleVO> validAllArticle = articleService.getValidAllArticle(getValidArticleDTO);
        return Result.success(validAllArticle);
    }
}
