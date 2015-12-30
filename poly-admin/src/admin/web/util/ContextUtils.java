package admin.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import vg.core.common.DateUtils;
import vg.core.common.StringUtils;
import vg.core.configproxy.ConfigProxy;

@ManagedBean
@ViewScoped
@SuppressWarnings("serial")
public class ContextUtils implements Serializable {
	public static Boolean isDebug = Boolean.valueOf(ConfigProxy.categories.get("app").get("isDebug"));
	public static String contextPath = ConfigProxy.categories.get("app").get("contextPath");
	private static String domain = ConfigProxy.categories.get("file").get("server");
	
	public static void systemOutPrint(String input) {
		if(isDebug) {
			System.out.println(input);
		}
	}
	
	public static String makeSessionKey() {
		Random random = new Random();
		String cDate = DateUtils.format("yyyyMMddHHmmss", new Date());
		String token = String.format("%s%s%s%s%s", cDate.substring(0, 3), random.nextInt(100), cDate.substring(4, 7), cDate.substring(8), random.nextInt(100));					
		String encode = StringUtils.md5(token);	

		setSession("vgSessionKey", encode);		
		return encode;
	}
	public static String makeSessionKey(HttpServletRequest req) {
		Random random = new Random();
		String cDate = DateUtils.format("yyyyMMddHHmmss", new Date());
		String token = String.format("%s%s%s%s%s", cDate.substring(0, 3), random.nextInt(100), cDate.substring(4, 7), cDate.substring(8), random.nextInt(100));					
		String encode = StringUtils.md5(token);
		
		System.out.println("encode:" + encode);

		setSession(req, "vgSessionKey", encode);		
		return encode;
	}	
	public static boolean authSessionKey(HttpServletRequest req, String token) {
		String sessionKey = getSession(req, "vgSessionKey", "");
		
		return (!StringUtils.isBlank(token) && !StringUtils.isBlank(sessionKey) && sessionKey.equals(token));
	}	
	
