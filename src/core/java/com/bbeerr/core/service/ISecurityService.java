package com.bbeerr.core.service;

import com.bbeerr.core.db.entity.User;

public interface ISecurityService {

	public User findUser(String username);

}