package com.bbeerr.wechat.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbeerr.wechat.base.service.ICustomService;
import com.bbeerr.wechat.base.util.StringUtil;
import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.constants.ConstantsWeChat;
import com.bbeerr.wechat.entity.customer.CustomerBaseMessage;
import com.bbeerr.wechat.entity.customer.MediaMessage;
import com.bbeerr.wechat.entity.customer.MusicMessage;
import com.bbeerr.wechat.entity.customer.NewsMessage;
import com.bbeerr.wechat.entity.customer.TextMessage;
import com.bbeerr.wechat.entity.customer.VideoMessage;

/**
 * 发送客服消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
@Service
public class CustomServiceImpl implements ICustomService{

	public static Logger log = Logger.getLogger(CustomServiceImpl.class);

	private static String CUSTOME_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public static Map<String, CustomerBaseMessage> bulidMessageMap = new HashMap<String, CustomerBaseMessage>();
	
	static {
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
	}
	
	/**
	 * 发送客服消息
	 * @param obj	消息对象
	 * @return 是否发送成功
	 */
	public  boolean sendCustomerMessage(Object obj ,String access_token) {
		boolean bo = false;
		String url = CUSTOME_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject json = JSONObject.fromObject(obj);
		System.out.println(json);
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", json.toString());
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
					&& jsonObject.getString("errcode").equals("0")) {
				bo = true;
			}
		}
		return bo;
	}

	
	/**
	 * 构建基本消息
	 * 
	 * @param toUser
	 *            接受者用户openId
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public  Object bulidCustomerBaseMessage(String toUser, String msgType) {
		CustomerBaseMessage message = bulidMessageMap.get(msgType);
		message.setTouser(toUser);
		message.setMsgtype(msgType);
		return message;
	}

}
