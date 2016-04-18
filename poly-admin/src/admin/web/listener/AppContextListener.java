package admin.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import vg.core.configproxy.ConfigProxy;
import vg.core.dbproxy.DbProxy;

public class AppContextListener
  implements ServletContextListener
{
  public static String AppName = "poly.admin";
  
  public void contextDestroyed(ServletContextEvent arg0)
  {
    System.out.println(String.format("%s.mainDestroyed", new Object[] { AppName }));
    
    DbProxy.shutdownConnPool();
  }
  
  public void contextInitialized(ServletContextEvent arg0)
  {
    System.out.println(String.format("%s.mainInitialized", new Object[] { AppName }));
    
    ServletContext servletContext = arg0.getServletContext();
    ConfigProxy.init(servletContext.getRealPath("/"));
    

    System.out.println(String.format("%s.appPath: %s", new Object[] { AppName, 
      ConfigProxy.appPath }));
    



    DbProxy.username = ConfigProxy.categories.get("vgdb").get("username");
    DbProxy.password = ConfigProxy.categories.get("vgdb").get("password");
    DbProxy.setAppPath(ConfigProxy.appPath);
    DbProxy.configureConnPool();
  }
}
