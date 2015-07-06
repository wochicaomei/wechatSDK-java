package com.bbeerr.web.security.filter;

import com.bbeerr.web.security.Passport;

public interface IDataFilter {

	public boolean check(String url, Passport passport);

}