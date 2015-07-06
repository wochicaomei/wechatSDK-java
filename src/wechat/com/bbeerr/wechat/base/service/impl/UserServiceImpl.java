package com.bbeerr.wechat.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbeerr.wechat.base.service.IUserService;
import com.bbeerr.wechat.base.util.DateFormart;
import com.bbeerr.wechat.base.util.StringUtil;
import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.entity.user.UserWeiXin;
import com.bbeerr.wechat.serv.constants.ConstantsService;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;
/**
 * 用户管理
 * @author caspar.chen
 * @version 1.0
 * 
 */
@Service
public class UserServiceImpl implements IUserService{

	public static Logger log = Logger.getLogger(UserServiceImpl.class);

	/**
	 * 获取用户详细信息
	 */
	public static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取用户openid列表
	 */
	public static String GET_USER_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	public UserWeiXin getUnsubCribeUserInfo(String openid,String access_token){
		UserWeiXin user = null;
		if (access_token != null) {
			String url = GET_USER_INFO.replace("ACCESS_TOKEN", access_token).replace(
					"OPENID", openid);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
					user.setOpenid(jsonObject.getString("openid"));
					user.setUnionid(jsonObject.getString("unionid"));
				}
			}

		}
		return user;
	}
	/**
	 * 获取用户详细信息
	 * 
	 * @param openid
	 * @return UserWeiXin 用户详细信息
	 */
	public UserWeiXin getUserInfo(String openid ,String access_token) {
		UserWeiXin user = null;
		if (access_token != null) {
			String url = GET_USER_INFO.replace("ACCESS_TOKEN", access_token).replace(
					"OPENID", openid);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
					user.setSubscribe(jsonObject.getInt("subscribe"));
					user.setOpenid(jsonObject.getString("openid"));
					user.setNickname(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setProvince(jsonObject.getString("province"));
					user.setLanguage(jsonObject.getString("language"));
					user.setHeadimgurl(jsonObject.getString("headimgurl"));
					long subscibeTime = jsonObject.getLong("subscribe_time");
					Date st = DateFormart
							.paserYYYY_MM_DD_HHMMSSToDate(subscibeTime);
					user.setSubscribe_time(st);
					user.setUnionid(jsonObject.getString("unionid"));
				}
			}

		}
		return user;
	}

	/**
	 * 获取关注者OpenID列表
	 * 
	 * @return List<String> 关注者openID列表
	 */
	public List<String> getUserOpenIdList(String access_token) {
		List<String> list = null;
		if (access_token != null) {
			String url = GET_USER_OPENID_LIST.replace("ACCESS_TOKEN", access_token)
					.replace("NEXT_OPENID", "");

			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode"))
						&& jsonObject.get("errcode") != "0") {
					log.error("获取关注用户列表失败 errcode:"
							+ jsonObject.getInt("errcode") + "，errmsg:"
							+ jsonObject.getString("errmsg"));
				} else {
					list = new ArrayList<String>();
					JSONObject data = jsonObject.getJSONObject("data");
					String openidStr = data.getString("openid");
					openidStr = openidStr.substring(1, openidStr.length() - 1);
					openidStr = openidStr.replace("\"", "");
					String openidArr[] = openidStr.split(",");
					for (int i = 0; i < openidArr.length; i++) {
						list.add(openidArr[i]);
					}
				}
			}

		}
		return list;
	}

	/**
	 * 获取关注者列表
	 * 
	 * @return List<UserWeiXin> 关注者列表信息
	 */
	public List<UserWeiXin> getUserList(String access_token) {
		List<UserWeiXin> list = new ArrayList<UserWeiXin>();

		// 获取关注用户openid列表
		List<String> listStr = getUserOpenIdList(access_token);

		if (listStr == null || listStr.size() == 0) {
			return null;
		}
		for (int i = 0; i < listStr.size(); i++) {
			// 根据openid查询用户信息
			UserWeiXin user = getUserInfo(listStr.get(i),access_token);
			if (user != null) {
				list.add(user);
			}
		}
		return list;
	}
	
	
	public static void main(String[] args){
		/*for(UserWeiXin user:getUserList(ConstantsSubscribe.getAccess_token())){
			System.out.println("openid:"+user.getOpenid()+"  unionid:"+user.getUnionid()+"  nickname:"+user.getNickname());
	}*/
	}
}
