package com.seiu.web.common;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.jconfig.Configuration;

public class Config {
	private static final Logger logger = Logger.getLogger(Config.class);
	  public static String configPath;
	  public static Configuration config;
	  public static String appName = "polyvietnam";
	  public static String contextPath = "http://polyvietnam.edu.vn";
	  public static String usePathStatic = "true";
	  public static String host = "smtp.gmail.com";
	  public static int port = 465;
	  public static String user = "forgotpass.polyvietnam";
	  public static String password = "Poly25251325";
	  public static String from = "forgotpass.polyvietnam@gmail.com";
	  public static String subject = "[POLY] - Reset password";
	  public static String content = "New your password is : {PASSWORD}";
	  
	  public static void load(String path)
	  {
	    configPath = path;
	    config = ConfigUtils.loadConfig("wap_config", configPath);
	    initConfig();
	  }
	  
	  public static void initConfig()
	  {
	    contextPath = getProperty("contextPath", contextPath, "app");
	    appName = getProperty("appName", appName, "app");
	    usePathStatic = getProperty("usePathStatic", usePathStatic, "app");
	    
	    host = getProperty("host", appName, "email");
	    port = getInt("port", appName, "email");
	    user = getProperty("user", appName, "email");
	    password = getProperty("password", appName, "email");
	    from = getProperty("from", appName, "email");
	    subject = getProperty("subject", appName, "email");
	    content = getProperty("content", appName, "email");
	  }
	  
	  private static String getProperty(String key, String defaultValue, String category)
	  {
	    String rs = config.getProperty(key, defaultValue, category);
	    logger.debug("Load config ==> " + key + ":" + rs);
	    return rs;
	  }
	  
	  private static int getInt(String key, String defaultValue, String category)
	  {
	    String rs = getProperty(key, defaultValue, category);
	    try
	    {
	      return Integer.valueOf(rs).intValue();
	    }
	    catch (Exception e) {}
	    return 0;
	  }
	  
	  public String toString()
	  {
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	  }
}
