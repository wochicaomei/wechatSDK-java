package com.bbeerr.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bbeerr.core.db.entity.User;

public class Passport implements UserDetails {

	private static final long serialVersionUID = -3118315835681279229L;

	private String username;
	private String password;
	private User user;

	private Set<GrantedAuthority> authorities;
	private Set<String> grantedResource;

	public Passport(String username) {
		this.username = username;
	}

	public Passport(User user) {
		this.user = user;
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = new HashSet<GrantedAuthority>();
		this.grantedResource = new HashSet<String>();
		this.authorities.add(new GrantedAuthorityImpl(user.getUsername()));

		String authorities = user.getAuthority();
		if (authorities != null) {
			String[] auths = authorities.split(",");
			for (int i = 0; i < auths.length; i++) {
				this.authorities.add(new GrantedAuthorityImpl(auths[i]));
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public Set<String> getGrantedResource() {
		return grantedResource;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}

}