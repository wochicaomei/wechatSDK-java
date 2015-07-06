package com.bbeerr.wechat.base.service;

public interface IQRcodeService {

	public String getTicket(String actionName, int sceneId ,String access_token);
	
	public String getQrCodeImgURL(String ticket);
	
	
}
