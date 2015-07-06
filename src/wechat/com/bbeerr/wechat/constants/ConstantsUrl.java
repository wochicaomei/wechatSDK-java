package com.bbeerr.wechat.constants;

/**与tm_web 数据交互相关的url地址
 * @author Administrator
 *
 */
public class ConstantsUrl {

	/**
	 * 微信菜单重定向地址
	 */
	public static String redirect_url = "http://test.91tianmi.com/tm_web/wechat_redirect";
	/**
	 * 微信正式服接收添米头条用户关注取关url
	 */
	public static String offical_user_url = "https://www.91tianmi.com/wechatAttentionGet";
	
	/**
	 * 微信测试服接收添米头条用户关注取关url
	 */
	public static String test_user_url = "http://test.91tianmi.com/tm_web/wechatAttentionGet";
	
	/**
	 * 微信正式服接收用户领取卡券数据post url
	 */
	public static String offical_card_url = "https://www.91tianmi.com/wechatAwardsGet";
	
	/**
	 * 微信测试服接收用户领取卡券数据post url
	 */
	public static String test_card_url = "http://test.91tianmi.com/tm_web/wechatAwardsGet";
	
	public static String wechat_event20150618_url ="http://test.91tianmi.com/tm_web/event20150618"; 
			
	/**
	 *微信号添米头条 
	 */
	public static String wxh_tmtt = "tmtt";
	
	/**
	 *微信号添米财富
	 */
	public static String wxh_tmcf = "tmcf";
	
	/**
	 * 用户关注
	 */
	public static String operation_subcribe = "1";
	
	/**
	 * 用户取消关注
	 */
	public static String operation_unsubcribe = "0";
	
}
