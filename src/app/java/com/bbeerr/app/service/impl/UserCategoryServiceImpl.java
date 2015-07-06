package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.UserCategoryDao;
import com.bbeerr.app.service.IUserCategoryService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.UserCategory;
import com.bbeerr.app.db.entity.UserCategoryView;

@Service
public class UserCategoryServiceImpl implements IUserCategoryService {

	@Autowired
	private UserCategoryDao dao;

	@Override
	public Page countUserCategory(JSONObject q) {
		long count = dao.count(UserCategory.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listUserCategory(JSONObject q) {
		return dao.list(UserCategoryView.class, q);
	}

	@Override
	public UserCategoryView createUserCategory(JSONObject q) {
		UserCategory userCategory = new UserCategory();
		BaseUtils.convert(q, userCategory);

		userCategory.setUserId(AbstractPo.getPassport().getUser().getId());
		if (dao.findUserCategory(userCategory.getUserId(), userCategory.getCategoryId()) != null) {
			return null;
		}

		dao.save(userCategory);
		return (UserCategoryView) dao.get(UserCategoryView.class, userCategory.getId());
	}

	@Override
	public int deleteUserCategory(int id, JSONObject q) {
		UserCategory userCategory = (UserCategory) dao.get(UserCategory.class, id);
		dao.delete(userCategory);
		return id;
	}

	@Override
	public UserCategory findUserCategory(Integer userId, Integer categoryId) {
		return dao.findUserCategory(userId, categoryId);
	}

}