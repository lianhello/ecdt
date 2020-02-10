package com.lian.ecdt.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lian.ecdt.entity.Permission;
import com.lian.ecdt.entity.RolePermission;
import com.lian.ecdt.mapper.PermissionMapper;
import com.lian.ecdt.mapper.RolePermissionMapper;
import com.lian.ecdt.service.PermissionService;
import com.lian.ecdt.service.RoleService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired private PermissionMapper permissionMapper;
	@Autowired private RoleService roleService;
	@Autowired private RolePermissionMapper rolePermissionMapper;
	
	public Set<String> listPermissions(String username) {
		int rid = roleService.getByName(username).getId();
		Set<String> result = new HashSet<>();
		List<RolePermission> rolePermissions = rolePermissionMapper.ListByRid(rid);
		for(RolePermission rp : rolePermissions) {
			Permission p = permissionMapper.getById(rp.getPid());
			result.add(p.getName());
		}
		return result;
	}
}
