package com.database.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
	private static String url = null;
	private static String driver = null ;
	private static String username = null;
	private static String password = null;
	private static Integer maxpoolsize = null;
	private static Integer minpoolsize = null;
	private static String dataSource = null ;
	private static Integer achrivecount = null ;
	
	public static boolean flag = false ;
	
	static {
		Properties prop = new Properties();
		if(dataSource!=null&&"".equals(dataSource)){
			InputStream in = Object.class
					.getResourceAsStream(dataSource);
			try {
				prop.load(in);
				url = prop.getProperty("url").trim();
				username = prop.getProperty("username").trim();
				password = prop.getProperty("password").trim();
				driver = prop.getProperty("driver").trim() ;
				maxpoolsize = Integer.parseInt(prop.getProperty("maxpoolsize").trim()) ;
				minpoolsize = Integer.parseInt(prop.getProperty("minpoolsize").trim()) ;
				achrivecount = Integer.parseInt(prop.getProperty("achrivecount").trim()) ;
				flag = finish() ;
			} catch (IOException e) {
				System.out.println("exception");
				e.printStackTrace();
				flag = finish();
			}
		}else{
			flag = finish();
		}
	}
	
	
	
	public static void init(String property){
		dataSource = property ;
	}
	public static boolean finish(){
		if(url==null&&("").equals(url)){
			return false ;
		}
		if(username==null&&("").equals(url)){
			return false ;
		}
		if(password==null&&("").equals(url)){
			return false ;
		}
		if(driver==null&&("").equals(url)){
			return false ;
		}
		if(maxpoolsize==null&&("").equals(url)){
			return false ;
		}
		if(minpoolsize==null&&("").equals(url)){
			return false ;
		}
		if(achrivecount==null&&("").equals(url)){
			return false ;
		}
		
		return true ;
	}
	private PropertiesUtil() {
		
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		PropertiesUtil.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		PropertiesUtil.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		PropertiesUtil.password = password;
	}
	public static String getDriver() {
		return driver;
	}
	public static void setDriver(String driver) {
		PropertiesUtil.driver = driver;
	}
	public static Integer getMaxpoolsize() {
		return maxpoolsize;
	}
	public static void setMaxpoolsize(Integer maxpoolsize) {
		PropertiesUtil.maxpoolsize = maxpoolsize;
	}
	public static Integer getMinpoolsize() {
		return minpoolsize;
	}
	public static void setMinpoolsize(Integer minpoolsize) {
		PropertiesUtil.minpoolsize = minpoolsize;
	}
	public static Integer getAchrivecount() {
		return achrivecount;
	}
	public static void setAchrivecount(Integer achrivecount) {
		PropertiesUtil.achrivecount = achrivecount;
	}
}
