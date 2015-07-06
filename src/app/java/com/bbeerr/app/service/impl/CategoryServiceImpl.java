package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.CategoryDao;
import com.bbeerr.app.service.ICategoryService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.app.db.entity.Category;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryDao dao;

	@Override
	public Page countCategory(JSONObject q) {
		long count = dao.count(Category.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listCategory(JSONObject q) {
		return dao.list(Category.class, q);
	}

	@Override
	public Category createCategory(JSONObject q) {
		Category category = new Category();
		BaseUtils.convert(q, category);
		category.setName(q.getString("name"));
		category.setIsShow(1);
		dao.save(category);
		return category;
	}

	@Override
	public Category updateCategory(int id, JSONObject q) {
		q = BaseUtils.filterFields(q, null, new String[] { "id" });
		Category category = (Category) dao.get(Category.class, id);
		BaseUtils.convert(q, category);
		if (q.containsKey("name")) {
			category.setName(q.getString("name"));
		}
		dao.update(category);
		return category;
	}

	@Override
	public int deleteCategory(int id, JSONObject q) {
		Category category = (Category) dao.get(Category.class, id);
		dao.delete(category);
		return id;
	}

	@Override
	public Category findCategory(int id) {
		return (Category) dao.get(Category.class, id);
	}

	@Override
	public int changeCategory(JSONObject json) throws Exception {
		Integer id1 = json.getInt("id1");
		Integer id2 = json.getInt("id2");
		Category rd1 = (Category) dao.get(Category.class, id1);
		Category rd2 = (Category) dao.get(Category.class, id2);
		String seq1 = rd1.getSeq();
		String seq2 = rd2.getSeq();
		rd1.setSeq(seq2);
		rd2.setSeq(seq1);
		dao.update(rd1);
		dao.update(rd2);
		return 1;
	}

}