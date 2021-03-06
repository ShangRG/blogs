package com.lrm.service;

import com.lrm.po.Blog;

import com.lrm.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/20.
 */
public interface BlogService {

    Blog getBlog(Long id);

    //按照id获取博客，并将blog.comment由MAKERDOWN转换为html格式
    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    //根据名字模糊查询并分页
    Page<Blog> listBlog(String query,Pageable pageable);


    Page<Blog> listBlog(Long tagId, Pageable pageable);

    //博客归档
    Map<String,List<Blog>> archiveBlog();


    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);


    Long countBlog();
}
