package com.koryakin.service;

import com.koryakin.model.LoginUser;

import java.util.List;

public interface LoginUserService {

    LoginUser save(LoginUser user);
    List<LoginUser> findAll();
    void delete(long id);
    LoginUser findOne(String username);

    LoginUser findById(Long id);
}
