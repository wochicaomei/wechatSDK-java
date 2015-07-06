package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.BlogCommentView;

public interface IBlogCommentService {

	public Page countBlogComment(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listBlogComment(JSONObject json);

	public BlogCommentView createBlogComment(JSONObject json);

	public int deleteBlogComment(int id, JSONObject json);

}