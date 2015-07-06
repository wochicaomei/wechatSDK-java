package com.bbeerr.app.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.BlogDao;
import com.bbeerr.app.service.IBlogService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.base.util.TextFormatter;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.Blog;

@Service
public class BlogServiceImpl implements IBlogService {

	@Autowired
	private BlogDao dao;

	@Override
	public Page countBlog(JSONObject q) {
		long count = dao.count(Blog.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listBlog(JSONObject q) {
		return dao.list(Blog.class, q);
	}

	@Override
	public Blog createBlog(JSONObject q) {
		Blog blog = new Blog();
		BaseUtils.convert(q, blog);

		blog.setTitle(q.getString("title"));
		blog.setContent(q.getString("content"));
		blog.setNum(0);
		blog.setUserId(AbstractPo.getPassport().getUser().getId());

		String now_string = TextFormatter.formatDate(new Date(), "yyyyMMdd");
		String blogNo = createBlogNo(blog.getUserId(), now_string, 1);
		blog.setBlogNo(blogNo);

		dao.save(blog);
		return blog;
	}

	@Override
	public Blog updateBlog(int id, JSONObject q) {
		q = BaseUtils.filterFields(q, null, new String[] { "id" });
		Blog blog = (Blog) dao.get(Blog.class, id);
		BaseUtils.convert(q, blog);
		if (q.containsKey("title")) {
			blog.setTitle(q.getString("title"));
		}
		if (q.containsKey("content")) {
			blog.setContent(q.getString("content"));
		}
		dao.update(blog);
		return blog;
	}

	@Override
	public int deleteBlog(int id, JSONObject q) {
		Blog blog = (Blog) dao.get(Blog.class, id);
		dao.delete(blog);
		return id;
	}

	@Override
	public Blog findBlog(int id) {
		return (Blog) dao.get(Blog.class, id);
	}

	// 产生用户每篇博客的时间戳(唯一)
	private String createBlogNo(Integer userId, String now_string, Integer i) {
		if (dao.findBlog(userId, now_string + i.toString()) == null) {
			return now_string + i.toString();
		} else {
			return createBlogNo(userId, now_string, i + 1);
		}
	}

}