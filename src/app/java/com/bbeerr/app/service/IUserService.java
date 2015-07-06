package com.bbeerr.app.service;

import com.bbeerr.core.db.entity.User;

public interface IUserService {

	public User findUserByUsername(String username);

	public User findUserByNickname(String nickname);

	public User findUserByUsercode(String usercode);

	public User register(User user);

}