package com.lian.ecdt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lian.ecdt.entity.User;
import com.lian.ecdt.mapper.UserMapper;
import com.lian.ecdt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserMapper usermapper;
	@Override
	public User getById(Long id) {
		return usermapper.getById(id);
	}

	@Override
	public User getByUsername(String username) {
		return usermapper.getByUsername(username);
	}
	
}
