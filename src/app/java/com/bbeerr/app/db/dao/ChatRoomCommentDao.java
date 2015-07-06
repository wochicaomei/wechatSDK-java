package com.bbeerr.app.db.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bbeerr.core.db.dao.HibernateDao;

@Repository
public class ChatRoomCommentDao extends HibernateDao {

	@Autowired
	public ChatRoomCommentDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}