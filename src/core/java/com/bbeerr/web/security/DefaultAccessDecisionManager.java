package com.bbeerr.web.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.bbeerr.web.security.filter.IDataFilter;

public class DefaultAccessDecisionManager implements AccessDecisionManager {

	Map<String, IDataFilter> dataFilters = new HashMap<String, IDataFilter>();

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		Object principal = authentication.getPrincipal();
		if (principal instanceof Passport) {
			return;
		}

		throw new AccessDeniedException("Access Denied.");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Map<String, IDataFilter> getDataFilters() {
		return dataFilters;
	}

	public void setDataFilters(Map<String, IDataFilter> dataFilters) {
		this.dataFilters = dataFilters;
	}

}