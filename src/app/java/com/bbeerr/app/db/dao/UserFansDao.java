package com.bbeerr.app.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.app.db.entity.UserFans;
import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class UserFansDao extends HibernateDao {

	@Autowired
	public UserFansDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public UserFans findUserFans(Integer userId, Integer fansId) {
		Criteria criteria = getSession().createCriteria(UserFans.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("fansId", fansId));
		return (UserFans) criteria.uniqueResult();
	}

}