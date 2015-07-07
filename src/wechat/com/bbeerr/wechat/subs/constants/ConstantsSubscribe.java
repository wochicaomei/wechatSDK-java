package com.bbeerr.wechat.subs.constants;

import com.bbeerr.wechat.base.util.ConfigUtil;

/**
 * 添米微信测试- 订阅号 常量
 * 
 * @author Administrator
 * 
 */
public class ConstantsSubscribe {

	private static Object lock = new Object();
	/**
	 * 与接口配置信息中的Token要一致
	 */
	public static String TOKEN = ConfigUtil.get("subscribe.token");

	/**
	 * 第三方用户唯一凭证
	 */
	public static String APPID = ConfigUtil.get("subscribe.appId");

	/**
	 * 第三方用户唯一凭证密钥
	 */
	public static String APPSECRET = ConfigUtil.get("subscribe.appSecret");

	private static String ACCESS_TOKEN = "";
	private static String JSAPI_TICKET = "";
	private static String API_TICKET = "";

	public static String getAccess_token() {
		synchronized (lock) {

		}
		return ACCESS_TOKEN;
	}

	public static void setAccess_token(String access_token) {
		synchronized (lock) {
			ConstantsSubscribe.ACCESS_TOKEN = access_token;
		}
	}

	public static String getJsapi_ticket() {
		 synchronized (lock) {
		
		 }
		return JSAPI_TICKET;
	}

	public static void setJsapi_ticket(String jsapi_ticket) {
		 synchronized (lock) {
		ConstantsSubscribe.JSAPI_TICKET = jsapi_ticket;
		 }
	}

	public static String getApi_ticket() {
		 synchronized (lock) {

		 }
		return API_TICKET;
	}

	public static void setApi_ticket(String api_ticket) {
		 synchronized (lock) {
		ConstantsSubscribe.API_TICKET = api_ticket;
		 }
	}

	public static void main(String[] args) {
		System.out.println(getAccess_token());
	}
}