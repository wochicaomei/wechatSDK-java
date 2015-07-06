package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.UserFans;
import com.bbeerr.app.db.entity.UserFansView;

public interface IUserFansService {

	public Page countUserFans(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listUserFans(JSONObject json);

	public UserFansView createUserFans(JSONObject json);

	public int deleteUserFans(int id, JSONObject json);

	public UserFans findUserFans(Integer userId, Integer fansId);

}