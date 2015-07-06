package com.bbeerr.wechat.base.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.bbeerr.wechat.entity.user.UserWeiXin;

public class FileUtil {
	
	public static void  writeToTxt(String filePath, String cardid) throws Exception{
		//下面是写文件
		boolean flag = false;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(filePath, true);
			bw = new BufferedWriter(fw, 100);
				bw.write("card_id"+cardid);
			
			flag = true;
		} catch (IOException e) {
			System.out.println("写入文件出错");
			flag = false;
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			if (fw != null)
				fw.close();
		}
	}
	
}
