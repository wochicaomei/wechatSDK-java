package com.bbeerr.base.util;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;

public abstract class BaseUtils {

	static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static void setValue(Object obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
			obj.getClass().getMethod(methodName, new Class[] { field.getType() }).invoke(obj, new Object[] { value });
		} catch (Exception ex) {
			System.out.println(obj.toString() + " set " + name + "=" + value + "failed");
		}
	}

	public static Object getValue(Object obj, String name) throws Exception {
		String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		return obj.getClass().getMethod(methodName, new Class[] {}).invoke(obj, new Object[] {});
	}

	public static String md5(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}

	public static String generateRandomCode(int len) {
		StringBuffer randomCode = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(62)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	public void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

	public boolean isEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	public static void convert(JSONObject json, Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();

			if (json.containsKey(name)) {
				Object value = json.get(name);

				if (field.getType() == java.lang.String.class) {
					value = value == null ? "" : value;
					value = StringEscapeUtils.escapeHtml4(String.valueOf(value));
				} else {
					if (value == null || value.equals("")) {
						continue;
					}
				}

				if (field.getType() != value.getClass()) {
					if (field.getType() == java.util.Date.class) {
						value = TextFormatter.parseDatetime(value.toString());
					}

					if (fields[i].getType() == java.lang.Long.class) {
						value = Long.parseLong(value.toString());
					}

					if (fields[i].getType() == java.lang.Double.class) {
						value = Double.parseDouble(value.toString());
					}

					if (fields[i].getType() == java.lang.Boolean.class) {
						value = Boolean.parseBoolean(value.toString());
					}

					if (fields[i].getType() == java.lang.Float.class) {
						value = Float.parseFloat(value.toString());
					}

					if (fields[i].getType() == java.lang.Integer.class) {
						value = Integer.parseInt(value.toString());
					}

					if (fields[i].getType() == java.math.BigDecimal.class) {
						value = BigDecimal.valueOf(Double.parseDouble(value.toString()));
					}
				}

				setValue(obj, name, value);
			}
		}
	}

	public static JSONObject filterFields(JSONObject json, String[] include, String[] exclude) {
		if (exclude != null) {
			for (int i = 0; i < exclude.length; i++) {
				if (json.containsKey(exclude[i])) {
					json.remove(exclude[i]);
				}
			}
		}

		if (include != null) {
			Arrays.sort(include);
			@SuppressWarnings("unchecked")
			Iterator<String> it = json.keys();
			while (it.hasNext()) {
				String key = it.next();
				if (Arrays.binarySearch(include, key) < 0) {
					json.remove(key);
				}
			}
		}

		return json;
	}

	public static String compressImage(String filename, int width, int height) throws Exception {
		return new ImageCompressor(new FileInputStream(filename), width, height).compress();
	}

	public static void main(String args[]) {
		for (int i = 0; i < 1000; i++) {
			System.out.println(createUUID());
		}
	}

}