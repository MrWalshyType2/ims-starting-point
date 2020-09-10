package com.qa.ims.utils;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Utils {
	
	private static Properties properties = null;
	public static String MYSQL_URL = null;
	
	private static final String FILENAME = "app.properties";
	public static final Scanner SCANNER = new Scanner(System.in);

	public Utils() {
	
	}
	
	public static void initProperties(InputStream stream) {
		try {
			properties = new Properties();
			properties.load(stream);
			
			MYSQL_URL = properties.getProperty("url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getInput() {
		return SCANNER.nextLine();
	}
	
	public static String getPropFileName() {
		return FILENAME;
	}

}
