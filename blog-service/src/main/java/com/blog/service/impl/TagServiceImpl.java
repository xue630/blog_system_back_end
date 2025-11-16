package com.blog.service.impl;

import com.blog.dto.tag.PageQueryTagDTO;
import com.blog.entities.Tag;
import com.blog.mapper.TagMapper;
import com.blog.result.PageQuery;
import com.blog.service.TagService;
import com.blog.vo.tag.PageQueryTagVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTags();
    }
    
    @Override
    public void addTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tag.setTagStatus(1); // 默认状态为1，表示启用
        tagMapper.insertTag(tag);
    }
    
    @Override
    public void updateTag(Integer id, String tagName) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setTagName(tagName);
        // 不设置tagStatus，这样动态SQL就不会更新这个字段
        tagMapper.updateTag(tag);
    }
    
    @Override
    public void updateTagStatus(Integer id, Integer status) {
        Tag tag = new Tag();
        tag.setId(id);
        // 不设置tagName，这样动态SQL就不会更新这个字段
        tag.setTagStatus(status);
        tagMapper.updateTag(tag);
    }
    
    @Override
    public void deleteTag(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            tagMapper.deleteTag(Integer.parseInt(id));
        }
    }
    
    @Override
    public PageQuery<PageQueryTagVO> pageQueryTag(PageQueryTagDTO pageQueryTagDTO) {
        // 处理空字符串
        if (pageQueryTagDTO.getTagName() != null && pageQueryTagDTO.getTagName().isEmpty()) {
            pageQueryTagDTO.setTagName(null);
        }
        
        // 创建分页结果对象
        PageQuery<PageQueryTagVO> result = new PageQuery<>();
        result.setRecords(new ArrayList<>());
        
        // 开始分页查询
        PageHelper.startPage(pageQueryTagDTO.getPage(), pageQueryTagDTO.getPageSize());
        
        // 执行查询
        Page<Tag> tags = tagMapper.pageQueryTag(pageQueryTagDTO.getTagName());
        
        // 转换为VO对象
        tags.getResult().forEach(tag -> {
            PageQueryTagVO vo = new PageQueryTagVO();
            BeanUtils.copyProperties(tag, vo);
            result.getRecords().add(vo);
        });
        
        // 设置总数
        result.setTotals(tags.getTotal());
        
        return result;
    }
}
