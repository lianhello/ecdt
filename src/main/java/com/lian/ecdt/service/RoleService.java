package com.lian.ecdt.service;

import com.lian.ecdt.entity.Role;

public interface RoleService {
	Role getByName(String name);
	String getRoleName(String username);
}
