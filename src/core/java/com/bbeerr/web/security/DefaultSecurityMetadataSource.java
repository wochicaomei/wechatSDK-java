package com.bbeerr.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

// 加载资源与权限的对应关系  
public class DefaultSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	static Logger logger = LogManager.getFormatterLogger("uri");

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String reqUri = ((FilterInvocation) object).getRequestUrl();
		logger.info(reqUri);

		Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		ConfigAttribute configAttribute = new SecurityConfig(reqUri);
		configAttributes.add(configAttribute);

		return configAttributes;
	}

}