package com.lian.ecdt.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lian.ecdt.shiro.MySessionManager;
import com.lian.ecdt.shiro.MyShiroRealm;

@Configuration
public class ShiroConfig {
	/**
	 * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
		// Map<K,V> K指的是拦截的url V值的是该url是否拦截
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>(16);
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
		filterChainMap.put("/login.html", "anon");
		filterChainMap.put("/logout.html", "anon");
		filterChainMap.put("/login", "anon");
		filterChainMap.put("/logout", "anon");
		filterChainMap.put("/css/*", "anon");
		filterChainMap.put("/js/*", "anon");
		filterChainMap.put("/img/*", "anon");
		// filterChainMap.put("/**", "authc");
		
		// 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
		// 设置拦截请求后跳转的URL.
		// 没有登录的用户请求需要登录的页面时自动跳转到登录页面。 
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		// 没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 二.权限管理
	 * 
	 * @Title: securityManager Shiro核心 安全管理器
	 * @Description: SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理
	 * @return SecurityManager
	 *         SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。
	 *         （类似于SpringMVC中的DispatcherServlet控制器）
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(sessionManager());
		// 注入记住我管理器
		// securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 1.自定义认证
	 * 
	 * @Title: myShiroRealm
	 * @Description: ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理
	 * @return MyShiroRealm
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		// myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	/**
	 * 密码凭证匹配器，作为自定义认证的基础 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	/**
	 * 2.自定义sessionManager，用户的唯一标识，即Token或Authorization的认证
	 */
	@Bean
	public SessionManager sessionManager() {
		MySessionManager mySessionManager = new MySessionManager();
		// mySessionManager.setSessionDAO(redisSessionDAO());
		return mySessionManager;
	}

	/**
	 * Shiro生命周期处理器 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * Enable Shiro Annotations for Spring-configured beans. Only run after the lifecycleBeanProcessor(保证实现了Shiro内部lifecycle函数的bean执行) has run
     * 不使用注解的话，可以注释掉这两个配置
     */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}
}
