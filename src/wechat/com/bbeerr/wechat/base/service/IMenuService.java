package com.bbeerr.wechat.base.service;

import net.sf.json.JSONObject;

import com.bbeerr.wechat.entity.menu.Menu;

public interface IMenuService {

	public Integer createMenu(String jsonMenu, String access_token);
	
	public Integer createMenu(Menu menu, String access_token);
	
	public JSONObject getMenuJson(String access_token);
	
	public Menu getMenu(String access_token);
}
