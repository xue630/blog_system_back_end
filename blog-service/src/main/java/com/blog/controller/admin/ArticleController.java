package com.blog.controller.admin;

import com.blog.constant.MessageConstant;
import com.blog.context.ArticleNameSet;
import com.blog.dto.article.AddArticleDTO;
import com.blog.dto.article.PageQueryArticleDTO;
import com.blog.dto.article.UpdateArticleDTO;
import com.blog.exception.ArticleException;
import com.blog.result.PageQuery;
import com.blog.result.Result;
import com.blog.service.ArticleService;
import com.blog.service.CommonService;
import com.blog.vo.article.PageQueryArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("adminArticleController")
@Slf4j
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public Result<String> addArticle(@Valid @RequestBody AddArticleDTO addArticleDTO) {
        log.info("ArticleController addArticleDTO(处理添加文章事件):{}", addArticleDTO);
        if(ArticleNameSet.getInstance().contains(addArticleDTO.getArticleName())){
            throw new ArticleException(MessageConstant.REPETITION_FILENAME);
        }
        articleService.addArticle(addArticleDTO);

        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageQuery<PageQueryArticleVO>> pageArticle(PageQueryArticleDTO pageQueryArticleDTO) {
        log.info("ArticleController pageArticle(处理分页查询文章事件):{}", pageQueryArticleDTO);
        PageQuery<PageQueryArticleVO> pageQueryArticleVOPageQuery = articleService.pageQueryArticle(pageQueryArticleDTO);
        log.info("ArticleController pageArticle(处理分页查询文章事件，响应数据):{}", pageQueryArticleVOPageQuery);
        return Result.success(pageQueryArticleVOPageQuery);
    }

    @PutMapping()
    public Result<String> updateArticle(@Valid @RequestBody UpdateArticleDTO updateArticleDTO) {
        log.info("ArticleController updateArticle(处理修改文章事件):{}", updateArticleDTO);
        articleService.updateArticle(updateArticleDTO);
        return Result.success();
    }

    @PostMapping("/{id}")
    public Result<String> updateArticleStatus(@PathVariable Integer id, @RequestParam Integer status) {
        log.info("ArticleController updateArticleStatus(处理文章上下架事件):文章ID={}, 状态={}", id, status);
        articleService.updateArticleStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteArticle(@PathVariable Integer id) {
        log.info("ArticleController deleteArticle(处理删除文章事件):文章ID={}", id);
        articleService.deleteArticle(id);
        return Result.success();
    }
}
