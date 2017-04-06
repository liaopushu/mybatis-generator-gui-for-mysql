package com.mybatisgenerator.model;

import java.util.Map;

public class ConnectionEntry {

	public String connectionId;
	public String connectionName;
	public Map<String,String> context;
	
	public String getConnectionName(){
		return connectionName;
	}
	
	public void setConnectionName(String connectionName){
		this.connectionName = connectionName;
	}
	
	public String getConnectionId(){
		return connectionId;
	}
	
	public void setConnectionId(String connectionId){
		this.connectionId=connectionId;
	}
	
	public void setContext(Map<String,String> context){
		this.context = context;
	}
	
	public Map<String,String> getContext(){
		return context;
	}
	
	public ConnectionEntry(){
	}

	public ConnectionEntry(String connectionId, String connectionName, Map<String, String> context) {
		super();
		this.connectionId = connectionId;
		this.connectionName = connectionName;
		this.context = context;
	}
}
