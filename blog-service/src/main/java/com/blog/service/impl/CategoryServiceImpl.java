package com.blog.service.impl;

import com.blog.dto.category.PageQueryCategoryDTO;
import com.blog.entities.Category;
import com.blog.mapper.CategoryMapper;
import com.blog.result.PageQuery;
import com.blog.service.CategoryService;
import com.blog.vo.category.PageQueryCategoryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }
    
    @Override
    public void addCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryStatus(1); // 默认状态为1，表示启用
        categoryMapper.insertCategory(category);
    }
    
    @Override
    public void updateCategory(Integer id, String categoryName) {
        Category category = new Category();
        category.setId(id);
        category.setCategoryName(categoryName);
        // 不设置categoryStatus，这样动态SQL就不会更新这个字段
        categoryMapper.updateCategory(category);
    }
    
    @Override
    public void updateCategoryStatus(Integer id, Integer status) {
        Category category = new Category();
        category.setId(id);
        // 不设置categoryName，这样动态SQL就不会更新这个字段
        category.setCategoryStatus(status);
        categoryMapper.updateCategory(category);
    }
    
    @Override
    public void deleteCategory(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            categoryMapper.deleteCategory(Integer.parseInt(id));
        }
    }
    
    @Override
    public PageQuery<PageQueryCategoryVO> pageQueryCategory(PageQueryCategoryDTO pageQueryCategoryDTO) {
        // 处理空字符串
        if (pageQueryCategoryDTO.getCategoryName() != null && pageQueryCategoryDTO.getCategoryName().isEmpty()) {
            pageQueryCategoryDTO.setCategoryName(null);
        }
        
        // 创建分页结果对象
        PageQuery<PageQueryCategoryVO> result = new PageQuery<>();
        result.setRecords(new ArrayList<>());
        
        // 开始分页查询
        PageHelper.startPage(pageQueryCategoryDTO.getPage(), pageQueryCategoryDTO.getPageSize());
        
        // 执行查询
        Page<Category> categories = categoryMapper.pageQueryCategory(pageQueryCategoryDTO.getCategoryName());
        
        // 转换为VO对象
        categories.getResult().forEach(category -> {
            PageQueryCategoryVO vo = new PageQueryCategoryVO();
            BeanUtils.copyProperties(category, vo);
            result.getRecords().add(vo);
        });
        
        // 设置总数
        result.setTotals(categories.getTotal());
        
        return result;
    }
}
