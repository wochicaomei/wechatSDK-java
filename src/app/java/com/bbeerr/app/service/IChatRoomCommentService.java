package com.bbeerr.app.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.bbeerr.base.Page;
import com.bbeerr.app.db.entity.ChatRoomCommentView;

public interface IChatRoomCommentService {

	public Page countChatRoomComment(JSONObject json);

	@SuppressWarnings("rawtypes")
	public List listChatRoomComment(JSONObject json);

	public ChatRoomCommentView createChatRoomComment(JSONObject json);

}