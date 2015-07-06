package com.bbeerr.web.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

public class BaseUrlFilter {

	public Map<String, String> getCookieMap(Cookie[] cookies) {
		Map<String, String> map = new HashMap<String, String>();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				map.put(cookie.getName(), cookie.getValue().trim());
			}
		}
		return map;
	}

}