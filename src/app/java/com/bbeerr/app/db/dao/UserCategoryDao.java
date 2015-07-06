package com.bbeerr.app.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.app.db.entity.UserCategory;
import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class UserCategoryDao extends HibernateDao {

	@Autowired
	public UserCategoryDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public UserCategory findUserCategory(Integer userId, Integer categoryId) {
		Criteria criteria = getSession().createCriteria(UserCategory.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("categoryId", categoryId));
		return (UserCategory) criteria.uniqueResult();
	}

}