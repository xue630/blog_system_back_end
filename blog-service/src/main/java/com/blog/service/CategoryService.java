package com.blog.service;


import com.blog.dto.category.PageQueryCategoryDTO;
import com.blog.entities.Category;
import com.blog.result.PageQuery;
import com.blog.vo.category.PageQueryCategoryVO;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    
    void addCategory(String categoryName);
    
    void updateCategory(Integer id, String categoryName);
    
    void updateCategoryStatus(Integer id, Integer status);
    
    void deleteCategory(String ids);
    
    PageQuery<PageQueryCategoryVO> pageQueryCategory(PageQueryCategoryDTO pageQueryCategoryDTO);
}
