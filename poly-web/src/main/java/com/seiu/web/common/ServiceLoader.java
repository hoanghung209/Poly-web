package com.seiu.web.common;

import java.io.File;
import javax.servlet.ServletContext;
import org.apache.log4j.PropertyConfigurator;
import com.vega.db.ConnectionPool;
import com.vega.db.DBManager;

public class ServiceLoader {
	public static String configDir = "";
	private static DBManager dbManager;
	private static ConnectionPool dbPool;

	public static String getConfigPath(String configName) {
		if (configDir != null) {
			return new File(configDir, configName).getPath();
		}
		return configName;
	}

	public static void loadConfig(ServletContext context) {
		String c = context.getInitParameter("config-location");
		System.out.println(c);
		configDir = new File(context.getRealPath(""), c).getPath();
		loadLog4jConfig();
		loadDBConfig();			
		Config.loadConfig();
		loadWebConfig();
	}
	
	public static void loadLog4jConfig() {
		String log4jConfig = getConfigPath("log4j.properties");
		PropertyConfigurator.configure(log4jConfig);
	}

	public static void loadDBConfig() {
		String config = getConfigPath("db.xml");
		DBManager.loadConfig(config);
		dbManager = DBManager.getInstance();
		dbPool = dbManager.getDBPool();
	}	

	public static void loadWebConfig() {
		Config.load(getConfigPath("config.xml"));
	}

	public static DBManager getDbManager() {
		return dbManager;
	}

	public static void setDbManager(DBManager dbManager) {
		ServiceLoader.dbManager = dbManager;
	}

	public static String getConfigDir() {
		return configDir;
	}

	public static void setConfigDir(String configDir) {
		ServiceLoader.configDir = configDir;
	}

	
	public static ConnectionPool getDbPool() {
		return dbPool;
	}

	public static void setDbPool(ConnectionPool dbPool) {
		ServiceLoader.dbPool = dbPool;
	}
}
