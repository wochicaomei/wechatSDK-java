package com.bbeerr.wechat.base.service;

import javax.servlet.http.HttpServletRequest;

public interface ISignService {

	public boolean checkSignature(String token, HttpServletRequest request);

	public boolean checkSignature(String token, String signature, String timestamp, String nonce);
}
