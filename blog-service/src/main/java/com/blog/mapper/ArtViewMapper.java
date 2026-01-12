package com.blog.mapper;

import com.blog.entities.ArtView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArtViewMapper {
    @Insert("insert into art_view values (#{artId},#{views})")
    void insertArticleView(ArtView artView);

    @Select("select views from art_view where art_id=#{artId}")
    Integer getViewByArtId(int artId);

/**
 * 更新文章浏览次数的方法
 * 使用@Update注解来执行SQL更新语句
 * @param artId 文章ID，用于定位需要更新的文章
 */
    @Update("update art_view set views=views+1 where art_id=#{artId}")
    void addViewCount(int artId);
}
