package com.bbeerr.wechat.serv.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.bbeerr.wechat.serv.constants.ConstantsService;
import com.bbeerr.wechat.util.WeixinUtil;

public class CardService {
	public static Logger log = Logger.getLogger(CardService.class);
	/**
	 * 创建卡券接口
	 */
	public final static String create_code_url = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
	/**
	 * 查询code接口
	 */
	public final static String code_url = "https://api.weixin.qq.com/card/code/get?access_token=TOKEN";
	
	/**
	 * 查看卡券详情
	 */
	public final static String code_detail_url = "https://api.weixin.qq.com/card/get?access_token=TOKEN";
	
	/**
	 * 批量查询卡列表
	 */
	public final static String code_list_url = "https://api.weixin.qq.com/card/code/unavailable?access_token=TOKEN";
	
	/**核销卡券接口
	 * 
	 */
	public final static String destroy_code_url = "https://api.weixin.qq.com/card/code/unavailable?access_token=TOKEN";
	
	/**
	 * 测试用户白 名单
	 */
	public final static String test_white_url ="https://api.weixin.qq.com/card/testwhitelist/set?access_token=TOKEN";
	
	/**调用查询code接口可获取code的有效性（非自定义code），该code对应的用户openid、卡券有效期等信息。
	 * @param request
	 * @return
	 */
	public static  JSONObject getCardCode(HttpServletRequest request,String code,String access_token){
		int result = 0;
		String requestUrl = code_url.replace("TOKEN", access_token);
		JSONObject post_data = new JSONObject();
		post_data.put("code", code);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", post_data.toString());
		log.debug("code:"+jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("核销卡券 code失败 errcode:" + jsonObject.getInt("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return  jsonObject;
	}
	
	/**通过card_id 获取卡券详情
	 * @param request
	 * @param cardId
	 * @return
	 */
	public static JSONObject getCardInfo(HttpServletRequest request,String cardId,String access_token){
		int result = 0;
		String requestUrl = code_detail_url.replace("TOKEN", access_token);
		JSONObject post_data = new JSONObject();
		post_data.put("card_id", cardId);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", post_data.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("获取卡券信息失败 errcode:" + jsonObject.getInt("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return  jsonObject;
	}
	
	/**通过code 核销卡券
	 * @param request
	 * @param code
	 * return 0 表示核销成功
	 */
	public static Integer destroyCode(HttpServletRequest request,String code,String access_token){
		int result = 0;
		String requestUrl = destroy_code_url.replace("TOKEN", access_token);
		JSONObject post_data = new JSONObject();
		post_data.put("card_id", code);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", post_data.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("核销卡券失败 errcode:" + jsonObject.getInt("errcode")
						+ "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
	
	/**设置卡券测试用户白名单
	 * @param access_token
	 * @param data 包含openid  username:微信号
	 * @return 0 表示成功 1表示失败
	 */
	public static Integer testWhiteList(String access_token ,  JSONObject data){
		int result = 0;
		JSONObject jsonObject = WeixinUtil.httpsRequest(test_white_url, "POST", data.toString());
		if(jsonObject.getString("errcode").equals("0")){
			System.out.println("设置测试用户白名单成功");
		}
		else{
			System.out.println("设置测试用户白名单失败");
		}
		return result;
	}
	
	public static void main(String[] args){
		JSONObject json = new JSONObject();
		json.put("openid", "oBaiot6DDkEhDCcl_8s0CivSBOJs");
		json.put("username", "");
		testWhiteList(ConstantsService.getAccess_token(),json);
	}
}
