package com.bbeerr.app.service.impl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.UserDao;
import com.bbeerr.app.service.IUserService;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.User;

@Service
public class UserServiceImpl implements IUserService {

	private char[] numberSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@Autowired
	private UserDao dao;

	@Override
	public User findUserByUsername(String username) {
		return dao.findUserByUsername(username);
	}

	@Override
	public User findUserByNickname(String nickname) {
		return dao.findUserByNickname(nickname);
	}

	@Override
	public User findUserByUsercode(String usercode) {
		return dao.findUserByUsercode(usercode);
	}

	@Override
	public User register(User user) {
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setAdmin(false);
		user.setAuthority("MOD_USER");// 普通用户
		user.setPassword(BaseUtils.md5(user.getPassword()));
		user.setCreatedatetime(new Date());
		user.setUpdatedatetime(new Date());
		user.setCreateusername("system");
		user.setUpdateusername("system");
		user.setUsercode(generateRandomCode(8));// 随机8位数(唯一)
		dao.save(user);
		return user;
	}

	// 生成8位的随机code
	private String generateRandomCode(int len) {
		StringBuffer randomCode = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			String strRand = String.valueOf(numberSequence[random.nextInt(10)]);
			randomCode.append(strRand);
		}
		String usercode = randomCode.toString();

		if (dao.findUserByUsercode(usercode) == null) {
			return usercode;
		} else {
			return generateRandomCode(8);
		}
	}

}