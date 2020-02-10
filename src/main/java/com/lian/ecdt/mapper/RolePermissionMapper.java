package com.lian.ecdt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lian.ecdt.entity.RolePermission;

@Mapper
public interface RolePermissionMapper {
	
	@Select("select * from ecdt_role_permission where rid = #{rid}")
	public List<RolePermission> ListByRid(int rid);
}
