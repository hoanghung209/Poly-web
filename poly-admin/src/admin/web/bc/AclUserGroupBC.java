package admin.web.bc;

import admin.web.common.RewriteURL;
import admin.web.dao.AclGroupDAO;
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
public class AclUserGroupBC
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String reqUserId = ContextUtils.getRequest("userId", "0");
  private List<Map<String, String>> lstGroup;
  private List<Map<String, String>> lstFreeGroup;
  private String lstGroupId;
  private String lstFreeGroupId;
  
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
    if (Integer.valueOf(this.reqUserId).intValue() > 0)
    {
      this.lstGroup = AclGroupDAO.getListByUser(this.reqUserId);
      this.lstFreeGroup = AclGroupDAO.getListFreeByUser(this.reqUserId);
    }
  }
  
  public void add()
  {
    if (!StringUtils.isBlank(this.lstGroupId))
    {
      boolean succesQuery = true;
      
      String[] groupIds = this.lstGroupId.split(",");
      for (String groupId : groupIds) {
        if (!AclUserGroupDAO.insert(this.reqUserId, groupId, this.userId))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstGroupId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.InsertedSucess"));
      }
    }
  }
  
  public void delete()
  {
    if (!StringUtils.isBlank(this.lstFreeGroupId))
    {
      boolean succesQuery = true;
      
      String[] groupIds = this.lstFreeGroupId.split(",");
      for (String groupId : groupIds) {
        if (!AclUserGroupDAO.delete(this.reqUserId, groupId))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstFreeGroupId = "";
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
    if (!AccessControl.HasPermission("user", req)) {
      return false;
    }
    return true;
  }
  
  public void setId(String id)
  {
    this.reqUserId = id;
  }
  
  public String getId()
  {
    return this.reqUserId;
  }
  
  public void setLstGroup(List<Map<String, String>> lstGroup)
  {
    this.lstGroup = lstGroup;
  }
  
  public List<Map<String, String>> getLstGroup()
  {
    return this.lstGroup;
  }
  
  public void setLstFreeGroup(List<Map<String, String>> lstFreeGroup)
  {
    this.lstFreeGroup = lstFreeGroup;
  }
  
  public List<Map<String, String>> getLstFreeGroup()
  {
    return this.lstFreeGroup;
  }
  
  public void setLstGroupId(String lstGroupId)
  {
    this.lstGroupId = lstGroupId;
  }
  
  public String getLstGroupId()
  {
    return this.lstGroupId;
  }
  
  public void setLstFreeGroupId(String lstFreeGroupId)
  {
    this.lstFreeGroupId = lstFreeGroupId;
  }
  
  public String getLstFreeGroupId()
  {
    return this.lstFreeGroupId;
  }
}
