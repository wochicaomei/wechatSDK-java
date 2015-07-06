package com.bbeerr.app.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbeerr.app.db.dao.ChatRoomDao;
import com.bbeerr.app.db.entity.ChatRoom;
import com.bbeerr.app.service.IChatRoomService;
import com.bbeerr.base.Constants;
import com.bbeerr.base.Page;
import com.bbeerr.base.util.BaseUtils;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {

	@Autowired
	private ChatRoomDao dao;

	@Override
	public Page countChatRoom(JSONObject q) {
		long count = dao.count(ChatRoom.class, q);
		int size = Constants.PAGESIZE_DEFAULT;
		if (q.containsKey("pagesize")) {
			size = q.getInt("pagesize");
		}
		Page page = new Page(count, size);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listChatRoom(JSONObject q) {
		return dao.list(ChatRoom.class, q);
	}

	@Override
	public ChatRoom createChatRoom(JSONObject q) {
		ChatRoom chatRoom = new ChatRoom();
		BaseUtils.convert(q, chatRoom);
		chatRoom.setName(q.getString("name"));
		chatRoom.setIsShow(1);
		dao.save(chatRoom);
		return chatRoom;
	}

	@Override
	public ChatRoom updateChatRoom(int id, JSONObject q) {
		q = BaseUtils.filterFields(q, null, new String[] { "id" });
		ChatRoom chatRoom = (ChatRoom) dao.get(ChatRoom.class, id);
		BaseUtils.convert(q, chatRoom);
		if (q.containsKey("name")) {
			chatRoom.setName(q.getString("name"));
		}
		dao.update(chatRoom);
		return chatRoom;
	}

	@Override
	public int deleteChatRoom(int id, JSONObject json) {
		ChatRoom chatRoom = (ChatRoom) dao.get(ChatRoom.class, id);
		dao.delete(chatRoom);
		return id;
	}

	@Override
	public ChatRoom findChatRoom(int id) {
		return (ChatRoom) dao.get(ChatRoom.class, id);
	}

	@Override
	public int changeChatRoom(JSONObject json) throws Exception {
		Integer id1 = json.getInt("id1");
		Integer id2 = json.getInt("id2");
		ChatRoom rd1 = (ChatRoom) dao.get(ChatRoom.class, id1);
		ChatRoom rd2 = (ChatRoom) dao.get(ChatRoom.class, id2);
		String seq1 = rd1.getSeq();
		String seq2 = rd2.getSeq();
		rd1.setSeq(seq2);
		rd2.setSeq(seq1);
		dao.update(rd1);
		dao.update(rd2);
		return 1;
	}

}