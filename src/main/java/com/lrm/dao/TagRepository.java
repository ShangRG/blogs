package com.lrm.dao;

import com.lrm.po.Tag;
import com.lrm.po.Type;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);


    @Modifying
    @Query(value = "insert into t_tag(name) values(?1)",nativeQuery = true)
    int addTag(String name);


    /**
     * 其实就是一个分页查询
     * @param pageable
     * @return
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);


}
