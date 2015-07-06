package com.bbeerr.app.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.app.db.entity.Blog;
import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class BlogDao extends HibernateDao {

	@Autowired
	public BlogDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Blog findBlog(Integer userId, String blogNo) {
		Criteria criteria = getSession().createCriteria(Blog.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("blogNo", blogNo));
		return (Blog) criteria.uniqueResult();
	}

}