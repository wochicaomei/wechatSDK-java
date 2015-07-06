package com.bbeerr.core.controller;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.bbeerr.base.util.ServerConfig;

@Controller
public class UploadController {

	private final static int MAXFILESIZE = 1024 * 1024 * 1024;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(ModelMap model, DefaultMultipartHttpServletRequest request, HttpServletResponse response) {
		String upid = request.getParameter("upid");
		String catalog = request.getParameter("catalog");
		String uploadDir = ServerConfig.getProperty("upload.dir") + File.separator + catalog;
		String fileName = uploadDir + File.separator + upid;

		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		MultipartFile file = request.getFile("Filedata");
		if (file.getSize() > MAXFILESIZE) {
			return;
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(fileName);

			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/download/{catalog}/{file}", method = RequestMethod.GET)
	public void pic(@PathVariable("file") String file, @PathVariable("catalog") String catalog, HttpServletRequest request, HttpServletResponse response) {
		String uploadDir = ServerConfig.getProperty("upload.dir");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(uploadDir + File.separator + catalog + File.separator + file);
			byte[] b = new byte[fis.available()];
			fis.read(b);

			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/download/{catalog}/{date}/{file}", method = RequestMethod.GET)
	public void picture(@PathVariable("catalog") String catalog, @PathVariable("date") String date, @PathVariable("file") String file, HttpServletRequest request, HttpServletResponse response) {
		String uploadDir = ServerConfig.getProperty("upload.dir");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(uploadDir + File.separator + catalog + File.separator + date + File.separator + file);
			byte[] b = new byte[fis.available()];
			fis.read(b);

			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}