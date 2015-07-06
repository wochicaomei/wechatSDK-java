package com.bbeerr.wechat.base.service;

import java.util.List;

import com.bbeerr.wechat.entity.user.UserWeiXin;

public interface IUserService {

	public UserWeiXin getUnsubCribeUserInfo(String openid,String access_token);
	
	public UserWeiXin getUserInfo(String openid ,String access_token);
	
	public List<String> getUserOpenIdList(String access_token);
	
	public List<UserWeiXin> getUserList(String access_token);
}
