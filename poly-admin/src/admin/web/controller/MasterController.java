package admin.web.controller;

import admin.web.util.ContextUtils;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MasterController
  implements Serializable
{
  private String suffixLanguage = ContextUtils.getSession("suffixLanguage", "_en");
  
  @PostConstruct
  protected void init()
  {
    if (!ContextUtils.isPostBack()) {
      FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(this.suffixLanguage.replace("_", "")));
    }
  }
  
  public String getAuthor()
  {
    return "SEIUNEIL";
  }
}
