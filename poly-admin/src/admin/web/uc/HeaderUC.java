package admin.web.uc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import vg.core.configproxy.ConfigProxy;
import admin.web.common.AccessControl;
import admin.web.resources.LanguageBundle;
import admin.web.util.ContextUtils;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HeaderUC implements Serializable {
	public Map<String, String> appConf = ConfigProxy.categories.get("app");
	
	private String username = "";
	private Map<String, Boolean> menu = new HashMap<String, Boolean>();
	
	@PostConstruct
	protected void init(){
		if(!ContextUtils.isPostBack()){
			bind();			
		}
	}		
	
	//#region "Function is used by VIEW layer"
	
	public void bind() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();				
        if (AccessControl.IsLoggedIn(request)) {
        	username = (String) AccessControl.getName(request);
        } else {
        	ContextUtils.redirect(ContextUtils.getContextPath() + "/index.xhtml");
        }
	}
	
	public void logout() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        javax.servlet.http.HttpSession session = request.getSession();
		session.invalidate();		
		ContextUtils.redirect(ContextUtils.getContextPath() + "/index.xhtml");
	}		
	
	public void useEnglish() {
		LanguageBundle.changeLanguage("_en");
		ContextUtils.setSession("suffixLanguage", "_en");
		ContextUtils.redirect(ContextUtils.getSession("redirect_path", ContextUtils.getCurrentPath()));
	}
	public void useVietnamese() {
		LanguageBundle.changeLanguage("_vn");
		ContextUtils.setSession("suffixLanguage", "_vn");
		ContextUtils.redirect(ContextUtils.getSession("redirect_path", ContextUtils.getCurrentPath()));
	}
	//#endRegion
	
	//#region "Getter & Setter"

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Boolean> getMenu() {
		return menu;
	}

	public void setMenu(Map<String, Boolean> menu) {
		this.menu = menu;
	}
}