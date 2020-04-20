package com.lrm.dao;

import com.lrm.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
}
