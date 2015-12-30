package com.seiu.web.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jconfig.Category;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.XMLFileHandler;


public class ConfigUtils {
	private static Logger logger=Logger.getLogger(ConfigUtils.class);
	@SuppressWarnings("static-access")
	public static Configuration loadConfig(String configName,String path){
		ConfigurationManager conf_manager = ConfigurationManager.getInstance();
		File file = new File(path);
		XMLFileHandler handler = new XMLFileHandler();
		System.out.println(file.exists());
		handler.setFile(file);		
		try {
			conf_manager.load(handler, configName);
			Configuration config = conf_manager.getConfiguration(configName);
			return config;
		} catch (ConfigurationManagerException e) {
			// TODO Auto-generated catch block
			logger.error(String.format("error in load config %s from file %s",configName,path), e);
		}
		return null;
			
	}
	public static Configuration loadConfig(String path){
		String tempName="config_temnp_"+System.currentTimeMillis();
		return loadConfig(tempName, path);
	}
	
	public static String getCurrentDirPath(){
		String prefix=new File("").getAbsolutePath();
		prefix=prefix.replaceAll("\\\\", "/");
		return prefix;
	}
	public static Map<Object,Object> loadAllProperties(String categoryName,Configuration config){
		Map<Object,Object> result= new HashMap<Object, Object>();
		Category category = config.getCategory(categoryName);
		Properties properties = category.getProperties();
    	if(properties!=null){
    		for(Object key : properties.keySet()){
    			String value=properties.getProperty((String) key);
    			result.put(key, value);
    		}    		
    	}
    	return result;
	}
	@SuppressWarnings("rawtypes")
	public static Map<String,Map> loadAllCategories(Configuration config){
		Map<String,Map> result= new HashMap<String, Map>();
		String[] categoryNames = config.getCategoryNames();
		for (String categoryName : categoryNames) {
			if (!categoryName.equalsIgnoreCase("general")) {
				Map<Object, Object> properties = loadAllProperties(categoryName, config);
				result.put(categoryName, properties);
			}
		}
		return result;
	}
	
}
