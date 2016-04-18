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
import vg.core.configproxy.ConfigProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContextUtils  implements Serializable
{
  public static Boolean isDebug = Boolean.valueOf(ConfigProxy.categories.get("app").get("isDebug"));
  public static String contextPath = ConfigProxy.categories.get("app").get("contextPath");
  private static String domain = ConfigProxy.categories.get("file").get("server");
  
  public static void systemOutPrint(String input)
  {
    if (isDebug.booleanValue()) {
      System.out.println(input);
    }
  }
  
  public static String makeSessionKey()
  {
    Random random = new Random();
    String cDate = DateUtils.format("yyyyMMddHHmmss", new Date());
    String token = String.format("%s%s%s%s%s", new Object[] { cDate.substring(0, 3), Integer.valueOf(random.nextInt(100)), cDate.substring(4, 7), cDate.substring(8), Integer.valueOf(random.nextInt(100)) });
    String encode = vg.core.common.StringUtils.md5(token);
    
    setSession("vgSessionKey", encode);
    return encode;
  }
  
  public static String makeSessionKey(HttpServletRequest req)
  {
    Random random = new Random();
    String cDate = DateUtils.format("yyyyMMddHHmmss", new Date());
    String token = String.format("%s%s%s%s%s", new Object[] { cDate.substring(0, 3), Integer.valueOf(random.nextInt(100)), cDate.substring(4, 7), cDate.substring(8), Integer.valueOf(random.nextInt(100)) });
    String encode = vg.core.common.StringUtils.md5(token);
    
    System.out.println("encode:" + encode);
    
    setSession(req, "vgSessionKey", encode);
    return encode;
  }
  
  public static boolean authSessionKey(HttpServletRequest req, String token)
  {
    String sessionKey = getSession(req, "vgSessionKey", "");
    
    return (!vg.core.common.StringUtils.isBlank(token)) && (!vg.core.common.StringUtils.isBlank(sessionKey)) && (sessionKey.equals(token));
  }
  
  public static String getRemoteIp()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    
    String ip = request.getHeader("X-Forwarded-For");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
  
  public static String getRemoteIp(HttpServletRequest req)
  {
    String ip = req.getHeader("X-Forwarded-For");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = req.getHeader("Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = req.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = req.getHeader("HTTP_CLIENT_IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = req.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = req.getRemoteAddr();
    }
    return ip;
  }
  
  public static HttpServletRequest getRequest()
  {
    return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }
  
  public static int getRequest(String key, int defaultValue)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    Map<String, String> request = fc.getExternalContext().getRequestParameterMap();
    int value = 0;
    try
    {
      String rValue = (String)request.get(key);
      value = vg.core.common.StringUtils.isBlank(rValue) ? defaultValue : Integer.parseInt(rValue);
    }
    catch (Exception e)
    {
      value = defaultValue;
    }
    return value;
  }
  
  public static String getRequest(String key, String defaultValue)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    
    Map<String, String> request = fc.getExternalContext().getRequestParameterMap();
    String value = "";
    try
    {
      String rValue = (String)request.get(key);
      value = vg.core.common.StringUtils.isBlank(rValue) ? defaultValue : rValue;
    }
    catch (Exception e)
    {
      value = defaultValue;
    }
    return value;
  }
  
  public static String getRequestTemplate()
  {
    return getRequest("template", "default");
  }
  
  public static String getSessionId()
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
    HttpSession session = request.getSession();
    
    return session.getId();
  }
  
  public static Object getSession(String key)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
    HttpSession session = request.getSession();
    
    return session.getAttribute(key);
  }
  
  public static void removeSession(String key)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
    HttpSession session = request.getSession();
    
    session.removeAttribute(key);
  }
  
  public static String getSession(String key, String defaultValue)
  {
    try
    {
      FacesContext fc = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
      HttpSession session = request.getSession();
      Object objValue = session.getAttribute(key);
      if (objValue != null) {
        return String.valueOf(objValue);
      }
      return defaultValue;
    }
    catch (Exception ex) {}
    return defaultValue;
  }
  
  public static void setSession(String key, Object value)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
    HttpSession session = request.getSession();
    
    session.setAttribute(key, value);
  }
  
  public static String getSessionId(HttpServletRequest req)
  {
    HttpSession session = req.getSession();
    return session.getId();
  }
  
  public static String getSession(HttpServletRequest req, String key, String defaultValue)
  {
    HttpSession session = req.getSession();
    Object objValue = session.getAttribute(key);
    if (objValue != null) {
      return String.valueOf(objValue);
    }
    return defaultValue;
  }
  
  public static void setSession(HttpServletRequest req, String key, Object value)
  {
    HttpSession session = req.getSession(true);
    session.setAttribute(key, value);
  }
  
  public static String getContextPath()
  {
    try
    {
      HttpServletRequest httprequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      String url = httprequest.getRequestURL().toString();
      String uri = httprequest.getRequestURI();
      int inx = url.indexOf(uri);
      if (inx > 0)
      {
        int ini = uri.substring(1).indexOf("/");
        contextPath = url.substring(0, inx) + "/" + uri.substring(1, ini + 1);
      }
    }
    catch (Exception ex)
    {
      return "localhost:8080/admin";
    }
    return contextPath;
  }
  
  public static String getContextPath(boolean fullPath)
  {
    return contextPath;
  }
  
  public static String getCurrentPath()
  {
    try
    {
      HttpServletRequest httprequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      String url = httprequest.getRequestURL().toString();
      String uri = httprequest.getRequestURI();
      int inx = url.indexOf(uri);
      if (inx > 0)
      {
        int ini = uri.substring(1).indexOf("/");
        contextPath = url.substring(0, inx) + "/" + uri.substring(1, ini + 1);
      }
    }
    catch (Exception ex)
    {
      return "localhost:8080/admin";
    }
    return contextPath;
  }
  
  public static String getCurrentPath(boolean fullPath)
  {
    ExternalContext xContext = FacesContext.getCurrentInstance().getExternalContext();
    




    String servletPath = xContext.getRequestServletPath();
    
    return contextPath + servletPath;
  }
  
  public static void redirect(String url)
  {
    FacesContext fc = FacesContext.getCurrentInstance();
    try
    {
      fc.getExternalContext().redirect(url);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void addMessage(String detail)
  {
    addMessage("info", detail, detail);
  }
  
  public static void addMessage(String severity, String detail)
  {
    addMessage(severity, detail, detail);
  }
  
  public static void addMessage(String severity, String summary, String detail)
  {
    FacesMessage msg = null;
    if (severity.equalsIgnoreCase("INFO")) {
      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    } else if (severity.equalsIgnoreCase("WARN")) {
      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
    } else if (severity.equalsIgnoreCase("ERROR")) {
      msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
    } else if (severity.equalsIgnoreCase("FATAL")) {
      msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
  
  public static boolean isPostBack()
  {
    return FacesContext.getCurrentInstance().isPostback();
  }
  
  public static String getInitParamter(String name)
  {
    return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(name);
  }
  
  public static void debugSession(HttpSession newSession)
  {
    System.out.println("debugSession================================" + newSession.getId());
    
    Enumeration<String> e = newSession.getAttributeNames();
    while (e.hasMoreElements())
    {
      String name = (String)e.nextElement();
      Object value = newSession.getAttribute(name).toString();
      
      System.out.println("name is: " + name + " value is: " + value);
    }
    System.out.println("================================debugSessionFinished");
  }
  
  public static void debugRequest(HttpServletRequest request)
  {
    System.out.println("debugRequest================================");
    
    Enumeration<String> parameterList = request.getParameterNames();
    while (parameterList.hasMoreElements())
    {
      String sName = ((String)parameterList.nextElement()).toString();
      if (sName.toLowerCase().startsWith("question"))
      {
        String[] sMultiple = request.getParameterValues(sName);
        if (1 >= sMultiple.length) {
          System.out.println(sName + " = " + request.getParameter(sName) + "<br>");
        } else {
          for (int i = 0; i < sMultiple.length; i++) {
            System.out.println(sName + "[" + i + "] = " + sMultiple[i] + "<br>");
          }
        }
      }
    }
    System.out.println("================================debugRequestFinished");
  }
  
  public static int getInt(String str, int def)
  {
    try
    {
      if ((str != null) && (str != "")) {
        return Integer.valueOf(str).intValue();
      }
    }
    catch (Exception localException) {}
    return def;
  }  
  
  public static void systemOutPrint(Map<String, String> map)
  {
    int dodai = 0;
    if (map != null)
    {
      for (String key : map.keySet()) {
        if (dodai < key.length()) {
          dodai = key.length();
        }
      }
      for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println(String.format("%" + dodai + "s:%s", new Object[] { entry.getKey(), entry.getValue() }));
      }
    }
  }
  
  public static void clearMenu()
  {
    String url = ConfigProxy.categories.get("app").get("contextWeb");
    url = url + "/clear";
    Map<String, String> params = new HashMap<String, String>();
    System.out.println("HttpGet:" + url);
    processHttpGet(url, params);
  }
  
  private static String processHttpGet(String serviceUrl, Map<String, String> params)
  {
    String result = "";
    

    HttpClient httpclient = new DefaultHttpClient();
    HttpGet method = new HttpGet(serviceUrl);
    
    httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
    httpclient.getParams().setParameter("http.socket.timeout", new Integer(1000));
    httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
    for (Iterator<String> param = params.keySet().iterator(); param.hasNext();)
    {
      String name = (String)param.next();
      String value = (String)params.get(name);
      method.getParams().setParameter(name, value);
    }
    try
    {
      HttpResponse response = httpclient.execute(method);
      HttpEntity entity = response.getEntity();
      if (entity != null)
      {
        BufferedReader in = null;
        StringWriter stringOut = new StringWriter();
        BufferedWriter dumpOut = new BufferedWriter(stringOut, 8192);
        try
        {
          in = new BufferedReader(new InputStreamReader(entity.getContent()));
          String line = "";
          while ((line = in.readLine()) != null)
          {
            dumpOut.write(line);
            dumpOut.newLine();
          }
        }
        catch (IOException localIOException)
        {
          try
          {
            dumpOut.flush();
            dumpOut.close();
            if (in != null) {
              in.close();
            }
          }
          catch (IOException localIOException1) {}
        }
        finally
        {
          try
          {
            dumpOut.flush();
            dumpOut.close();
            if (in != null) {
              in.close();
            }
          }
          catch (IOException localIOException2) {}
        }
        result = stringOut.toString();
      }
      else
      {
        result = "";
      }
    }
    catch (IOException localIOException4) {}finally
    {
      method.releaseConnection();
    }
    System.out.println("HttpGet:" + serviceUrl);
    return result;
  }
  
  public static String getDomain()
  {
    return domain;
  }
  
  public static void setDomain(String domain1)
  {
    domain = domain1;
  }
  
  public static String convertToSlug(String input)
  {
    String str = input.toLowerCase();
    str = str.replaceAll("đ", "d");
    str = org.apache.commons.lang3.StringUtils.stripAccents(str);
    str = str.toLowerCase();
    str = str.replaceAll("[^a-zA-Z0-9\\s]", "");
    str = str.replaceAll("-", "");
    str = str.replaceAll("\\\\", "-");
    str = str.replaceAll("\\s+", "-");
    str = str.replaceAll("/", "-");
    return str;
  }
}
