package admin.web.bc;

import admin.web.dao.AclUserDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import vg.core.common.StringUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AclUserPasswordBC
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String reqUserId = ContextUtils.getRequest("userId", "0");
  private Map<String, String> userInfo;
  private String oldPassword = "";
  private String newPassword = "";
  private boolean disabled = false;
  private boolean actionPrivate = false;
  
  @PostConstruct
  protected void init()
  {
    if ((!ContextUtils.isPostBack()) && 
      (checkPermission())) {
      bind();
    }
  }
  
  private void bind()
  {
    this.userInfo = AclUserDAO.getById(this.reqUserId);
    if (this.userInfo == null)
    {
      this.disabled = true;
      ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectNotExist"), new Object[] { LanguageBC.getValue("label.user") }));
    }
  }
  
  public void update()
  {
    if ((Integer.valueOf(this.reqUserId).intValue() > 0) && (!StringUtils.isBlank(this.newPassword)))
    {
      if ((this.actionPrivate) && 
        (!StringUtils.md5(this.oldPassword).equals(this.userInfo.get("password"))))
      {
        ContextUtils.addMessage(LanguageBC.getValue("message.oldPasswordWrong"));
        return;
      }
      if (!AclUserDAO.updatePassword(this.reqUserId, this.newPassword, this.userId)) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.UpdatedSuccess"));
      }
    }
  }
  
  private boolean checkPermission()
  {
    if (Integer.valueOf(this.reqUserId).intValue() <= 0)
    {
      this.reqUserId = AccessControl.getUserid(ContextUtils.getRequest());
      this.actionPrivate = true;
    }
    return true;
  }
  
  public String getOldPassword()
  {
    return this.oldPassword;
  }
  
  public void setOldPassword(String oldPassword)
  {
    this.oldPassword = oldPassword;
  }
  
  public String getNewPassword()
  {
    return this.newPassword;
  }
  
  public void setNewPassword(String newPassword)
  {
    this.newPassword = newPassword;
  }
  
  public boolean isDisabled()
  {
    return this.disabled;
  }
  
  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }
  
  public boolean isActionPrivate()
  {
    return this.actionPrivate;
  }
  
  public void setActionPrivate(boolean actionPrivate)
  {
    this.actionPrivate = actionPrivate;
  }
}
