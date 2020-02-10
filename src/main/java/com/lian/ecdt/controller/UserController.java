package com.lian.ecdt.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lian.ecdt.entity.User;
import com.lian.ecdt.service.UserService;

@Controller
public class UserController {
	@Autowired UserService userService;
	
	@RequiresPermissions(value= {"listProduct"})
	@ResponseBody
	@RequestMapping("/getUser")
	public User getUser(long id) {
		User u = userService.getById(id);
		if(u==null) {
			return null;
		}
		return u;		
	}
}
