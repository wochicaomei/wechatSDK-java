package com.bbeerr.wechat.base.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbeerr.wechat.base.service.IFileService;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;

/**
 * 文件上传下载
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
@Service
public class FileServiceImpl implements IFileService{

	public static Logger log = Logger.getLogger(FileServiceImpl.class);
	/**
	 * 上传logo_url
	 */
	public final static String logoUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=TOKEN";

	/**
	 * 上传文件URL
	 */
	private static String uploadFileUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 下载文件URL
	 */
	private static String dwonloadFileURL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 
	 * 向微信服务器上传文件
	 * 
	 * 
	 * 
	 * @param accessToken
	 * 
	 *            进入的接口
	 * 
	 * @param type
	 *            文件类型(语音或者是图片)(对于文档不适合)
	 * @param url
	 *            文件的存储路径
	 * @return json的格式{"media_id":
	 *         "nrSKG2eY1E_svLs0Iv2Vvh46PleKk55a47cNO1ZS5_pdiNiSXuijbCmWWc8unzBQ"
	 *         ,"created_at":1408436207,"type":"image"}
	 */
	public  JSONObject uploadLogo(String filePath, String access_token) throws Exception {
		// 上传文件请求路径
		String action = logoUrl.replace("TOKEN", access_token);
		URL url = new URL(action);
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("上传的文件不存在");
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.fromObject(result);
		return jsonObj;
	}

	/**
	 * 上传多媒体文件
	 * 
	 * @param fileType
	 *            文件类型,分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filename
	 *            文件名称
	 * @param filePath
	 *            文件路径
	 * @return json
	 */
	public  JSONObject uploadFile(String fileType, String filename, String filePath, String access_token) {

		String requestUrl = uploadFileUrl.replace("ACCESS_TOKEN", access_token).replace("TYPE", fileType);
		File file = new File(filePath);
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		URL submit = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + file + "\";filename=\"" + filename + ";filelength=\"" + filePath + ";" + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			dos.close();
			is.close();
		} catch (MalformedURLException e) {
			log.error("File upload fail..." + e);
		} catch (IOException e) {
			log.error("File upload fail..." + e);
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @param mediaId
	 * @return 媒体url地址
	 */
	public  String downloadFile(String mediaId, String access_token) {
		return dwonloadFileURL.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", mediaId);
	}

	public static void main(String[] args) {
		
		// 上传卡券logo到微信服务器，返回url
//		JSONObject jsonObject = new JSONObject();
//		try {
//			jsonObject = uploadLogo("C:\\Users\\Administrator\\Documents\\tm_web\\WebContent\\static\\css\\topper\\login.bmp", ConstantsSubscribe.getAccess_token());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(jsonObject.toString());
		// 订阅号卡券logo url
		// ：http://mmbiz.qpic.cn/mmbiz/ZLzqcHwH9WrhEj8NfdYwKDZ0PJeLM1Yqe97P9BrmtCkIssqyyicVMFKb0fgQTSV7f1dCtjNLIpIibFIDSSsKzl6w/0
	}
}
