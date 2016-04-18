package admin.web.uc;

import admin.web.resources.LanguageBundle;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vg.core.configproxy.ConfigProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HeaderUC
  implements Serializable
{
  public Map<String, String> appConf = ConfigProxy.categories.get("app");
  private String username = "";
  private Map<String, Boolean> menu = new HashMap<String, Boolean>();
  
  @PostConstruct
  protected void init()
  {
    if (!ContextUtils.isPostBack())
    {
      bind();
      bindMenu();
    }
  }
  
  public void bind()
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if (AccessControl.IsLoggedIn(request)) {
      this.username = AccessControl.getName(request);
    } else {
      ContextUtils.redirect(ContextUtils.getContextPath() + "/index.xhtml");
    }
  }
  
  public void bindMenu()
  {
    HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    

    this.menu.put("menu", Boolean.valueOf(AccessControl.HasPermission("menu", req)));
    this.menu.put("content", Boolean.valueOf(AccessControl.HasPermission("content", req)));
    this.menu.put("news", Boolean.valueOf(AccessControl.HasPermission("news", req)));
    this.menu.put("tab-content", Boolean.valueOf((Boolean.valueOf(((Boolean)this.menu.get("menu")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("content")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("news")).booleanValue()).booleanValue())));
    

    this.menu.put("course", Boolean.valueOf(AccessControl.HasPermission("course", req)));
    this.menu.put("scheduler", Boolean.valueOf(AccessControl.HasPermission("scheduler", req)));
    this.menu.put("tab-course", Boolean.valueOf((Boolean.valueOf(((Boolean)this.menu.get("course")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("scheduler")).booleanValue()).booleanValue())));
    

    this.menu.put("album", Boolean.valueOf(AccessControl.HasPermission("album", req)));
    this.menu.put("file", Boolean.valueOf(AccessControl.HasPermission("file", req)));
    this.menu.put("ads", Boolean.valueOf(AccessControl.HasPermission("ads", req)));
    this.menu.put("tab-media", Boolean.valueOf((Boolean.valueOf(((Boolean)this.menu.get("album")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("file")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("ads")).booleanValue()).booleanValue())));
    

    this.menu.put("register", Boolean.valueOf(AccessControl.HasPermission("register", req)));
    this.menu.put("feedback", Boolean.valueOf(AccessControl.HasPermission("feedback", req)));
    this.menu.put("faq", Boolean.valueOf(AccessControl.HasPermission("faq", req)));
    this.menu.put("customer", Boolean.valueOf(AccessControl.HasPermission("customer", req)));
    if ((!Boolean.valueOf(((Boolean)this.menu.get("register")).booleanValue()).booleanValue()) && (!Boolean.valueOf(((Boolean)this.menu.get("feedback")).booleanValue()).booleanValue())) {}
    this.menu.put("tab-cskh", Boolean.valueOf(Boolean.valueOf((((Boolean)this.menu.get("faq")).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("customer")).booleanValue()).booleanValue())).booleanValue()));
    

    this.menu.put("action", Boolean.valueOf(AccessControl.HasPermission("action", req)));
    this.menu.put("user", Boolean.valueOf(AccessControl.HasPermission("user", req)));
    this.menu.put("group", Boolean.valueOf(AccessControl.HasPermission("group", req)));
    this.menu.put("tab-member", Boolean.valueOf((Boolean.valueOf(((Boolean)this.menu.get("action")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("user")).booleanValue()).booleanValue()) || (Boolean.valueOf(((Boolean)this.menu.get("group")).booleanValue()).booleanValue())));
  }
  
  public void logout()
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    HttpSession session = request.getSession();
    session.invalidate();
    ContextUtils.redirect(ContextUtils.getContextPath() + "/index.xhtml");
  }
  
  public void useEnglish()
  {
    LanguageBundle.changeLanguage("_en");
    ContextUtils.setSession("suffixLanguage", "_en");
    ContextUtils.redirect(ContextUtils.getSession("redirect_path", ContextUtils.getCurrentPath()));
  }
  
  public void useVietnamese()
  {
    LanguageBundle.changeLanguage("_vn");
    ContextUtils.setSession("suffixLanguage", "_vn");
    ContextUtils.redirect(ContextUtils.getSession("redirect_path", ContextUtils.getCurrentPath()));
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public Map<String, Boolean> getMenu()
  {
    return this.menu;
  }
  
  public void setMenu(Map<String, Boolean> menu)
  {
    this.menu = menu;
  }
}
