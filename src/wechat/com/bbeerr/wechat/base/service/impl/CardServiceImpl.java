package com.bbeerr.wechat.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbeerr.wechat.base.service.ICardService;
import com.bbeerr.wechat.base.util.FileUtil;
import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;

@Service
public class CardServiceImpl implements ICardService {
	public static Logger log = Logger.getLogger(CardServiceImpl.class);
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
	 * code解码接口
	 */
	public final static String decrypt_code_url = "https://api.weixin.qq.com/card/code/decrypt?access_token=TOKEN";
	/**
	 * 测试用户白 名单
	 */
	public final static String test_white_url ="https://api.weixin.qq.com/card/testwhitelist/set?access_token=TOKEN";
	
	
	/**创建卡券
	 * @param jsonCard 卡券json数据
	 * @param access_token 
	 * @return
	 */
	public  JSONObject createCard(String jsonCard , String access_token){
		int result = 0;
		JSONObject jsonObject = new JSONObject();
		if (access_token != null) {
			// 拼装创建菜单的url
			String url = create_code_url.replace("ACCESS_TOKEN", access_token);
			// 调用接口创建菜单
			 jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonCard);
			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					System.out.println(jsonObject.toString());
					try {
						FileUtil.writeToTxt("card_id.text", jsonObject.getString("card_id"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result = jsonObject.getInt("errcode");
				}
			}
		}
		return jsonObject;
	}
	
	
	/**调用查询code接口可获取code的有效性（非自定义code），该code对应的用户openid、卡券有效期等信息。
	 * @param request
	 * @return
	 */
	public   JSONObject getCardCode(HttpServletRequest request,String code,String access_token){
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
	public  JSONObject getCardInfo(HttpServletRequest request,String cardId,String access_token){
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
	public  Integer destroyCode(HttpServletRequest request,String code,String card_id, String access_token){
		int result = 0;
		String requestUrl = destroy_code_url.replace("TOKEN", access_token);
		JSONObject post_data = new JSONObject();
		post_data.put("card_id", card_id);
		post_data.put("code", code);
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
	
	/**code解码
	 * @param request
	 * @param encrypy_code
	 * @param access_token
	 * @return code
	 */
	public  String decryptCode(HttpServletRequest request , String encrypt_code , String access_token){
		String code =  null;
		String requestUrl = decrypt_code_url.replace("TOKEN", access_token);
		JSONObject post_data = new JSONObject();
		post_data.put("encrypt_code", encrypt_code);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", post_data.toString());
		if(null != jsonObject){
			if(1 != jsonObject.getInt("errcode")){
				code = jsonObject.getString("code");
			}
			else{
				System.out.println("code解码失败："+jsonObject.getString("errmsg"));
			}
		}
		return code;
	}
	
	/**设置卡券测试用户白名单
	 * @param access_token
	 * @param data 包含openid  username:微信号
	 * @return 0 表示成功 1表示失败
	 */
	public  Integer testWhiteList(String access_token ,  String data){
		int result = 0;
		String requestUrl = test_white_url.replace("TOKEN", access_token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", data);
		System.out.println(jsonObject);
		if(jsonObject.getString("errcode").equals("0")){
			System.out.println("设置测试用户白名单成功");
		}
		else{
			System.out.println("设置测试用户白名单失败");
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String [] args){
		//添加卡券
		List<String> urls = new ArrayList<String>();
		Map<Integer,String> cardList = new HashMap<Integer,String>();
		List<String> cardIdList = new ArrayList<String>();
		String card_10_url=CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_10.json").toString().replace("file:", "");
		String card_20_url=CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_20.json").toString().replace("file:", "");
		String card_50_url=CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_50.json").toString().replace("file:", "");
		String card_charge_url=CardServiceImpl.class.getClassLoader().getResource("wechat_card_charge.json").toString().replace("file:", "");
		String card_watch_url=CardServiceImpl.class.getClassLoader().getResource("wechat_card_watch.json").toString().replace("file:", "");
		urls.add(card_10_url);
		urls.add(card_20_url);
		urls.add(card_50_url);
		urls.add(card_charge_url);
		urls.add(card_watch_url);
		for(int i = 0; i<urls.size();i++){
			cardList.put(i, WeixinUtil.ReadFile(urls.get(i)));
			System.out.println(cardList.get(i));
		}
//		
//		for(int i = 0;i<cardList.size();i++){
//			cardIdList.add(createCard(cardList.get(i) , ConstantsSubscribe.getAccess_token()).toString());
//		}
//		System.out.println(createCard(jsonCard , ConstantsSubscribe.getAccess_token()));
		
		//测试用户白名单
//		String test_white=CardService.class.getClassLoader().getResource("wechat_testwhite.json").toString().replace("file:/", "");
//		String jsonUser= WeixinUtil.ReadFile(test_white);
//		testWhiteList(ConstantsSubscribe.getAccess_token(),jsonUser);
	}
}
