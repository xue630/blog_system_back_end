package com.blog.controller.admin;


import com.blog.dto.tag.PageQueryTagDTO;
import com.blog.entities.Tag;
import com.blog.result.PageQuery;
import com.blog.result.Result;
import com.blog.service.TagService;
import com.blog.vo.tag.PageQueryTagVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("adminTagController")
@RequestMapping("/admin/tag")
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/select")
    public Result<List<Tag>> selectAllTag() {
        log.info("adminTagController selectAllTag 提示：处理获取全部标签请求");
        List<Tag> allTag = tagService.getAllTag();
        return Result.success(allTag);
    }
    
    @GetMapping("/page")
    public Result<PageQuery<PageQueryTagVO>> pageTag(PageQueryTagDTO pageQueryTagDTO) {
        log.info("adminTagController pageTag 提示：处理分页查询标签请求");
        PageQuery<PageQueryTagVO> pageQueryTagVOPageQuery = tagService.pageQueryTag(pageQueryTagDTO);
        return Result.success(pageQueryTagVOPageQuery);
    }

    @PostMapping
    public Result<String> addTag(@RequestBody Map<String, String> request) {
        log.info("adminTagController addTag 提示：处理新增标签请求");
        String tagName = request.get("tagName");
        tagService.addTag(tagName);
        return Result.success("新增标签成功");
    }
    
    @PutMapping
    public Result<String> updateTag(@RequestBody Map<String, Object> request) {
        log.info("adminTagController updateTag 提示：处理修改标签请求");
        Integer id = (Integer) request.get("id");
        String tagName = (String) request.get("tagName");
        tagService.updateTag(id, tagName);
        return Result.success("修改标签成功");
    }
    
    @PostMapping("/status/{status}")
    public Result<String> updateTagStatus(@PathVariable Integer status,  @Param("id") Integer id) {
        log.info("adminTagController updateTagStatus 提示：处理修改标签状态请求");
        tagService.updateTagStatus(id, status);
        return Result.success("修改标签状态成功");
    }
    
    @DeleteMapping("/{ids}")
    public Result<String> deleteTag(@PathVariable String ids) {
        log.info("adminTagController deleteTag 提示：处理删除标签请求");
        tagService.deleteTag(ids);
        return Result.success("删除标签成功");
    }
}
