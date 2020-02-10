package com.lian.ecdt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lian.ecdt.entity.Role;

@Mapper
public interface RoleMapper {
	@Select("select * from ecdt_role where id = #{uid}")
	@Results({
		@Result(property="uid",
				column="uid",
				one = @One(select="com.lian.ecdt.mapper.UserRoleMapper.getByUid"))
	})
	public Role getRoleByUid(Long uid);
	
	
	public List<Role> listRoles();
	
	@Select("select * from ecdt_role where name = #{name}")
	public Role getByName(String name);
	
	
	public void add(Role role);
	public void delete(int id);
	@Select("select * form ecdt_role where id = #{id}")
	public Role get(int id);
	public void update(Role role);
	
}
