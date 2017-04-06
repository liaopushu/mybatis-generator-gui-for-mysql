package com.mybatisgenerator.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mybatisgenerator.model.ConnectionEntry;

public class PropertiesManager {
	
	public static String programPath;
	public static File file;
	public static FileUtils fu = new FileUtils();
	public static JsonParser parser = new JsonParser();
	public static JsonArray js;
	public static Gson g = new Gson();
	
	static{
		setProgramPath();
		boolean exist = checkTheFileExist(programPath);
		if(exist){
				String jsonString = readPropertiesFile(programPath);
				if(StringUtils.isNotEmpty(jsonString)){
					js = getJsonArray(jsonString);
				}else{
					js = new JsonArray();
				}
		}else{
			js = new JsonArray();
		}
	}
	
	public static boolean checkTheFileExist(String path){
		File file = new  File(path+"\\connections.json");
		if(file.exists()){
			System.out.println("file exist");
			PropertiesManager.file = file;
			return true;
		}else{
			try {
				boolean flag = file.createNewFile();
				PropertiesManager.file = file;
				System.out.println("file no exist,create file");
				return flag;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static void setProgramPath(){
		File file = new File("");
		String ap = file.getAbsolutePath();
		programPath = ap;
	}
	
	public static String readPropertiesFile(String path){
		try {
			StringBuffer sb = new StringBuffer();
//			InputStream in = new URL(path).openStream();
			List<String> l = FileUtils.readLines(file);
			for(String i:l){
				sb.append(i);
			}
			String jsonString =StringUtils.deleteWhitespace(sb.toString()); 
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray getJsonArray(String jsonString){
		JsonArray jsonArray = null;
		parser = new JsonParser();
		JsonElement el = parser.parse(jsonString);
		if(el.isJsonArray()){
			jsonArray=el.getAsJsonArray();
		}
		return jsonArray;
	}
	
	public static List<ConnectionEntry> getConnectionInformationFromFile(JsonArray jsonArray){
		List<ConnectionEntry> list = new ArrayList<ConnectionEntry>();
		if(null!=jsonArray){
			ConnectionEntry ce = null;
			Iterator it = jsonArray.iterator();
			while(it.hasNext()){
				JsonElement e = (JsonElement)it.next();
				ce = g.fromJson(e, ConnectionEntry.class);
				list.add(ce);
			}
			return list;
		}
		return null;
	}
	
	public static ConnectionEntry getConnectionEntry(int index){
		ConnectionEntry ce = null;
		if(js.size()>0){
			JsonElement je = js.get(index);
			ce = g.fromJson(je, ConnectionEntry.class);
			return ce;
		}else{
			return null;
		}
	}
	
	public static boolean updateJsonObj(int index,ConnectionEntry connectionEntry){
		JsonElement je;
		Map<String,String> context = connectionEntry.getContext();
		boolean flag = validateMap(context);
		if(true==flag){
			String jsonString = g.toJson(connectionEntry);
			je = parser.parse(jsonString);
			js.set(index, je);
			writePropertiesFile();
			return true;
		}
		return false;
	}
	
	public static boolean removeJsonObj(int index){
		js.remove(index);
		if(writePropertiesFile()){
			return true;
		}
		return false;
	}
	
	public static boolean isNameExist(String name){
		if(null!=js&&js.size()>0){
			Iterator<JsonElement> ij = js.iterator();
			while(ij.hasNext()){
				JsonObject jo = ij.next().getAsJsonObject();
				if(StringUtils.equals(name, jo.get("connectionName").getAsString())){
					return false;
				}
			}
		}
		return  true;
	}
	
	public static boolean validateMap(Map<String,String> map){
		if(null!=map&&map.size()==4){
			if(map.containsKey(DriverInformationKey.connectionUrl.toString())&&map.containsKey(DriverInformationKey.userId.toString())&&map.containsKey(DriverInformationKey.password.toString())&&map.containsKey("type")){
				return true;
			}
		}
		return false;
	}
	
	public static boolean addJsonToProperties(ConnectionEntry connectionEntry){
		if(null!=connectionEntry){
			String jsonString = g.toJson(connectionEntry);
			JsonElement je = parser.parse(jsonString);
			js.add(je);
			return writePropertiesFile();
		}
		return false;
	}
	
	public static boolean writePropertiesFile(){
		try {
			FileUtils.writeStringToFile(file, js.toString(), "utf-8");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static JsonElement getEditEntry(int index){
		if(null!=js&&js.size()>0){
			JsonElement je = js.get(index);
			return je;
		}
		return null;
	}
}
