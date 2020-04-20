package com.lrm.service;

import com.lrm.NotFoundExceptin;
import com.lrm.dao.TypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lrm.po.Type;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;


    @Override
    @Transactional
    public Type save(Type type) {
        if(type.getName()==null){
            throw new NotFoundExceptin("名字不能为空");
        }
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {

        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public List<Type> listTypeTop(Integer size) {
        return null;
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findOne(id);
        if(t==null){
            throw new NotFoundExceptin("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
            typeRepository.delete(id);
    }
}
