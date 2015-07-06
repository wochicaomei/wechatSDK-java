package com.bbeerr.app.db.dao;

import java.util.Iterator;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.app.db.entity.Category;
import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class CategoryDao extends HibernateDao {

	@Autowired
	public CategoryDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	protected void addRestrictions(Criteria criteria, JSONObject q) {
		if (q.containsKey("$criteria")) {
			JSONObject cs = q.getJSONObject("$criteria");
			Iterator<String> it = cs.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object value = cs.get(key);
				if (!value.equals("")) {
					if (key.equals("hot") || key.equals("isShow")) {
						criteria.add(Restrictions.eq(key, Integer.valueOf((String) value)));
					} else if (key.equals("name")) {
						criteria.add(Restrictions.like(key, (String) value, MatchMode.ANYWHERE));
					} else {
						criteria.add(Restrictions.eq(key, value));
					}
				}
			}
		}
	}

	public Category findCategoryByName(String name) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("name", name));
		return (Category) criteria.uniqueResult();
	}

}