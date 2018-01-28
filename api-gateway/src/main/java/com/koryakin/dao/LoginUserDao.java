package com.koryakin.dao;

import com.koryakin.model.LoginUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserDao extends CrudRepository<LoginUser, Long> {
    LoginUser findByUsername(String username);
}
