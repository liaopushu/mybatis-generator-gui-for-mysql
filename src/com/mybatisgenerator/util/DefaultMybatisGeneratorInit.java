package com.mybatisgenerator.util;

import java.util.Map;



public class DefaultMybatisGeneratorInit extends AbstractMybatisGeneratorInit {
	
	public DefaultMybatisGeneratorInit(Map<String, Map<String, String>> map, String databaseType) {
		super(map, databaseType);
		// TODO Auto-generated constructor stub
	}
	
	public DefaultMybatisGeneratorInit() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void setConnectionUrl(String url) {
		// TODO Auto-generated method stub
		jdbcConfig.setConnectionURL(url);
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		jdbcConfig.setUserId(userId);
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		jdbcConfig.setPassword(password);
	}

	@Override
	public void setJdbcConfigDriver(String jdbcConfigDriverClass) {
		// TODO Auto-generated method stub
		jdbcConfig.setDriverClass(jdbcConfigDriverClass);
	}

	@Override
	public void setProjectPath(String path) {
		// TODO Auto-generated method stub
		projectPath = path;
	}

	@Override
	public void setModelPackgetPath(String path) {
		// TODO Auto-generated method stub
		packgetPath = path;
	}
	
	@Override
	public void setMapPackgetPath(String path) {
		// TODO Auto-generated method stub
		mapPackgetPath = path;
	}
	
	@Override
	public void setDaoPackgetPath(String path) {
		// TODO Auto-generated method stub
		daopackgetPath = path;
	}
	
	@Override
	public void setMapperXmlPath(String path) {
		// TODO Auto-generated method stub
		mapperXmlPath = path;
	}

	@Override
	public void setConnectionLibPath(String path) {
		// TODO Auto-generated method stub
		connectionLibPath = path;
	}


}
