package com.bbeerr.wechat.base.service;

import com.bbeerr.wechat.entity.AccessTokenOAuth;
import com.bbeerr.wechat.entity.user.UserWeiXin;

public interface IOAuthService {

	public String getOauthUrl(String appid, String redirectUrl, String charset, String scope);
	
	public AccessTokenOAuth getOAuthAccessToken(String appid, String appsecret, String code);
	
	public UserWeiXin getUserInfoOauth(String token, String openid);
	
	
}
