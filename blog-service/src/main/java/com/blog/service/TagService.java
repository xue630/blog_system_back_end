package com.blog.service;

import com.blog.dto.tag.PageQueryTagDTO;
import com.blog.entities.Tag;
import com.blog.result.PageQuery;
import com.blog.vo.tag.PageQueryTagVO;

import java.util.List;

public interface TagService {
    List<Tag> getAllTag();
    
    void addTag(String tagName);
    
    void updateTag(Integer id, String tagName);
    
    void updateTagStatus(Integer id, Integer status);
    
    void deleteTag(String ids);
    
    PageQuery<PageQueryTagVO> pageQueryTag(PageQueryTagDTO pageQueryTagDTO);
}
