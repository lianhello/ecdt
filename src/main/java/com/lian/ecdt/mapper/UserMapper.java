package com.lian.ecdt.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lian.ecdt.entity.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from ecdt_user where id = #{id}") 
	public User getById(Long id);

	@Select("select * from ecdt_user where username = #{username}")
	public User getByUsername(String username);
	
	@Insert("insert info ecdt_user (username,password,salt,email,phone,create_time) values (#{user.username},#{user.password},#{user.salt},#{user.email},#{user.phone},now())")
	public void add(User user);
} 