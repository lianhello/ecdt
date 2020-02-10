package com.lian.ecdt.shiro;
import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

public class MySessionManager extends DefaultWebSessionManager {
	private static final String AUTHORIZATION = "Authorization";

	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

	public MySessionManager() {
        super();
        // session失效时间，默认30min
        setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT * 1);
    }

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);// 如果请求头中有 Authorization 则其值为sessionId
		if (!StringUtils.isEmpty(sessionId)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		} else {
			// 否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}
}