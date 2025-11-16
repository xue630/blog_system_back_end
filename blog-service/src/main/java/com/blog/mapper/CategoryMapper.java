package com.blog.mapper;

import com.blog.entities.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Integer CategoryId);

    Category getCategoryById(Integer CategoryId);

    @Select("select * from category where category_status=1")
    List<Category> getAllCategory();
    
    Page<Category> pageQueryCategory(String categoryName);
}
