package com.bbeerr.wechat.base.service;

public interface ICardSignService {

	public String WxCardSign(String api_ticket,String timeStamp,String openid,String code ,String card_id );
}
