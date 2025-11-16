package com.blog.mapper;

import com.blog.dto.article.GetValidArticleDTO;
import com.blog.entities.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(article_name, " +
            "article_url, cover_image, article_title, " +
            "article_summary, article_status, create_time," +
            "category_id) VALUES " +
            "(#{articleName},#{articleUrl},#{coverImage},#{articleTitle}" +
            ",#{articleSummary},#{articleStatus},#{createTime},#{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertArticle(Article article);

    /**
     * 用于updateArticle和updateArticleStatus用
     * @param article
     */
    void updateArticle(Article article);

    void deleteArticle(Article article);

    @Select("select * from article where id=#{id}")
    Article getArticleById(Integer id);

    Page<Article> pageQueryArticle(List<Integer> ids,String articleTitle, Integer categoryId);

    /**
     *
     * 获取上架的文章
     * @return
     */

    List<Article> getValidArticle(List<Integer> ids,String articleTitle, Integer categoryId);
}
