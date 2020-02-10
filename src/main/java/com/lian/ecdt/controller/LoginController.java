package com.lian.ecdt.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lian.ecdt.entity.User;
import com.lian.ecdt.service.RoleService;

@CrossOrigin
@Controller
public class LoginController {
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject doLogin(@RequestBody User user) throws Exception {
		JSONObject jsonObject = new JSONObject();
		System.out.println(user);
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			subject.login(token);

			String role = roleService.getRoleName(user.getUsername());

			jsonObject.put("token", subject.getSession().getId());
			jsonObject.put("code", 1001);
			jsonObject.put("msg", "登录成功");
			jsonObject.put("username", user.getUsername());
			jsonObject.put("role", role);
		} catch (IncorrectCredentialsException e) {
			jsonObject.put("code", 1000);
			jsonObject.put("msg", "用户名或密码错误");
		} catch (LockedAccountException e) {
			jsonObject.put("code", 1000);
			jsonObject.put("msg", "用户名或密码错误");
			// jsonObject.put("msg", "登录失败，该用户已被冻结");
		} catch (AuthenticationException e) {
			jsonObject.put("code", 1000);
			jsonObject.put("msg", "用户名或密码错误");
			// jsonObject.put("msg", "该用户不存在");
		} catch (Exception e) {
			jsonObject.put("code", 1000);
			jsonObject.put("msg", "用户名或密码错误");
			e.printStackTrace();
		}
		System.out.println(jsonObject);
		return jsonObject;
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping(value = "/logout")
	// @ResponseBody
	// public RespEntity<String> logout(HttpServletRequest request) throws Exception
	// {
	// RespEntity<String> resp = new RespEntity<>();
	// Subject currentUser = SecurityUtils.getSubject();
	// currentUser.logout();
	// resp.setHttpCode(HttpCode.Success);
	// resp.setMessage("注销成功");
	// return resp;
	// }

	/**
	 * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/unauth")
	@ResponseBody
	public Object unauth() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "1000000");
		map.put("msg", "未登录");
		return map;
	}

	// @RequestMapping(value = "/error")
	// @ResponseBody
	// public RespEntity<String> error(HttpServletRequest request) throws Exception
	// {
	// RespEntity<String> resp = new RespEntity<>();
	// resp.setHttpCode(HttpCode.Error);
	// resp.setMessage("服务器异常");
	// return resp;
	// }

}
