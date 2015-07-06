package com.bbeerr.wechat.base.service;

public interface ICustomService {

	public boolean sendCustomerMessage(Object obj ,String access_token);
	
	public Object bulidCustomerBaseMessage(String toUser, String msgType);
	
}
