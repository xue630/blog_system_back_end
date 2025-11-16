package com.blog.service.impl;

import com.blog.dto.article.AddArticleDTO;
import com.blog.dto.article.GetValidArticleDTO;
import com.blog.dto.article.PageQueryArticleDTO;
import com.blog.dto.article.UpdateArticleDTO;
import com.blog.entities.Article;
import com.blog.entities.Tag;
import com.blog.mapper.ArtTagMapper;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.TagMapper;
import com.blog.result.PageQuery;
import com.blog.service.ArticleService;
import com.blog.vo.article.PageQueryArticleVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArtTagMapper artTagMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    @Transactional
    public void addArticle(AddArticleDTO addArticleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(addArticleDTO, article);
        article.setArticleStatus(0);
        article.setCreateTime(LocalDateTime.now());
        // 插入文章并获取自动生成的ID
        articleMapper.insertArticle(article);
        
        // 添加文章标签关联
        if (addArticleDTO.getArticleTag()!= null && !addArticleDTO.getArticleTag().isEmpty()) {
            for (Tag tag : addArticleDTO.getArticleTag()) {
                // 插入文章标签关联，同时插入标签名称
                artTagMapper.insertArticleTag(article.getId(), tag.getId(), tag.getTagName());
            }
        }
    }



    @Override
    @Transactional
    public void updateArticle(UpdateArticleDTO updateArticleDTO) {
        // 创建文章对象并复制属性
        Article article = new Article();
        BeanUtils.copyProperties(updateArticleDTO, article);
        
        // 更新文章信息
        articleMapper.updateArticle(article);
        
        // 更新文章标签
        // 先删除该文章的所有标签
        artTagMapper.deleteTagsByArticleId(updateArticleDTO.getId());
        
        // 如果有新的标签ID列表，则添加新的标签关联
        if (updateArticleDTO.getTagIds() != null && !updateArticleDTO.getTagIds().isEmpty()) {
            for (Integer tagId : updateArticleDTO.getTagIds()) {
                // 查询标签信息
                Tag tag = tagMapper.getTagById(tagId);
                if (tag != null) {
                    // 插入文章标签关联，同时插入标签名称
                    artTagMapper.insertArticleTag(updateArticleDTO.getId(), tagId, tag.getTagName());
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteArticle(Integer id) {
        // 先删除文章关联的标签
        artTagMapper.deleteTagsByArticleId(id);
        
        // 创建文章对象并设置ID
        Article article = new Article();
        article.setId(id);
        
        // 删除文章
        articleMapper.deleteArticle(article);
    }

    @Override
    public void updateArticleStatus(Integer id, Integer status) {
        // 创建文章对象并设置ID和状态
        Article article = new Article();
        article.setId(id);
        article.setArticleStatus(status);
        
        // 更新文章状态
        articleMapper.updateArticle(article);
    }

    @Override
    public Article getArticleById(Integer id) {
        return null;
    }

    @Override
    public PageQuery<PageQueryArticleVO> pageQueryArticle(PageQueryArticleDTO articleDTO) {

        if(articleDTO.getArticleTitle().isEmpty()){//将空字符串转化为null
            articleDTO.setArticleTitle(null);
        }

        //先根据标签列表查询符合的文章id返回，之后查询按名称，分类id查询。
        List<Integer> articleIds = null;//按标签筛选完的文章的id
        PageQuery<PageQueryArticleVO> result = new PageQuery<>();//返回的结果集对象
        result.setRecords(new ArrayList<>());//创建结果集


        if(articleDTO.getTagIds()!=null&&!articleDTO.getTagIds().isEmpty()){
            articleIds = artTagMapper.getArticleIdsByTagIds(articleDTO.getTagIds());//获取含有这个标签的文章id
        }


        PageHelper.startPage(articleDTO.getPage(),articleDTO.getPageSize());

        if(articleIds!=null && articleIds.isEmpty()){
            result.setTotals(0L);
            return result;
        }

        //筛选结果集
        Page<Article> articles = articleMapper.pageQueryArticle(articleIds,articleDTO.getArticleTitle(), articleDTO.getCategoryId());

        articles.getResult().forEach(article -> {//注意：此时每个对象的Tags列表还为空
            PageQueryArticleVO tempPQAVO = new PageQueryArticleVO();
            BeanUtils.copyProperties(article,tempPQAVO);
            tempPQAVO.setTags(artTagMapper.getTagsByArticleId(article.getId()));//再根据此文章id查询还有哪些标签
            result.getRecords().add(tempPQAVO);
        });
        result.setTotals((articles.getTotal()));

        return result;
    }

    @Override
    public List<PageQueryArticleVO> getValidAllArticle(GetValidArticleDTO articleDTO) {
        if(articleDTO.getArticleTitle().isEmpty()){//将空字符串转化为null
            articleDTO.setArticleTitle(null);
        }

        //先根据标签列表查询符合的文章id返回，之后查询按名称，分类id查询。
        List<Integer> articleIds = null;//按标签筛选完的文章的id
        List<PageQueryArticleVO> result = new ArrayList<>();//返回的结果集对象

        //获取含有该标签组合的文章id列表，如果不提供列表则不走这个
        if(articleDTO.getTagIds()!=null&&!articleDTO.getTagIds().isEmpty()){
            //进行标签组的筛选，如果该标签组合没有符合的id就直接返回
            articleIds = artTagMapper.getArticleIdsByTagIds(articleDTO.getTagIds());
        }

        if(articleIds!=null && articleIds.isEmpty()){
            return result;
        }

        List<Article> validArticle = articleMapper.getValidArticle(articleIds, articleDTO.getArticleTitle(), articleDTO.getCategoryId());

        validArticle.forEach(article -> {//注意：此时每个对象的Tags列表还为空
            PageQueryArticleVO tempPQAVO = new PageQueryArticleVO();
            BeanUtils.copyProperties(article,tempPQAVO);
            tempPQAVO.setTags(artTagMapper.getTagsByArticleId(article.getId()));//再根据此文章id查询还有哪些标签
            result.add(tempPQAVO);
        });
        return result;
    }
}
