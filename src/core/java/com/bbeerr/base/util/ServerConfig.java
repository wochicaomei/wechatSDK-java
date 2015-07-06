package com.bbeerr.base.util;

import java.io.IOException;
import java.util.Properties;

public class ServerConfig {

	private static Properties props = new Properties();

	static {
		try {
			props.load(ServerConfig.class.getClassLoader().getResourceAsStream("server.cfg.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		String value = props.getProperty(key);
		return value == null ? "" : value;
	}

}