package com.lrm.service;

import com.lrm.NotFoundExceptin;
import com.lrm.dao.TagRepository;
import com.lrm.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Override
    public Tag save(Tag type) {
        return tagRepository.save(type);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        return null;
    }

    @Override
    public Tag updateTag(Long id, Tag type) {
        Tag one = tagRepository.findOne(id);
        if(one==null){
            throw new NotFoundExceptin("没有此id");
        }
        BeanUtils.copyProperties(type,one);
        return tagRepository.save(one);
    }

    @Override
    public void deleteTag(Long id) {

        tagRepository.delete(id);

    }

    @Override
    public List<Tag> listTag(String ids) {

        return tagRepository.findAll(convertToList(ids)); //findAll这个find可以循环查询，知道你传递ids最后一个查完
    }

    private List<Long> convertToList(String ids) {  //id=“1，2，3”  切割字符串   1  2   3
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
