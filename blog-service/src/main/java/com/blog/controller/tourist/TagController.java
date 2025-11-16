package com.blog.controller.tourist;

import com.blog.entities.Tag;
import com.blog.result.Result;
import com.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("touristTagController")
@RequestMapping("/tourist/tag")
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/select")
    public Result<List<Tag>> selectAllTag() {
        log.info("touristTagController selectAllTag 提示：处理获取全部标签请求");
        List<Tag> allTag = tagService.getAllTag();
        return Result.success(allTag);
    }
}
