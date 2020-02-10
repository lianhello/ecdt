package com.lian.ecdt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lian.ecdt.entity.Role;
import com.lian.ecdt.mapper.RoleMapper;
import com.lian.ecdt.service.RoleService;
import com.lian.ecdt.service.UserService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired private UserService userService;
	@Autowired private RoleMapper roleMapper;
	
	public Role getByName(String name) {
		return roleMapper.getByName(name);
	}
	
	@Override
	public String getRoleName(String username) {
		return roleMapper.getRoleByUid(userService.getByUsername(username).getId()).getName();
//		return roleMapper.get(userRoleMapper.getByUid(userService.getByUsername(username).getId()).getRid()).getName();
	}

}
