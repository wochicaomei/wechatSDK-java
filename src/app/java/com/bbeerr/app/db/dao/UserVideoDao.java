package com.bbeerr.app.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.app.db.entity.UserVideo;
import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class UserVideoDao extends HibernateDao {

	@Autowired
	public UserVideoDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public UserVideo findUserVideo(Integer userId, Integer videoId) {
		Criteria criteria = getSession().createCriteria(UserVideo.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("videoId", videoId));
		return (UserVideo) criteria.uniqueResult();
	}

}