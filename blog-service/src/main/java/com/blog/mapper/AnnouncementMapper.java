package com.blog.mapper;

import com.blog.dto.common.UpdateAnnoDTO;
import com.blog.entities.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AnnouncementMapper {
    @Select("select * from announcement")
    Announcement getAnno();
    void updateContent(Announcement anno);
}
