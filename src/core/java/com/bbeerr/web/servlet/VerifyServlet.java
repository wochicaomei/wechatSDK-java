package com.bbeerr.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/verify.jpg" })
public class VerifyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private int width = 60;
	private int height = 20;

	private int codeCount = 5;
	private int x = 0;
	private int fontHeight;
	private String fontName;
	private int codeY;

	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	String[] fontNames = { "Fixedsys", "Arial" };
	int[] fontSizes = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

	public VerifyServlet() {
		super();
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		String strWidth = req.getParameter("width");
		String strHeight = req.getParameter("height");

		if (strWidth != null && strWidth.length() != 0) {
			try {
				width = Integer.parseInt(strWidth);
			} catch (Exception ex) {

			}
		}

		if (strHeight != null && strHeight.length() != 0) {
			try {
				height = Integer.parseInt(strHeight);
			} catch (Exception ex) {

			}
		}

		if (width < 40) {
			width = 40;
		}

		if (height < 20) {
			height = 20;
		}

		x = width / (codeCount + 1);
		codeY = height - 10;

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height + 10);
		g.setColor(Color.BLACK);

		StringBuffer randomCode = new StringBuffer();

		g.setColor(Color.BLUE);
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(62)]);
			fontName = fontNames[random.nextInt(2)];
			fontHeight = height - fontSizes[random.nextInt(15)];

			Font font = new Font(fontName, Font.PLAIN, fontHeight);
			g.setFont(font);
			g.drawString(strRand, (i + 1) * x, codeY);
			randomCode.append(strRand);
		}

		HttpSession session = req.getSession();
		session.setAttribute("verifyCode", randomCode.toString());

		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");

		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}