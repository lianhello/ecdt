package com.lian.ecdt.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class HelloController {
 
	@RequiresPermissions(value= {"listProduct"})
    @RequestMapping(value="/hello")
	@ResponseBody
    public String hello() {
    	System.out.print("hello");
        return "Hello Spring Boot!";
    }
 
}