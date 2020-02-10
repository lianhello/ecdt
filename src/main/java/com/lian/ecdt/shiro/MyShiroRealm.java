package com.lian.ecdt.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.lian.ecdt.entity.User;
import com.lian.ecdt.service.PermissionService;
import com.lian.ecdt.service.RoleService;
import com.lian.ecdt.service.UserService;

public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("-----授权------");
		// 能进入到这里，表示账号已经通过验证了
		String userName = (String) principalCollection.getPrimaryPrincipal();
		// 通过service获取角色和权限
		Set<String> permissions = permissionService.listPermissions(userName);
		String role = roleService.getRoleName(userName);

		// 授权对象
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		// 把通过service获取到的角色和权限放进去
		s.setStringPermissions(permissions);
		s.addRole(role);
		return s;
	}

	/**
	 * 主要是用来进行身份认证的，即验证用户输入的账号和密码是否正确
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("-----认证------");
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		// 通过username从数据库中查找
		User user = userService.getByUsername(username);
		if (user == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
				user.getPassword(), // 密码
				// ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
				getName() // realm name
		);

		return authenticationInfo;
	}

}
