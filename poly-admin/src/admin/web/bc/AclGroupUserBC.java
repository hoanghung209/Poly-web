package admin.web.bc;

import admin.web.common.RewriteURL;
import admin.web.dao.AclUserDAO;
import admin.web.dao.AclUserGroupDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import vg.core.common.StringUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AclGroupUserBC
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String groupId = ContextUtils.getRequest("groupId", "0");
  private List<Map<String, String>> lstUser;
  private List<Map<String, String>> lstFreeUser;
  private String lstUserId;
  private String lstFreeUserId;
  
  @PostConstruct
  protected void init()
  {
    if (!ContextUtils.isPostBack()) {
      if (checkPermission()) {
        bind();
      } else {
        ContextUtils.redirect(RewriteURL.makeAccessForbiddenURL());
      }
    }
  }
  
  private void bind()
  {
    if (Integer.valueOf(this.groupId).intValue() > 0)
    {
      this.lstUser = AclUserDAO.getListByGroup(this.groupId);
      this.lstFreeUser = AclUserDAO.getListFreeByGroup(this.groupId);
    }
  }
  
  public void add()
  {
    if (!StringUtils.isBlank(this.lstUserId))
    {
      boolean succesQuery = true;
      
      String[] userIds = this.lstUserId.split(",");
      for (String id : userIds) {
        if (!AclUserGroupDAO.insert(id, this.groupId, this.userId))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstUserId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.InsertedSucess"));
      }
    }
  }
  
  public void delete()
  {
    if (!StringUtils.isBlank(this.lstFreeUserId))
    {
      boolean succesQuery = true;
      
      String[] userIds = this.lstFreeUserId.split(",");
      for (String id : userIds) {
        if (!AclUserGroupDAO.delete(id, this.groupId))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstFreeUserId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.DeletedSuccess"));
      }
    }
  }
  
  private boolean checkPermission()
  {
    HttpServletRequest req = ContextUtils.getRequest();
    if (!AccessControl.HasPermission("group", req)) {
      return false;
    }
    return true;
  }
  
  public void setId(String id)
  {
    this.groupId = id;
  }
  
  public String getId()
  {
    return this.groupId;
  }
  
  public void setLstUser(List<Map<String, String>> lstUser)
  {
    this.lstUser = lstUser;
  }
  
  public List<Map<String, String>> getLstUser()
  {
    return this.lstUser;
  }
  
  public void setLstFreeUser(List<Map<String, String>> lstFreeUser)
  {
    this.lstFreeUser = lstFreeUser;
  }
  
  public List<Map<String, String>> getLstFreeUser()
  {
    return this.lstFreeUser;
  }
  
  public void setLstUserId(String lstUserId)
  {
    this.lstUserId = lstUserId;
  }
  
  public String getLstUserId()
  {
    return this.lstUserId;
  }
  
  public void setLstFreeUserId(String lstFreeUserId)
  {
    this.lstFreeUserId = lstFreeUserId;
  }
  
  public String getLstFreeUserId()
  {
    return this.lstFreeUserId;
  }
}
