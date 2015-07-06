package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.Video;

public interface IVideoService {

	public Page countVideo(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listVideo(JSONObject json);

	public Video createVideo(JSONObject json);

	public Video updateVideo(int id, JSONObject json);

	public int deleteVideo(int id, JSONObject json);

	public Video findVideo(int id);

	public int changeVideo(JSONObject json) throws Exception;

}