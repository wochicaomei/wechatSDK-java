package com.bbeerr.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.RequestMatcher;

public class DefaultCsrfRequestMatcher implements RequestMatcher {

	@Override
	public boolean matches(HttpServletRequest request) {
		return request.getRequestURI().startsWith(request.getContextPath() + "/services/");
	}

}