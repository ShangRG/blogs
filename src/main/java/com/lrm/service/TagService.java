package com.lrm.service;

import com.lrm.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    /**
     * 保存方法
     * @param type
     * @return
     */
    Tag save(Tag tag);

    /**
     * 按照id查询单个Type
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 按照name查询单个type
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 分页  jpa封装的分页方法
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 查询所有Type
     * @return
     */
    List<Tag> listTag();

    /**
     *
     * @param size
     * @return
     */
    List<Tag> listTagTop(Integer size);

    /**
     *根据id跟新Type
     * @param id
     * @param type
     * @return
     */
    Tag updateTag(Long id,Tag tag);

    /**根据id删除Type
     *
     * @param id
     */
    void deleteTag(Long id);


    List<Tag> listTag(String ids);



    int saveTag(String name);
}
