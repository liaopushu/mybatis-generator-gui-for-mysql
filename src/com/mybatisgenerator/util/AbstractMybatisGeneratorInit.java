package com.mybatisgenerator.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

public abstract class AbstractMybatisGeneratorInit {
	
	static MyBatisGenerator myBatisGenerator;
    static ProgressCallback progressCallback;
    static Context context;
    static Configuration config;
    static Set<String> contextIds;
    static Set<String> fullyQualifiedTableNames;
    static ShellCallback shellCallback;
    static JDBCConnectionConfiguration jdbcConfig;
    static String projectPath;
    static String mapperXmlPath;
    static String packgetPath;
    static String daopackgetPath;
    static String mapPackgetPath;
    
	abstract public void setConnectionUrl(String url);
	
	abstract public void setUserId(String userId);
	
	abstract public void setPassword(String password);
	
	abstract public void setConnectLibPath(String path);
	
	abstract public void setJdbcConfigDriver(String jdbcConfigDriverClass);
	
	abstract public void setProjectPath(String path);
	
	abstract public void setModelPackgetPath(String path);
	
	abstract public void setDaoPackgetPath(String path);
	
	abstract public void setMapPackgetPath(String path);
	
	abstract public void setMapperXmlPath(String path);
	
	public void setBaseGeneratorConfig(Map<String,String> map){
		if(map.size()>0){
			setConnectionUrl(map.get(DriverInformationKey.connectionUrl.toString()));
			setConnectLibPath(map.get(DriverInformationKey.connectorLibPath.toString()));
			setJdbcConfigDriver(map.get(DriverInformationKey.jdbcConfigDriverClass.toString()));
			setUserId(map.get(DriverInformationKey.userId.toString()));
			setPassword(map.get(DriverInformationKey.password.toString()));
		}
	}
	
	public AbstractMybatisGeneratorInit(Map<String,Map<String,String>> map,String databaseType){
		Map<String,String> databaseInfo;
		config = new Configuration();
		context = new Context(ModelType.CONDITIONAL);
		config.addContext(context);
		jdbcConfig = new JDBCConnectionConfiguration();
		fullyQualifiedTableNames = new HashSet<>();
	    shellCallback = new DefaultShellCallback(true);
	    contextIds = new HashSet<>();
		if(null!=map){
			databaseInfo = map.get(databaseType); 
			setBaseGeneratorConfig(databaseInfo);
		}
	}
	
	public AbstractMybatisGeneratorInit(){
		config = new Configuration();
		context = new Context(ModelType.CONDITIONAL);
		config.addContext(context);
		jdbcConfig = new JDBCConnectionConfiguration();
		fullyQualifiedTableNames = new HashSet<>();
	    shellCallback = new DefaultShellCallback(true);
	    contextIds = new HashSet<>();
	}
	
	public void GeneratorTarget(String tableName,String entryName,boolean offset,boolean anno,boolean jpa,boolean example) throws InvalidConfigurationException, SQLException, IOException, InterruptedException{
		TableConfiguration tableConfig = new TableConfiguration(context);
		tableConfig.setTableName(tableName);
		tableConfig.setDomainObjectName(entryName);
		JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
		if(null!=projectPath&&null!=packgetPath){
			modelConfig.setTargetPackage(packgetPath);
			modelConfig.setTargetProject(projectPath);
		}
		SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();
		if(null!=mapPackgetPath&&null!=mapperXmlPath){
			mapperConfig.setTargetPackage(mapPackgetPath);
			mapperConfig.setTargetProject(mapperXmlPath);
		}
		JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
		if(null!=daopackgetPath){
			daoConfig.setConfigurationType("XMLMAPPER");
			daoConfig.setTargetPackage(daopackgetPath);
			daoConfig.setTargetProject(projectPath);
		}
		CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
		if(anno){
			commentConfig.addProperty("columnRemarks", "true");
		}
		if(jpa){
			commentConfig.addProperty("annotations", "true");
		}
		if(!example){
			tableConfig.setCountByExampleStatementEnabled(false);
			tableConfig.setDeleteByExampleStatementEnabled(false);
			tableConfig.setSelectByExampleStatementEnabled(false);
			tableConfig.setUpdateByExampleStatementEnabled(false);
		}
		context.setCommentGeneratorConfiguration(commentConfig);
	    context.setId("myid");
	    context.setTargetRuntime("MyBatis3");
	    context.addTableConfiguration(tableConfig);
	    context.setJdbcConnectionConfiguration(jdbcConfig);
	    context.setJavaModelGeneratorConfiguration(modelConfig);
	    context.setSqlMapGeneratorConfiguration(mapperConfig);
	    context.setJavaClientGeneratorConfiguration(daoConfig);
	    PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
	    serializablePluginConfiguration.addProperty("type", "org.mybatis.generator.plugins.SerializablePlugin");
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(serializablePluginConfiguration);
        if(offset){
        	PluginConfiguration pluginConfiguration = new PluginConfiguration();
        	pluginConfiguration.addProperty("type", "com.mybatisgenerator.util.MySQLLimitPlugin");
        	pluginConfiguration.setConfigurationType("com.mybatisgenerator.util.MySQLLimitPlugin");
        	context.addPluginConfiguration(pluginConfiguration);
        }
        context.setTargetRuntime("MyBatis3");
        List<String> warnings = new ArrayList<>();
        myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(progressCallback, contextIds, fullyQualifiedTableNames);
	}
	
	public void setProgressCallback(ProgressCallback pcb){
		progressCallback = pcb;
	}
}
