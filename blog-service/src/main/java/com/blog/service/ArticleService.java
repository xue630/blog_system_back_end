package com.blog.service;

import com.blog.dto.article.AddArticleDTO;
import com.blog.dto.article.GetValidArticleDTO;
import com.blog.dto.article.PageQueryArticleDTO;
import com.blog.dto.article.UpdateArticleDTO;
import com.blog.entities.Article;
import com.blog.entities.Tag;
import com.blog.result.PageQuery;
import com.blog.vo.article.PageQueryArticleVO;

import java.util.List;

public interface ArticleService {
    void addArticle(AddArticleDTO addArticleDTO);

    void updateArticle(UpdateArticleDTO updateArticleDTO);

    void deleteArticle(Integer id);

    void updateArticleStatus(Integer id, Integer status);

    Article getArticleById(Integer id);

    PageQuery<PageQueryArticleVO> pageQueryArticle(PageQueryArticleDTO pageQueryArticleDTO);

    List<PageQueryArticleVO> getValidAllArticle(GetValidArticleDTO validArticleDTO);


}
