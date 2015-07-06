package com.bbeerr.wechat.serv.job;


import java.util.Date;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.entity.AccessToken;
import com.bbeerr.wechat.serv.constants.ConstantsService;
import com.bbeerr.wechat.subs.job.WechatSubscribeJob;

public class WechatServiceJob {
	public static Logger log = Logger.getLogger(WechatServiceJob.class);
	
	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 获取jsapi_ticket的接口地址（GET）
	 */
	public final static String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取卡券 api_ticket的接口地址（GET）
	 */
	public final static String API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";

	
	public static void run(){
//		System.out.println("service_access_token running:"+new Date());
			getAccessToken(ConstantsService.APPID,ConstantsService.APPSECRET);
			System.out.println("service_access_token:"+ConstantsService.getAccess_token()+"   "+new Date());
		}
	public static void run2(){
//		System.out.println("service_jsapi_ticket running2:"+new Date());
//		System.out.println("service_api_ticket:"+new Date());
		getJsApiTicket(ConstantsService.getAccess_token());
		System.out.println("service_jsapi_ticket:"+ConstantsService.getJsapi_ticket()+"   "+new Date());
		
		getApiTicket(ConstantsService.getAccess_token());
		System.out.println("service_api_ticket:"+ConstantsService.getApi_ticket()+"   "+new Date());
		}
	
	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				//获取订阅号的access_token
					ConstantsService.setAccess_token( jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				System.out.println("获取token失败 errcode:" + jsonObject.getInt("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	/**
	 * 获取jsapi_ticket的值
	 * 
	 * @param access_token
	 */
	public static void getApiTicket(String access_token) {
		String requestUrl = API_TICKET.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				//获取订阅号的access_token
					ConstantsService.setApi_ticket(jsonObject.getString("ticket"));
			} catch (JSONException e) {
				// 获取token失败
				log.error("获取api_ticket失败 errcode:"
						+ jsonObject.getInt("errcode") + "，errmsg:"
						+ jsonObject.getString("errmsg"));
			}
		}
	}
	
	/**
	 * 获取jsapi_ticket的值
	 * 
	 * @param access_token
	 */
	public static void getJsApiTicket(String access_token) {
		String requestUrl = JSAPI_TICKET.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				//获取订阅号的access_token
					ConstantsService.setJsapi_ticket(jsonObject.getString("ticket"));
			} catch (JSONException e) {
				// 获取token失败
				log.error("获取jsapi_ticket失败 errcode:"
						+ jsonObject.getInt("errcode") + "，errmsg:"
						+ jsonObject.getString("errmsg"));
			}
		}
	}
	public static void main(String [] args ){
//		run();
//		WechatSubscribeJob.run();
		run2();
		WechatSubscribeJob.run2();
	}
}
