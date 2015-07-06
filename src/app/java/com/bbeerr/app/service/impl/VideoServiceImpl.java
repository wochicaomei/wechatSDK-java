package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.VideoDao;
import com.bbeerr.app.service.IVideoService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.app.db.entity.Video;

@Service
public class VideoServiceImpl implements IVideoService {

	@Autowired
	private VideoDao dao;

	@Override
	public Page countVideo(JSONObject q) {
		long count = dao.count(Video.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listVideo(JSONObject q) {
		return dao.list(Video.class, q);
	}

	@Override
	public Video createVideo(JSONObject q) {
		Video video = new Video();
		BaseUtils.convert(q, video);
		video.setName(q.getString("name"));
		video.setIsShow(1);
		video.setNum(0);
		dao.save(video);
		return video;
	}

	@Override
	public Video updateVideo(int id, JSONObject q) {
		q = BaseUtils.filterFields(q, null, new String[] { "id" });
		Video video = (Video) dao.get(Video.class, id);
		BaseUtils.convert(q, video);
		if (q.containsKey("name")) {
			video.setName(q.getString("name"));
		}
		dao.update(video);
		return video;
	}

	@Override
	public int deleteVideo(int id, JSONObject q) {
		Video video = (Video) dao.get(Video.class, id);
		dao.delete(video);
		return id;
	}

	@Override
	public Video findVideo(int id) {
		return (Video) dao.get(Video.class, id);
	}

	@Override
	public int changeVideo(JSONObject json) throws Exception {
		Integer id1 = json.getInt("id1");
		Integer id2 = json.getInt("id2");
		Video rd1 = (Video) dao.get(Video.class, id1);
		Video rd2 = (Video) dao.get(Video.class, id2);
		String seq1 = rd1.getSeq();
		String seq2 = rd2.getSeq();
		rd1.setSeq(seq2);
		rd2.setSeq(seq1);
		dao.update(rd1);
		dao.update(rd2);
		return 1;
	}

}