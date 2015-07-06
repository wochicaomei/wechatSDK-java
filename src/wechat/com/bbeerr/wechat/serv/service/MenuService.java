package com.bbeerr.wechat.serv.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.bbeerr.wechat.entity.menu.Button;
import com.bbeerr.wechat.entity.menu.Menu;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;
import com.bbeerr.wechat.subs.controller.WechatSubcribeController;
import com.bbeerr.wechat.util.WeixinUtil;

/**
 * 菜单创建
 * 
 * @author caspar.chen
 * @version 1.1
 * 
 */
public class MenuService {

	public static Logger log = Logger.getLogger(MenuService.class);

	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 菜单查询
	 */
	public static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param jsonMenu
	 *            json格式
	 * @return 状态 0 表示成功、其他表示失败
	 */
	public static Integer createMenu(String jsonMenu,String access_token) {
		int result = 0;
		;
		if (access_token != null) {
			// 拼装创建菜单的url
			String url = MENU_CREATE.replace("ACCESS_TOKEN", access_token);
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonMenu);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					log.error("创建菜单失败 errcode:" + jsonObject.getInt("errcode")
							+ "，errmsg:" + jsonObject.getString("errmsg"));
				}
			}
		}
		return result;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @return 0表示成功，其他值表示失败
	 */
	public static Integer createMenu(Menu menu,String access_token) {
		return createMenu(JSONObject.fromObject(menu).toString(),access_token);
	}


	/**
	 * 查询菜单
	 * 
	 * @return 菜单结构json字符串
	 */
	public static JSONObject getMenuJson(String access_token) {
		JSONObject result = null;
		if (access_token != null) {
			String url = MENU_GET.replace("ACCESS_TOKEN", access_token);
			result = WeixinUtil.httpsRequest(url, "GET", null);
		}
		return result;
	}

	/**
	 * 查询菜单
	 * @return Menu 菜单对象
	 */
	public static Menu getMenu(String access_token) {
		JSONObject json = getMenuJson(access_token).getJSONObject("menu");
		System.out.println(json);
		Menu menu = (Menu) JSONObject.toBean(json, Menu.class);
		return menu;
	}

	public static void main(String[] args) {
		String menu_url=WechatSubcribeController.class.getClassLoader().getResource("wechat_draw_menu.json").toString().replace("file:", "");
		System.out.println(menu_url);
		String jsonMenu = WeixinUtil.ReadFile(menu_url);
		System.out.println("menu:"+jsonMenu);
		int result = MenuService.createMenu(jsonMenu,ConstantsSubscribe.getAccess_token());
		System.out.println(result == 0?"菜单创建成功" : "菜单创建失败");
	}
}
