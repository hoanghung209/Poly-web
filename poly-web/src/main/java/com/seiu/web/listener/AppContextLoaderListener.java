package com.seiu.web.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.seiu.web.common.ServiceLoader;


public class AppContextLoaderListener extends ContextLoaderListener {
	 @Override
	    public void contextDestroyed(ServletContextEvent event) {
	        super.contextDestroyed(event);
	    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ServiceLoader.loadConfig(context);
    }
    
}
