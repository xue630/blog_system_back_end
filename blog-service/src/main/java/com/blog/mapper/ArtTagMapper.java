package com.blog.mapper;

import com.blog.entities.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArtTagMapper {

    /**
     * 根据标签的id列表查询对应的文章id列表
     * @param tagIds
     * @return
     */

    List<Integer> getArticleIdsByTagIds(List<Integer> tagIds);

    /**
     * 根据文章id获取它的标签
     * @param articleId
     * @return
     */
    List<Tag> getTagsByArticleId(Integer articleId);
    
    /**
     * 删除文章的所有标签
     * @param articleId
     */
    @Delete("delete from art_tag where article_id = #{articleId}")
    void deleteTagsByArticleId(Integer articleId);
    
    /**
     * 为文章添加标签
     * @param articleId
     * @param tagId
     * @param tagName
     */
    @Insert("insert into art_tag(article_id, tag_id, tag_name) values(#{articleId}, #{tagId}, #{tagName})")
    void insertArticleTag(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId, @Param("tagName") String tagName);
}
