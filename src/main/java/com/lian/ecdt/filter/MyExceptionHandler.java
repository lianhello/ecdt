package com.lian.ecdt.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class MyExceptionHandler {
	@ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
	@ResponseBody
	public JSONObject authorizationExceptionHandler() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 403);
		jsonObject.put("msg", "没有权限");
		return jsonObject;
	}
	
	
	@ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
	@ResponseBody
	public JSONObject unauthenticatedExceptionHandler() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 401);
		jsonObject.put("msg", "没有登录");
		return jsonObject;
	}
}