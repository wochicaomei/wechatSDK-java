package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.UserVideo;
import com.bbeerr.app.db.entity.UserVideoView;

public interface IUserVideoService {

	public Page countUserVideo(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listUserVideo(JSONObject json);

	public UserVideoView createUserVideo(JSONObject json);

	public int deleteUserVideo(int id, JSONObject json);

	public UserVideo findUserVideo(Integer userId, Integer videoId);

}