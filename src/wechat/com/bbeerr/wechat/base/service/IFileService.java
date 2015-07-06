package com.bbeerr.wechat.base.service;

import net.sf.json.JSONObject;

public interface IFileService {

	public JSONObject uploadLogo(String filePath, String access_token) throws Exception;
	
	public JSONObject uploadFile(String fileType, String filename, String filePath, String access_token);
	
	public String downloadFile(String mediaId, String access_token);
	
}
