package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.VideoCommentView;

public interface IVideoCommentService {

	public Page countVideoComment(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listVideoComment(JSONObject json);

	public VideoCommentView createVideoComment(JSONObject json);

	public int deleteVideoComment(int id, JSONObject json);

}