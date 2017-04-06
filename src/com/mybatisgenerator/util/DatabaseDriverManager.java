package com.mybatisgenerator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriverManager {
	static Connection        con;
	static PreparedStatement ps; 
	static ResultSet rs;
	
	public static boolean connectToDatabase(String databaseType,String url,String username,String password){
		try {
				Class.forName(DatabaseInfo.getChooseDatabase(databaseType).get("classname"));
				String urlprex = DatabaseInfo.getChooseDatabase(databaseType).get("urlprex");
				con = DriverManager.getConnection(urlprex+url, username, password);
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con = null;
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con = null;
			return false;
		}
		
	}
	
	public static List<String> getSchemaList(){
		List<String> list = new ArrayList<String>();
		try {
			ps = con.prepareStatement("SELECT SCHEMA_NAME FROM information_schema.SCHEMATA");
			rs = ps.executeQuery();
			String row;
			  while(rs.next()){
				  row=rs.getString("SCHEMA_NAME");
				  list.add(row);
			  }
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getTableList(String databaseName,String username) throws SQLException{
		List<String> list;
		if(con!=null){
			DatabaseMetaData dmd = con.getMetaData();
			list = new ArrayList<String>();
			rs = dmd.getTables(databaseName, username.toUpperCase(), null, null);
			while(rs.next()){
				list.add(rs.getString(3));
			}
			if(list.size()>0){
				return list;
			}
		}
		return null;
	}
}


