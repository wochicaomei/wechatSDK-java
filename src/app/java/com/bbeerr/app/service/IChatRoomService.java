package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.ChatRoom;

public interface IChatRoomService {

	public Page countChatRoom(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listChatRoom(JSONObject json);

	public ChatRoom createChatRoom(JSONObject json);

	public ChatRoom updateChatRoom(int id, JSONObject json);

	public int deleteChatRoom(int id, JSONObject json);

	public ChatRoom findChatRoom(int id);

	public int changeChatRoom(JSONObject json) throws Exception;

}