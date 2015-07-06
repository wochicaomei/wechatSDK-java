package com.bbeerr.wechat.base.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public interface ICardService {

	public JSONObject createCard(String jsonCard , String access_token);
	
	public JSONObject getCardCode(HttpServletRequest request,String code,String access_token);
	
	public JSONObject getCardInfo(HttpServletRequest request,String cardId,String access_token);
	
	public Integer destroyCode(HttpServletRequest request,String code,String card_id, String access_token);
	
	public String decrypt_code(HttpServletRequest request , String encrypt_code , String access_token);
	
	public Integer testWhiteList(String access_token ,  String data);
	
}
