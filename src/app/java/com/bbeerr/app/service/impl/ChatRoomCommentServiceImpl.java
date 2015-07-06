package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.ChatRoomCommentDao;
import com.bbeerr.app.service.IChatRoomCommentService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.app.db.entity.ChatRoomComment;
import com.bbeerr.app.db.entity.ChatRoomCommentView;

@Service
public class ChatRoomCommentServiceImpl implements IChatRoomCommentService {

	@Autowired
	private ChatRoomCommentDao dao;

	@Override
	public Page countChatRoomComment(JSONObject q) {
		long count = dao.count(ChatRoomComment.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listChatRoomComment(JSONObject q) {
		return dao.list(ChatRoomCommentView.class, q);
	}

	@Override
	public ChatRoomCommentView createChatRoomComment(JSONObject q) {
		ChatRoomComment chatRoomComment = new ChatRoomComment();
		BaseUtils.convert(q, chatRoomComment);
		chatRoomComment.setContent(q.getString("content"));
		chatRoomComment.setUserId(AbstractPo.getPassport().getUser().getId());
		dao.save(chatRoomComment);
		return (ChatRoomCommentView) dao.get(ChatRoomCommentView.class, chatRoomComment.getId());
	}

}