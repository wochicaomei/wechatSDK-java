package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.Category;

public interface ICategoryService {

	public Page countCategory(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listCategory(JSONObject json);

	public Category createCategory(JSONObject json);

	public Category updateCategory(int id, JSONObject json);

	public int deleteCategory(int id, JSONObject json);

	public Category findCategory(int id);

	public int changeCategory(JSONObject json) throws Exception;

}