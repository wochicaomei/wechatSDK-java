package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.Blog;

public interface IBlogService {

	public Page countBlog(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listBlog(JSONObject json);

	public Blog createBlog(JSONObject json);

	public Blog updateBlog(int id, JSONObject json);

	public int deleteBlog(int id, JSONObject json);

	public Blog findBlog(int id);

}