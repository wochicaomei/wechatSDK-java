package com.bbeerr.base.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ImageCompressor {

	private InputStream is;
	private int width;
	private int height;

	public ImageCompressor(InputStream is, int width, int height) { // 初始化变量
		this.is = is;
		this.width = width;
		this.height = height;
	}

	public String compress() throws Exception {
		Image image = null;
		ByteArrayOutputStream bos = null;
		try {
			image = ImageIO.read(is);
			bos = new ByteArrayOutputStream();
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
			ImageIO.write(bufferedImage, "png", bos);
			byte[] bs = bos.toByteArray();
			return new String(Base64.encodeBase64(bs));
		} finally {
			bos.close();
			is.close();
		}
	}

}