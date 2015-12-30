package admin.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import vg.core.configproxy.ConfigProxy;
import vg.core.dbproxy.DbProxy;

public class AppContextListener implements ServletContextListener {
	public static String AppName = "poly.admin";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println(String.format("%s.mainDestroyed", AppName));

		DbProxy.shutdownConnPool();		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(String.format("%s.mainInitialized", AppName));

		ServletContext servletContext = arg0.getServletContext();
		ConfigProxy.init(servletContext.getRealPath("/"));		
		//decryptConfig(); //decryptConfig

		System.out.println(String.format("%s.appPath: %s", AppName,
				ConfigProxy.appPath));
		
//		MemcacheProxy.setAppPath(ConfigProxy.appPath);
//		MemcacheProxy.init();

		DbProxy.username = ConfigProxy.categories.get("vgdb").get("username");
		DbProxy.password = ConfigProxy.categories.get("vgdb").get("password");
		DbProxy.setAppPath(ConfigProxy.appPath);
		DbProxy.configureConnPool();
	}
    
}
