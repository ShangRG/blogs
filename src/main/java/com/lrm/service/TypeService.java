package com.lrm.service;

import com.lrm.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface TypeService {
    /**
     * 保存方法
     * @param type
     * @return
     */
    Type save(Type type);

    /**
     * 按照id查询单个Type
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 按照name查询单个type
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 分页  jpa封装的分页方法
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 查询所有Type
     * @return
     */
    List<Type> listType();

    /**
     *
     * @param size
     * @return
     */
    List<Type> listTypeTop(Integer size);

    /**
     *根据id跟新Type
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**根据id删除Type
     *
     * @param id
     */
    void deleteType(Long id);
}
