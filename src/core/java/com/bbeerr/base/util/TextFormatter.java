package com.bbeerr.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public abstract class TextFormatter {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static String randomCode = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static Date parseDatetime(String source) {
		if (source == null || source.trim().equals("")) {
			return null;
		}
		Date date = null;
		try {
			if (source.length() <= 10) {
				source += " 00:00:00";
			}
			date = datetimeFormat.parse(source);
		} catch (ParseException e) {
			System.out.println("datetime parse error");
		}
		return date;
	}

	public static String formatDatetime(Date source, String format) {
		if (source == null) {
			return "";
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(format);
		String dateString = datetimeFormat.format(source);
		return dateString;
	}

	public static String formatDatetime(Date source) {
		if (source == null) {
			return "";
		}
		String dateString = datetimeFormat.format(source);
		return dateString;
	}

	public static String formatDate(Date source) {
		if (source == null) {
			return "";
		}
		String dateString = dateFormat.format(source);
		return dateString;
	}

	public static String formatDate(Date source, String format) {
		if (source == null) {
			return "";
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(format);
		String dateString = datetimeFormat.format(source);
		return dateString;
	}

	public static String zeroFill(int num, int len) {
		String format = "%0" + len + "d";
		String value = String.format(format, num);
		return value;
	}

	public static String randomCode(int len) {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			int idx = random.nextInt(62);
			String strRand = String.valueOf(randomCode.charAt(idx));
			buffer.append(strRand);
		}
		return buffer.toString();
	}

}