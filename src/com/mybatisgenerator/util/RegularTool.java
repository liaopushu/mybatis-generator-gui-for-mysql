package com.mybatisgenerator.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTool {
	public static Pattern rePackageName = Pattern.compile("^[a-z]+(\\.[a-z]+)*");
	public static Pattern reName = Pattern.compile("^[a-z]+");
	public static Pattern reConnectionName = Pattern.compile("^[A-Za-z0-9]+");
	public int a =1;
	
	public static boolean reName(String... name){
		Matcher matcher;
		for(String n:name){
			matcher = reName.matcher(n);
			if(!matcher.matches()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkPath(String path){
		File f = new File(path);
		if(f.exists()){
			return true;
		}
		return false;
	}
	
	public static boolean rePackageName(String packageName){
		Matcher matcher = rePackageName.matcher(packageName);
	    boolean rs = matcher.matches();
	    return rs;
	}
	
	public static boolean checkConnectionName(String name){
		Matcher matcher = reConnectionName.matcher(name);
		boolean rs = matcher.matches();
		return rs;
	}
	
	public static boolean reSystemDirectoryPath(String path){
		
		return false;
	}
}
