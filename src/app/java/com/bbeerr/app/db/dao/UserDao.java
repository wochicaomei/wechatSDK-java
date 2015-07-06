package com.bbeerr.app.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.core.db.dao.HibernateDao;
import com.bbeerr.core.db.entity.User;

@Repository
public class UserDao extends HibernateDao {

	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public User findUserByUsername(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}

	public User findUserByNickname(String nickname) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("nickname", nickname));
		return (User) criteria.uniqueResult();
	}

	public User findUserByUsercode(String usercode) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("usercode", usercode));
		return (User) criteria.uniqueResult();
	}

}