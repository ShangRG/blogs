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


    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);


    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);
}
