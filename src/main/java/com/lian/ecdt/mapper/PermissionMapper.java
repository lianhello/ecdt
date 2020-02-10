package com.lian.ecdt.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lian.ecdt.entity.Permission;

@Mapper
public interface PermissionMapper {
	
	@Select("select * from ecdt_permission")
	public Set<String> listPermissions();
	
	@Select("select * from ecdt_permission where id = #{id}")
	public Permission getById(int id);
	
	
	
	
}
