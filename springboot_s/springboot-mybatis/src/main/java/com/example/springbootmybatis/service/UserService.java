package com.example.springbootmybatis.service;

import com.example.springbootmybatis.pojo.User;

public interface UserService {
    public User findById(Integer id);
}
