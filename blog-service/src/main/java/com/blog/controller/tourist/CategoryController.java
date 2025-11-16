package com.blog.controller.tourist;

import com.blog.entities.Category;
import com.blog.result.Result;
import com.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("touristCategoryController")
@RequestMapping("/tourist/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/select")
    public Result<List<Category>> selectAllCategory() {
        log.info("touristCategoryController selectAllCategory 提示：处理获取全部分类请求");
        List<Category> allCategory = categoryService.getAllCategory();
        return Result.success(allCategory);
    }
}
