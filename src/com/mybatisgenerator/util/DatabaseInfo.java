package com.mybatisgenerator.util;

import java.util.HashMap;
import java.util.Map;

public class DatabaseInfo {
	public static final Map<String,String> mysql = new HashMap<String,String>();
	static{
		mysql.put("classname", "com.mysql.jdbc.Driver");
		mysql.put("urlprex","jdbc:mysql://");
		mysql.put("lib","mysql-connector-java-5.1.41-bin.jar");
	}
	
	public static Map<String,String> getChooseDatabase(final String databaseType){
		switch(databaseType){
			case "mysql":
				return mysql;
			default:
				return null;
		}
	}
}
