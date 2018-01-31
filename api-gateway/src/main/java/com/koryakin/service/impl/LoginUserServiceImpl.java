package com.koryakin.service.impl;

import com.koryakin.dao.LoginUserDao;
import com.koryakin.model.LoginUser;
import com.koryakin.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service(value = "loginUserService")
public class LoginUserServiceImpl implements UserDetailsService, LoginUserService {
	
	private final LoginUserDao loginUserDao;

	@Autowired
	public LoginUserServiceImpl(LoginUserDao loginUserDao) {
		this.loginUserDao = loginUserDao;
	}

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		LoginUser user = loginUserDao.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	public List<LoginUser> findAll() {
		List<LoginUser> list = new ArrayList<>();
		loginUserDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		loginUserDao.delete(id);
	}

	@Override
	public LoginUser findOne(String username) {
		return loginUserDao.findByUsername(username);
	}

	@Override
	public LoginUser findById(Long id) {
		return loginUserDao.findOne(id);
	}

	@Override
    public LoginUser save(LoginUser user) {
        return loginUserDao.save(user);
    }
}
