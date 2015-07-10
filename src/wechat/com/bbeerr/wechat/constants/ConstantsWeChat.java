package com.bbeerr.wechat.constants;


/**
 * 微信服务号 添米测试号 常量
 * @author caspar.chen
 * @version 1.0
 */
public class ConstantsWeChat {
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：图片
	 */
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	
	/**
	 * 返回消息类型：语音
	 */
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	
	/**
	 * 返回消息类型：视频
	 */
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	
	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	/**
	 * 返回消息类型：转发至多客服系统
	 */
	public static final String RESP_MESSAGE_TYPE_TRANSFER_CUSTOMER = "transfer_customer_service";
	
	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：事件
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(关注)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消关注)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型：SCAN(二维码扫描事件)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";	
	
	/**
	 * 事件类型：卡券审核事件推送
	 */
	public static final String EVENT_TYPE_CARD_PASS_CHECK = "card_pass_check";
	
	/**
	 * 事件类型：卡券领取事件推送
	 */
	public static final String EVENT_TYPE_USER_GET_CARD = "user_get_card";
	
	/**
	 * 事件类型：用户删除卡券事件推送
	 */
	public static final String EVENT_TYPE_USER_DEL_CARD = "user_del_card";
	
	/**
	 * 事件类型：核销卡券事件推送
	 */
	public static final String EVENT_TYPE_USER_CONSUME_CARD = "user_consume_card";
	
	/**
	 * OAUTH scope
	 */
	public static final String SCOPE_SNSAPI_BASE = "snsapi_base";
	public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";
}
