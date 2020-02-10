package com.lian.ecdt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lian.ecdt.entity.UserRole;

@Mapper
public interface UserRoleMapper {

	@Select("select * from ecdt_user_role where uid = #{uid} ")
	public UserRole getByUid(Long uid);

}
