package com.bbeerr.core.db.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bbeerr.core.db.entity.User;

public class BaseDao extends HibernateDao {

	public User findUser(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}

}