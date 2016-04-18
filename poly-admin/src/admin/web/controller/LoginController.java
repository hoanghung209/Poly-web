package admin.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

import vg.core.common.DateUtils;
import vg.core.common.StringUtils;
import vg.core.configproxy.ConfigProxy;
import vg.core.memcacheproxy.MemcacheProxy;

import com.vega.security.AccessControl;

import admin.web.bean.LoginBean;
import admin.web.common.RewriteURL;
import admin.web.util.ContextUtils;
import vg.web.captcha.CaptchaServlet;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {
	public Map<String, String> appConf = ConfigProxy.categories.get("app");
	
	private String keywords = "login-keyword";
	private String description = "Login";
	
	
	private int loginCount = 0;
	private long loginTime = System.currentTimeMillis();
	private int MAX_LOGIN_COUNT = Integer.valueOf(appConf.get("userLogin-maxLoginCount"));
	private int MAX_LOGIN_MINUTES = Integer.valueOf(appConf.get("userLogin-maxLoginMinutes"));
	private int MAX_CAPTCHA_COUNT = Integer.valueOf(appConf.get("userLogin-maxCaptchaCount"));
	
	private boolean captchaEnable = true;
	private boolean loginEnable = true;		
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	@PostConstruct
	protected void init(){
		//ContextUtils.debugSession(ContextUtils.getRequest().getSession()); // debugSession
		
		if(!ContextUtils.isPostBack()){
			//loginBean.setUseCaptcha(false);
			bind();
		}
	}		
	
	protected void bind(){	
		long totalMinutes = getTotalMinutes();
		if (loginCount < MAX_LOGIN_COUNT || totalMinutes > MAX_LOGIN_MINUTES) {
			captchaEnable = (loginCount >= MAX_CAPTCHA_COUNT);
			
			loginEnable = true;			
		} else {
			loginEnable = false;
			ContextUtils.addMessage(String.format("Chờ %s phút ", (MAX_LOGIN_MINUTES - totalMinutes)));
		}

		// {{ logged or detected		
		if(AccessControl.IsLoggedIn(ContextUtils.getRequest())) {
			if(syncLogin()) {
				redirectBackURL();	
			}			
		};
		// }}
	}	
	private boolean syncLogin(){
		if(!Boolean.valueOf(appConf.get("syncLogin"))) {
			return true;
		}
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpSession newSession = request.getSession();
	
		String mKey = String.valueOf(newSession.getAttribute("mKey"));
		String mValue = String.valueOf(MemcacheProxy.get(mKey));
		
		ContextUtils.systemOutPrint(String.format("syncLogin:%s|%s|%s", mKey, mValue, newSession.getAttribute("mValue")));
		
		return mValue.equals(newSession.getAttribute("mValue"));
	}
	
	private long getTotalMinutes(){
		String sLoginCount = ContextUtils.getSession("loginCount", "");
		String sLoginTime = ContextUtils.getSession("loginTime", "");
		
		if(!StringUtils.isBlank(sLoginCount)) {
			loginCount = Integer.valueOf(sLoginCount);
		}
		if(!StringUtils.isBlank(sLoginTime)) {
			loginTime = Long.valueOf(sLoginTime);
		}		
		return (System.currentTimeMillis() - loginTime) / 1000 / 60;
	}
    private void redirectBackURL() {
    	String backURL = ContextUtils.getSession("backURL", "");    
    	
    	if(!StringUtils.isBlank(backURL)) {
    		ContextUtils.redirect(backURL);
    	} else {    		
    		ContextUtils.redirect(RewriteURL.makeHomeURL());
    	}
    } 
    
	//#region "Function is used by VIEW layer"
	
	public void login() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                
		long totalMinutes = getTotalMinutes();		
        if (loginCount < MAX_LOGIN_COUNT || totalMinutes > MAX_LOGIN_MINUTES) {
        	//check-captcha
        	if(captchaEnable) {
        		String sessionCaptcha = ContextUtils.getSession(CaptchaServlet.CAPTCHA_KEY, "");
        		
        		String code = loginBean.getCapcha();
        		if(StringUtils.isBlank(code) || !code.equalsIgnoreCase(sessionCaptcha)) {
        			code = "";
        			
        			ContextUtils.setSession("loginCount", (loginCount + 1));
        			ContextUtils.setSession("loginTime", System.currentTimeMillis());        		
        		
        			ContextUtils.addMessage("(Captcha code is not exactly)!. System will block your account in 10 minutes if Login failed 5 times continuously. ')");
        			RequestContext.getCurrentInstance().execute("ChangeRandomImage('imageCapcha', '(Captcha code is not exactly)!. System will block your account in 10 minutes if Login failed 5 times continuously. ')");
        			return;
        		} else {
        			RequestContext.getCurrentInstance().execute("ChangeRandomImage('imageCapcha')");
        		}        		
        	}
        	
        	// {{ login 
        	String password = loginBean.getPassword();
        	AccessControl.SignIn(loginBean.getUsername(), password, request);
        	if(!AccessControl.IsLoggedIn(request))
        	{
	   			ContextUtils.setSession("loginCount", (loginCount + 1));
	   			ContextUtils.setSession("loginTime", System.currentTimeMillis());	   		
	   			
	   			captchaEnable = (loginCount >= MAX_CAPTCHA_COUNT);
	   			ContextUtils.addMessage("Username or Password is invalid");        		
        	} else {        		        		
        		javax.servlet.http.HttpSession newSession = request.getSession();        		
        		newSession.setAttribute("username", loginBean.getUsername());
        		
        		
        		// {{ syncLogin
        		if(Boolean.valueOf(appConf.get("syncLogin"))) {
            		// {{ Session HTTP
            		String ip = ContextUtils.getRemoteIp();
            		String lastLoginTime = DateUtils.format("yyyyMMddHHmmss", new Date());
            		
            		String mKey = StringUtils.md5(String.format("%s|%s","movitel.admin", loginBean.getUsername()));
            		String mValue = StringUtils.md5(String.format("%s|%s", ip, lastLoginTime));
            		        		    			    			    			    
        			newSession.setAttribute("mKey", mKey);
        			newSession.setAttribute("mValue", mValue);
    	    		// }}
    	    		
    	    		// {{ Session Memcache        		
        			
        			ContextUtils.systemOutPrint(String.format("put memecache: %s|%s", mKey, mValue));
        			
    	    		MemcacheProxy.put(mKey, mValue);
    	    		// }}        		
        		}
        		// }}
        		
        		//ContextUtils.debugSession(newSession); // debugSession
        		
        		String url = "";
				if (newSession.getAttribute("redirect_path") != null) {
					url = newSession.getAttribute("redirect_path").toString();
				} else {
					url = ContextUtils.getContextPath() + "/pages/welcome/index.xhtml";
				}
				System.out.println("di dau :"+url);
				
				try {
					HttpServletResponse response= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					response.sendRedirect(url);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}     
        	// }}
        	
        } else {
			loginEnable = false;
			ContextUtils.addMessage(String.format("Please wait %s minutes ", (MAX_LOGIN_MINUTES - totalMinutes)));        	
        }
	}
	
    
	//#endRegion
	
	//#region "Getter & Setter"
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}


	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public boolean isCaptchaEnable() {
		return captchaEnable;
	}

	public void setCaptchaEnable(boolean captchaEnable) {
		this.captchaEnable = captchaEnable;
	}

	public boolean isLoginEnable() {
		return loginEnable;
	}

	public void setLoginEnable(boolean loginEnable) {
		this.loginEnable = loginEnable;
	}	
	//#endRegion
}