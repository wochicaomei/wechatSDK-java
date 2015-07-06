package com.bbeerr.wechat.entity;

/**
 * 微信通用接口凭证
 * @author caspar.chen
 * @version 1.0
 */
public class AccessToken {
	
	/**
	 *  获取到的凭证
	 */
	public  static String token =null;
	
	/**
	 *  凭证有效时间，单位：秒
	 */
	private int expiresIn;


	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public AccessToken(String token, int expiresIn) {
		super();
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public AccessToken() {
		super();
	}
}