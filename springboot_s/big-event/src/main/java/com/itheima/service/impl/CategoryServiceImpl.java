package com.itheima.service.impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    //注入mapper
    @Autowired
    private CategoryMapper categoryMapper;
    //新增分类
    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }
    //列表查询
    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }
    //根据ID查询分类信息
    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);
        return c;
    }
    //更新分类
    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }
    //删除分类
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
