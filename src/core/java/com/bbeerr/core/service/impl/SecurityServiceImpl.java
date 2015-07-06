package com.bbeerr.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.core.db.dao.BaseDao;
import com.bbeerr.core.db.entity.User;
import com.bbeerr.core.service.ISecurityService;

@Service("securityService")
public class SecurityServiceImpl implements ISecurityService {

	@Autowired
	private BaseDao dao;

	@Override
	public User findUser(String username) {
		return dao.findUser(username);
	}

}