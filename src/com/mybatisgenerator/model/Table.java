package com.mybatisgenerator.model;

public class Table {
	private String tableName;
	private String pojoName;
	private boolean isPage;
	private boolean isDoc;
	private boolean isAnnotation;
	private boolean example;
	
	public boolean isExample() {
		return example;
	}
	public void setExample(boolean example) {
		this.example = example;
	}
	public boolean isPage() {
		return isPage;
	}
	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}
	public boolean isDoc() {
		return isDoc;
	}
	public void setDoc(boolean isDoc) {
		this.isDoc = isDoc;
	}
	public boolean isAnnotation() {
		return isAnnotation;
	}
	public void setAnnotation(boolean isAnnotation) {
		this.isAnnotation = isAnnotation;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPojoName() {
		return pojoName;
	}
	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}
}