	public static String getRemoteIp() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
        return ip;
	}	
	public static String getRemoteIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}		
 	public static HttpServletRequest getRequest() {
 		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
 	}	
 	public static int getRequest(String key, int defaultValue) {
 		FacesContext fc = FacesContext.getCurrentInstance();
 		Map<String, String> request = fc.getExternalContext().getRequestParameterMap();
 		int value = 0; 
 		try {
 			String rValue = request.get(key);
 			value = (StringUtils.isBlank(rValue)) ? defaultValue : Integer.parseInt(rValue);
 		}
 		catch(Exception e){
 			value = defaultValue;
 		}
 		return value;	
 	}
 	public static String getRequest(String key, String defaultValue) {
 		FacesContext fc = FacesContext.getCurrentInstance();

 		Map<String, String> request = fc.getExternalContext().getRequestParameterMap();
 		String value = ""; 
 		try {
 			String rValue = request.get(key);
 			value = (StringUtils.isBlank(rValue)) ? defaultValue : rValue; 		
 		}
 		catch(Exception e){
 			value = defaultValue;
 		}
 		return value;	
 	}	 
 	public static String getRequestTemplate() {
 		return getRequest("template", "default");	
 	}	  	

 	public static String getSessionId(){
 		FacesContext fc = FacesContext.getCurrentInstance(); 		
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();		
		HttpSession session = request.getSession();
		
		return session.getId(); 		
 	}
 	public static Object getSession(String key){
 		FacesContext fc = FacesContext.getCurrentInstance(); 		
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();		
		HttpSession session = request.getSession();
		
		return session.getAttribute(key); 		
 	}
 	public static void removeSession(String key){
 		FacesContext fc = FacesContext.getCurrentInstance(); 		
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();		
		HttpSession session = request.getSession();
		
		session.removeAttribute(key);
 	}
 	public static String getSession(String key, String defaultValue){ 
 		try {
 	 		FacesContext fc = FacesContext.getCurrentInstance(); 		
 			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();		
 			HttpSession session = request.getSession();		
 			Object objValue = session.getAttribute(key);
 			
 			if(objValue != null) {
 				return String.valueOf(objValue);
 			} else {
 				return defaultValue;
 			} 			
 		} catch (Exception ex) {
 			return defaultValue;
 		}
 	} 	
 	public static void setSession(String key, Object value){
 		FacesContext fc = FacesContext.getCurrentInstance(); 		
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();		
		HttpSession session = request.getSession();
		
		session.setAttribute(key, value); 		
 	}
 	
 	public static String getSessionId(HttpServletRequest req){		
		HttpSession session = req.getSession();		
		return session.getId(); 		
 	} 	
 	public static String getSession(HttpServletRequest req, String key, String defaultValue){
		HttpSession session = req.getSession();		
		Object objValue = session.getAttribute(key);
		
		if(objValue != null) {
			return String.valueOf(objValue);
		} else {
			return defaultValue;
		}
 	}  	
 	public static void setSession(HttpServletRequest req, String key, Object value) {
		HttpSession session = req.getSession(true);
		session.setAttribute(key, value);
 	}

 	public static String getContextPath(){
 		return getContextPath(true);
 	} 	
 	public static String getContextPath(boolean fullPath){
 		return ContextUtils.contextPath;
 		
 		/*
 		ExternalContext xContext = FacesContext.getCurrentInstance().getExternalContext();
 		
 		String scheme = xContext.getRequestScheme();
 		String serverName = xContext.getRequestServerName();
 		int port = xContext.getRequestServerPort();
 		String contextPath = xContext.getRequestContextPath();
 		 		
 		if(fullPath) return scheme + "://" + serverName + ":" + port + contextPath;
 		else return contextPath;
 		*/ 	
 	}
 	
 	public static String getCurrentPath(){
 		return getCurrentPath(true);
 	}
 	public static String getCurrentPath(boolean fullPath){ 		 	
 		/*
 		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
 		
 		String currentPath = request.getRequestURL().toString();
 		if (request.getQueryString() != null) {
 		   currentPath += '?' + request.getQueryString();
 		}
 		return currentPath;
 		*/
 		ExternalContext xContext = FacesContext.getCurrentInstance().getExternalContext();
 		
// 		String scheme = xContext.getRequestScheme();
// 		String serverName = xContext.getRequestServerName();
// 		int port = xContext.getRequestServerPort();
// 		String contextPath = xContext.getRequestContextPath();
 		String servletPath = xContext.getRequestServletPath();
 		 
 		return ContextUtils.contextPath + servletPath;
 		
// 		if(fullPath) return scheme + "://" + serverName + ":" + port + contextPath + servletPath;
// 		else return contextPath + servletPath; 	
 	}  	
 	
 	public static void redirect(String url){
		FacesContext fc = FacesContext.getCurrentInstance();
		try { 			
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
 	} 	

 	public static void addMessage(String detail){ 		 
 		addMessage("info", detail, detail);
 	}  	
 	public static void addMessage(String severity, String detail){ 		 
 		addMessage(severity, detail, detail);
 	} 	
 	
 	/**
 	 * 
 	 * @param severity: 4 types: "INFO", "WARN", "ERROR", "FATAL"
 	 * @param summary
 	 * @param detail
 	 */
 	public static void addMessage(String severity, String summary, String detail){ 		 
		FacesMessage msg = null;
		if(severity.equalsIgnoreCase("INFO")) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		} else if(severity.equalsIgnoreCase("WARN")){
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
		} else if(severity.equalsIgnoreCase("ERROR")){
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		} else if(severity.equalsIgnoreCase("FATAL")){
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
		}				
		     		  
		FacesContext.getCurrentInstance().addMessage(null, msg); 		
 	}

 	public static boolean isPostBack() {
 		return FacesContext.getCurrentInstance().isPostback();
 	}
 	public static String getInitParamter(String name){
	    return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(name);
 	} 	
 	public static void debugSession(HttpSession newSession) {
    	System.out.println("debugSession================================" + newSession.getId());    
		// {{ debug-session
		Enumeration<String> e = newSession.getAttributeNames();										
		
		while (e.hasMoreElements()) {
			String name = (String)e.nextElement();
			Object value = newSession.getAttribute(name).toString();																
			
			System.out.println("name is: " + name + " value is: " + value);				   			
		}				
		// }}    	
		System.out.println("================================debugSessionFinished");
    }	
 	public static void debugRequest(HttpServletRequest request) {
    	System.out.println("debugRequest================================");    
		// {{ debug-request
    	  Enumeration<String> parameterList = request.getParameterNames();
    	  while( parameterList.hasMoreElements()) {
    	    String sName = parameterList.nextElement().toString();
    	    if(sName.toLowerCase().startsWith("question")){
    	      String[] sMultiple = request.getParameterValues( sName );
    	      if( 1 >= sMultiple.length )
    	        // parameter has a single value. print it.
    	        System.out.println( sName + " = " + request.getParameter( sName ) + "<br>" );
    	      else
    	        for( int i=0; i<sMultiple.length; i++ )
    	          // if a paramater contains multiple values, print all of them
	        	System.out.println( sName + "[" + i + "] = " + sMultiple[i] + "<br>" );
    	    }
    	  }				
		// }}    	
		System.out.println("================================debugRequestFinished");
    } 	
 	public static int getInt(String str,int def){
 		try{
	 		if(str!=null&&str!=""){
	 			return Integer.valueOf(str);
	 		}
 		}catch(Exception ex){ 			
 		}
 		return def;
 	}
 	public static void systemOutPrint(List<Map<String, String>> lstMap) {
		if (isDebug) {
			if (lstMap != null) {
				// Tinh do dai max cua moi cot
				Map<String, String> mapSize = new HashMap<String, String>();
				if (lstMap.size() > 0) {
					for (Map.Entry<String, String> entry : lstMap.get(0)
							.entrySet()) {
						mapSize.put(entry.getKey(), entry.getKey().length()
								+ "");
					}
					for (Map<String, String> map : lstMap) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							if(entry.getValue()==null){
								continue;
							}
							if (entry.getValue().length() > Integer
									.valueOf(mapSize.get(entry.getKey()))) {
								mapSize.put(entry.getKey(), entry.getValue()
										.length() + "");
							}
						}
					}

					// In ra

					for (Map.Entry<String, String> entry : lstMap.get(0)
							.entrySet()) {
						System.out.print(String.format(
								"%" + mapSize.get(entry.getKey()) + "s",
								entry.getKey())
								+ " | ");
					}
					System.out.println();
					for (Map<String, String> map : lstMap) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							System.out.print(String.format(
									"%" + mapSize.get(entry.getKey()) + "s",
									entry.getValue()) + " | ");
						}
						System.out.println();
					}
				}
			} else {
				System.out.println("List is null.");
			}
		}
	}
 	
 	public static void systemOutPrint(Map<String, String> map) {
 		int dodai=0;
			if (map != null) {				
				for (String key : map.keySet()){
					if(dodai<key.length()){
						dodai = key.length();
					}
				}
					
					// In ra					
					
				for (Map.Entry<String, String> entry : map.entrySet()) {
					System.out.println(String.format("%" + dodai + "s:%s",entry.getKey(),entry.getValue()));
				}
			}		
		
	}
 	
 	public static void clearMenu(){
 		String url = ConfigProxy.categories.get("app").get("contextWeb");
 		url += "/clear";
 		Map<String,String> params = new HashMap<String, String>();
 		 System.out.println("HttpGet:"+url);
 		processHttpGet(url, params);
 	}
 	
 	private static String processHttpGet(String serviceUrl, Map<String, String> params) {
        String result ="";        

        // Essentially return a new Client(), but can be pulled from Spring context
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet  method =new HttpGet(serviceUrl);//See Details Below

        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", new Integer(1000));
        httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        for (Iterator<String> param = params.keySet().iterator(); param.hasNext();) {
            String name = param.next();
            String value = params.get(name);
            method.getParams().setParameter(name, value);
        }
        try {
            HttpResponse response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
    		if(entity!=null){
	            BufferedReader in = null;
	            StringWriter stringOut= new StringWriter();
	            BufferedWriter dumpOut = new BufferedWriter(stringOut,8192);
	            try {
	                 in=new BufferedReader(new InputStreamReader(entity.getContent()));
	                String line = "";
	                while ((line = in.readLine()) != null) {
	                    dumpOut.write(line);
	                    dumpOut.newLine();
	                }
	            } catch (IOException e) {	            	
	               
	            }finally{
	                try {
	                    dumpOut.flush();
	                    dumpOut.close();
	                    if(in!=null) {
	                        in.close();
	                    }
	                } catch (IOException e) {	                	
	                }
	            }
	            result = stringOut.toString();
	        } else {
	        	result = "";
	        }            	
        } catch (IOException ex) {        	
        } finally {
                method.releaseConnection();
        }        
        
        System.out.println("HttpGet:"+serviceUrl);
        return result;                  
               
    }	

	public static String getDomain() {
		return domain;
	}

	public static void setDomain(String domain) {
		ContextUtils.domain = domain;
	}
}