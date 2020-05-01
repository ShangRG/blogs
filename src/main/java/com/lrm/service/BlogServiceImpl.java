package com.lrm.service;

import com.lrm.NotFoundExceptin;
import com.lrm.dao.BlogRepository;
import com.lrm.po.Blog;
import com.lrm.po.BlogQuery;
import com.lrm.po.Type;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by limi on 2017/10/20.
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }


    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundExceptin("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }


    /**
     * jpa动态添加查询条件  ,加分页查询
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!=null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByLike(query,pageable);
    }

    /**
     * 多对多的查询 。  关联表查询
     * @param tagId
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    //为什么要写tags  使用为Blog类里有tags    private List<Tag> tags = new ArrayList<>();
                Join join = root.join("tags");  // 建立关联。当前Blog表与Tag表关联
                return cb.equal(join.get("id"),tagId);  //blog.id = tag.id
            }

        },pageable);

    }

    /**
     * 博客归档方法  按照年份进行归档博客
     * @return
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //查询年份
        List<String> groupYears = blogRepository.findGroupYear();
        Map<String,List<Blog>> maps =new HashMap<>();
        //往map里放博客归档信息   key是年份   value是属于这个年份的博客list
        for(String groupYear:groupYears){

            maps.put(groupYear,blogRepository.findByYear(groupYear));
        }
        return maps;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){   //说明是新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{  //更新
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundExceptin("博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
