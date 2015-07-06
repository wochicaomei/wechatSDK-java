package com.bbeerr.app.db.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class BlogCommentDao extends HibernateDao {

	@Autowired
	public BlogCommentDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}