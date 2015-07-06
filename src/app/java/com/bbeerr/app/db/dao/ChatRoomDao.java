package com.bbeerr.app.db.dao;

import java.util.Iterator;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class ChatRoomDao extends HibernateDao {

	@Autowired
	public ChatRoomDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public void addRestrictions(Criteria criteria, JSONObject q) {
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

}