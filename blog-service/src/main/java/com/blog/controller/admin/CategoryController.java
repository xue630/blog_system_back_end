package com.blog.controller.admin;

import com.blog.dto.category.PageQueryCategoryDTO;
import com.blog.entities.Category;
import com.blog.result.PageQuery;
import com.blog.result.Result;
import com.blog.service.CategoryService;
import com.blog.vo.category.PageQueryCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/select")
    public Result<List<Category>> selectAllCategory() {
        log.info("adminCategoryController selectAllCategory 提示：处理获取全部分类请求");
        List<Category> allCategory = categoryService.getAllCategory();
        return Result.success(allCategory);
    }
    
    @GetMapping("/page")
    public Result<PageQuery<PageQueryCategoryVO>> pageCategory(PageQueryCategoryDTO pageQueryCategoryDTO) {
        log.info("adminCategoryController pageCategory 提示：处理分页查询分类请求");
        PageQuery<PageQueryCategoryVO> pageQueryCategoryVOPageQuery = categoryService.pageQueryCategory(pageQueryCategoryDTO);
        return Result.success(pageQueryCategoryVOPageQuery);
    }

    @PostMapping
    public Result<String> addCategory(@RequestBody Map<String ,String> request) {
        log.info("adminCategoryController addCategory 提示：处理新增分类请求");
        String categoryName = request.get("categoryName");
        categoryService.addCategory(categoryName);
        return Result.success("新增分类成功");
    }
    
    @PutMapping
    public Result<String> updateCategory(@RequestBody Map<String, Object> request) {
        log.info("adminCategoryController updateCategory 提示：处理修改分类请求");
        Integer id = (Integer) request.get("id");
        String categoryName = (String) request.get("categoryName");
        categoryService.updateCategory(id, categoryName);
        return Result.success("修改分类成功");
    }
    
    @PostMapping("/status/{status}")
    public Result<String> updateCategoryStatus(@PathVariable Integer status, @Param("id") Integer id) {
        log.info("adminCategoryController updateCategoryStatus 提示：处理修改分类状态请求");
        categoryService.updateCategoryStatus(id, status);
        return Result.success("修改分类状态成功");
    }
    
    @DeleteMapping("/{ids}")
    public Result<String> deleteCategory(@PathVariable String ids) {
        log.info("adminCategoryController deleteCategory 提示：处理删除分类请求");
        categoryService.deleteCategory(ids);
        return Result.success("删除分类成功");
    }
}
