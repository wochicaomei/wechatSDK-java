package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.BlogCommentDao;
import com.bbeerr.app.service.IBlogCommentService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.BlogComment;
import com.bbeerr.app.db.entity.BlogCommentView;

@Service
public class BlogCommentServiceImpl implements IBlogCommentService {

	@Autowired
	private BlogCommentDao dao;

	@Override
	public Page countBlogComment(JSONObject q) {
		long count = dao.count(BlogComment.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listBlogComment(JSONObject q) {
		return dao.list(BlogCommentView.class, q);
	}

	@Override
	public BlogCommentView createBlogComment(JSONObject q) {
		BlogComment blogComment = new BlogComment();
		BaseUtils.convert(q, blogComment);
		blogComment.setContent(q.getString("content"));
		blogComment.setUserId(AbstractPo.getPassport().getUser().getId());
		dao.save(blogComment);
		return (BlogCommentView) dao.get(BlogCommentView.class, blogComment.getId());
	}

	@Override
	public int deleteBlogComment(int id, JSONObject q) {
		BlogComment blogComment = (BlogComment) dao.get(BlogComment.class, id);
		dao.delete(blogComment);
		return id;
	}

}