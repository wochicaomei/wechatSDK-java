package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.UserCategory;
import com.bbeerr.app.db.entity.UserCategoryView;

public interface IUserCategoryService {

	public Page countUserCategory(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listUserCategory(JSONObject json);

	public UserCategoryView createUserCategory(JSONObject json);

	public int deleteUserCategory(int id, JSONObject json);

	public UserCategory findUserCategory(Integer userId, Integer categoryId);

}