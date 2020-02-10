package com.lian.ecdt.service;

import com.lian.ecdt.entity.User;

public interface UserService {
	public User getById(Long id);
	public User getByUsername(String username);
}
