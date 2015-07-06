package com.bbeerr.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bbeerr.core.db.entity.User;
import com.bbeerr.core.service.ISecurityService;

public class DefaultUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ISecurityService securityService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = securityService.findUser(username);

		if (user == null) {
			throw new UsernameNotFoundException("username not found!");
		}

		return new Passport(user);
	}

}