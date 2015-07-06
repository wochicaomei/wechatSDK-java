package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.VideoCommentDao;
import com.bbeerr.app.service.IVideoCommentService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.VideoComment;
import com.bbeerr.app.db.entity.VideoCommentView;

@Service
public class VideoCommentServiceImpl implements IVideoCommentService {

	@Autowired
	private VideoCommentDao dao;

	@Override
	public Page countVideoComment(JSONObject q) {
		long count = dao.count(VideoComment.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listVideoComment(JSONObject q) {
		return dao.list(VideoCommentView.class, q);
	}

	@Override
	public VideoCommentView createVideoComment(JSONObject q) {
		VideoComment videoComment = new VideoComment();
		BaseUtils.convert(q, videoComment);
		videoComment.setContent(q.getString("content"));
		videoComment.setUserId(AbstractPo.getPassport().getUser().getId());
		dao.save(videoComment);
		return (VideoCommentView) dao.get(VideoCommentView.class, videoComment.getId());
	}

	@Override
	public int deleteVideoComment(int id, JSONObject q) {
		VideoComment videoComment = (VideoComment) dao.get(VideoComment.class, id);
		dao.delete(videoComment);
		return id;
	}

}