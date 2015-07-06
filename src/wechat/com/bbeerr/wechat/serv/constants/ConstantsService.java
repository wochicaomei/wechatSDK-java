package com.bbeerr.wechat.serv.constants;

import com.bbeerr.wechat.util.ConfigUtil;

/**
 * 添米微信 服务测试号 常量
 * 
 * @author Administrator
 * 
 */
public class ConstantsService {
	
	private static Object lock = new Object();

	/**
	 * 与接口配置信息中的Token要一致
	 */
	public final static String TOKEN = ConfigUtil.get("service.token");
	/**
	 * 第三方用户唯一凭证
	 */
	public final static String APPID = ConfigUtil.get("service.appId");
	/**
	 * 第三方用户唯一凭证密钥
	 */
	public final static String APPSECRET = ConfigUtil.get("service.appSecret");

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
			ConstantsService.ACCESS_TOKEN = access_token;
		}
	}

	public static String getJsapi_ticket() {
		 synchronized (lock) {
		
		 }
		return JSAPI_TICKET;
	}

	public static void setJsapi_ticket(String jsapi_ticket) {
		 synchronized (lock) {
		ConstantsService.JSAPI_TICKET = jsapi_ticket;
		 }
	}

	public static String getApi_ticket() {
		 synchronized (lock) {

		 }
		return API_TICKET;
	}

	public static void setApi_ticket(String api_ticket) {
		 synchronized (lock) {
		ConstantsService.API_TICKET = api_ticket;
		 }
	}

	public static void main(String[] args) {
		System.out.println(getJsapi_ticket());
		
	}

}
