package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.UserVideoDao;
import com.bbeerr.app.service.IUserVideoService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.UserVideo;
import com.bbeerr.app.db.entity.UserVideoView;

@Service
public class UserVideoServiceImpl implements IUserVideoService {

	@Autowired
	private UserVideoDao dao;

	@Override
	public Page countUserVideo(JSONObject q) {
		long count = dao.count(UserVideo.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listUserVideo(JSONObject q) {
		return dao.list(UserVideoView.class, q);
	}

	@Override
	public UserVideoView createUserVideo(JSONObject q) {
		UserVideo userVideo = new UserVideo();
		BaseUtils.convert(q, userVideo);

		userVideo.setUserId(AbstractPo.getPassport().getUser().getId());
		if (dao.findUserVideo(userVideo.getUserId(), userVideo.getVideoId()) != null) {
			return null;
		}

		dao.save(userVideo);
		return (UserVideoView) dao.get(UserVideoView.class, userVideo.getId());
	}

	@Override
	public int deleteUserVideo(int id, JSONObject q) {
		UserVideo userVideo = (UserVideo) dao.get(UserVideo.class, id);
		dao.delete(userVideo);
		return id;
	}

	@Override
	public UserVideo findUserVideo(Integer userId, Integer videoId) {
		return dao.findUserVideo(userId, videoId);
	}

}