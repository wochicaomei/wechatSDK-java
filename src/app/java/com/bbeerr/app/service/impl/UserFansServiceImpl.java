package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.UserFansDao;
import com.bbeerr.app.service.IUserFansService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.UserFans;
import com.bbeerr.app.db.entity.UserFansView;

@Service
public class UserFansServiceImpl implements IUserFansService {

	@Autowired
	private UserFansDao dao;

	@Override
	public Page countUserFans(JSONObject q) {
		long count = dao.count(UserFans.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listUserFans(JSONObject q) {
		return dao.list(UserFansView.class, q);
	}

	@Override
	public UserFansView createUserFans(JSONObject q) {
		UserFans userFans = new UserFans();
		BaseUtils.convert(q, userFans);

		userFans.setUserId(AbstractPo.getPassport().getUser().getId());
		if (dao.findUserFans(userFans.getUserId(), userFans.getFansId()) != null) {
			return null;
		}

		dao.save(userFans);
		return (UserFansView) dao.get(UserFansView.class, userFans.getId());
	}

	@Override
	public int deleteUserFans(int id, JSONObject q) {
		UserFans userFans = (UserFans) dao.get(UserFans.class, id);
		dao.delete(userFans);
		return id;
	}

	@Override
	public UserFans findUserFans(Integer userId, Integer fansId) {
		return dao.findUserFans(userId, fansId);
	}

}