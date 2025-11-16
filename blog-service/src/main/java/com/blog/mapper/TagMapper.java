package com.blog.mapper;

import com.blog.entities.Tag;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
    @Insert("insert into tag(tag_name, tag_status) values(#{tagName}, #{tagStatus})")
    void insertTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(Integer id);

    Tag getTagById(Integer id);

    @Select("select * from tag where tag_status=1")
    List<Tag> getAllTags();
    
    Page<Tag> pageQueryTag(String tagName);
}
