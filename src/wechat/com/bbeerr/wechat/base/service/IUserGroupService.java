package com.bbeerr.wechat.base.service;

import java.util.List;

import com.bbeerr.wechat.entity.user.UserGroup;

public interface IUserGroupService {

	public String createGroup(String groupName, String access_token);
	
	public List<UserGroup> getGroup(String access_token);
	
	public String getGroupByOpenid(String openid, String access_token);
	
	public boolean updateGroupName(int id, String name, String access_token);
	
	public boolean moveUserToGroup(String openid, int groupId, String access_token);
	
	
}
