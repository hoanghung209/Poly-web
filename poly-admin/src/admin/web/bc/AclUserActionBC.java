package admin.web.bc;

import admin.web.common.RewriteURL;
import admin.web.dao.AclActionDAO;
import admin.web.dao.AclActionObjectDAO;
import admin.web.dao.AclUserDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
public class AclUserActionBC
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String regUserId = ContextUtils.getRequest("userId", "0");
  private String objectType = "1";
  private List<Map<String, String>> lstAllowAction;
  private List<Map<String, String>> lstRestrictAction;
  private List<Map<String, String>> lstFreeAction;
  private String lstAllowActionId;
  private String lstRestrictActionId;
  private String lstFreeActionId;
  private String name;
  private String filterAllowName;
  private String filterRestrictName;
  private String filterFreeName;
  
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
  
  private boolean checkPermission()
  {
    HttpServletRequest req = ContextUtils.getRequest();
    if (!AccessControl.HasPermission("user", req)) {
      return false;
    }
    return true;
  }
  
  public void bind()
  {
    if (Integer.valueOf(this.regUserId).intValue() > 0)
    {
      Map<String, String> user = AclUserDAO.getById(this.regUserId);
      if (user == null)
      {
        ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectNotExist"), new Object[] { LanguageBC.getValue("label.user") }));
      }
      else
      {
        this.name = ((String)user.get("username"));
        

        List<Map<String, String>> lstAllowActionRaw = AclActionObjectDAO.getListAllow("1", this.objectType, this.regUserId);
        
        if (StringUtils.isBlank(this.filterAllowName))
        {
          this.lstAllowAction = lstAllowActionRaw;
        }
        else
        {
          this.lstAllowAction = new ArrayList<Map<String, String>>();
          for (Map<String, String> map : lstAllowActionRaw)
          {
           String name = map.get("name").toLowerCase();
            if (name.contains(this.filterAllowName.toLowerCase())) {
              this.lstAllowAction.add(map);
            }
          }
        }
        List<Map<String, String>> lstRestrictActionRaw = AclActionObjectDAO.getListAllow("0", this.objectType, this.regUserId);
        
        if (StringUtils.isBlank(this.filterRestrictName))
        {
          this.lstRestrictAction = lstRestrictActionRaw;
        }
        else
        {
          this.lstRestrictAction = new ArrayList<Map<String, String>>();
          for (Map<String, String> map : lstRestrictActionRaw)
          {
            String name = map.get("name").toLowerCase();
            if (name.contains(this.filterRestrictName.toLowerCase())) {
              this.lstRestrictAction.add(map);
            }
          }
        }
        String lstWorkFlow = "";
        for (Map<String, String> map : lstAllowActionRaw) {
          lstWorkFlow = String.format("%s$%s", new Object[] { lstWorkFlow, map.get("name") });
        }
        for (Map<String, String> map : lstRestrictActionRaw) {
          lstWorkFlow = String.format("%s$%s", new Object[] { lstWorkFlow, map.get("name") });
        }
        lstWorkFlow = lstWorkFlow + "$";
        
        List<Map<String, String>> lstFreeActionRaw = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lstAll = AclActionDAO.getAll();
        for (Map<String, String> map : lstAll)
        {
          String key = String.format("$%s$", new Object[] { map.get("name") });
          if (!lstWorkFlow.contains(key)) {
            lstFreeActionRaw.add(map);
          }
        }
        if (StringUtils.isBlank(this.filterFreeName))
        {
          this.lstFreeAction = lstFreeActionRaw;
        }
        else
        {
          this.lstFreeAction = new ArrayList<Map<String, String>>();
          for (Map<String, String> map : lstFreeActionRaw)
          {
            String name = ((String)map.get("name")).toLowerCase();
            if (name.contains(this.filterFreeName.toLowerCase())) {
              this.lstFreeAction.add(map);
            }
          }
        }
      }
    }
  }
  
  public void allow()
  {
    if (!StringUtils.isBlank(this.lstFreeActionId))
    {
      boolean succesQuery = true;
      
      String[] actionIds = this.lstFreeActionId.split(",");
      for (String actionId : actionIds)
      {
        Map<String, String> map = new HashMap<String, String>();
        
        map.put("action_name", actionId);
        map.put("object_id", this.regUserId);
        map.put("object_type", this.objectType);
        map.put("allow", "1");
        map.put("created_by", this.userId);
        map.put("last_updated_by", this.userId);
        if (!AclActionObjectDAO.insert(map))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstFreeActionId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.InsertedSucess"));
      }
    }
  }
  
  public void restrict()
  {
    if (!StringUtils.isBlank(this.lstFreeActionId))
    {
      boolean succesQuery = true;
      
      String[] actionIds = this.lstFreeActionId.split(",");
      for (String actionId : actionIds)
      {
        Map<String, String> map = new HashMap<String, String>();
        
        map.put("action_name", actionId);
        map.put("object_id", this.regUserId);
        map.put("object_type", this.objectType);
        map.put("allow", "0");
        map.put("created_by", this.userId);
        map.put("last_updated_by", this.userId);
        if (!AclActionObjectDAO.insert(map))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstFreeActionId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.InsertedSucess"));
      }
    }
  }
  
  public void removeAllow()
  {
    if (!StringUtils.isBlank(this.lstAllowActionId))
    {
      boolean succesQuery = true;
      
      String[] actionIds = this.lstAllowActionId.split(",");
      for (String actionId : actionIds) {
        if (!AclActionObjectDAO.delete(actionId, this.regUserId, this.objectType))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstAllowActionId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.DeletedSuccess"));
      }
    }
  }
  
  public void removeRestrict()
  {
    if (!StringUtils.isBlank(this.lstRestrictActionId))
    {
      boolean succesQuery = true;
      
      String[] actionIds = this.lstRestrictActionId.split(",");
      for (String actionId : actionIds) {
        if (!AclActionObjectDAO.delete(actionId, this.regUserId, this.objectType))
        {
          succesQuery = false;
          break;
        }
      }
      bind();
      this.lstRestrictActionId = "";
      if (!succesQuery) {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      } else {
        ContextUtils.addMessage(LanguageBC.getValue("message.DeletedSuccess"));
      }
    }
  }
  
  public List<Map<String, String>> getLstAllowAction()
  {
    return this.lstAllowAction;
  }
  
  public void setLstAllowAction(List<Map<String, String>> lstAllowAction)
  {
    this.lstAllowAction = lstAllowAction;
  }
  
  public List<Map<String, String>> getLstRestrictAction()
  {
    return this.lstRestrictAction;
  }
  
  public void setLstRestrictAction(List<Map<String, String>> lstRestrictAction)
  {
    this.lstRestrictAction = lstRestrictAction;
  }
  
  public List<Map<String, String>> getLstFreeAction()
  {
    return this.lstFreeAction;
  }
  
  public void setLstFreeAction(List<Map<String, String>> lstFreeAction)
  {
    this.lstFreeAction = lstFreeAction;
  }
  
  public String getLstAllowActionId()
  {
    return this.lstAllowActionId;
  }
  
  public void setLstAllowActionId(String lstAllowActionId)
  {
    this.lstAllowActionId = lstAllowActionId;
  }
  
  public String getLstRestrictActionId()
  {
    return this.lstRestrictActionId;
  }
  
  public void setLstRestrictActionId(String lstRestrictActionId)
  {
    this.lstRestrictActionId = lstRestrictActionId;
  }
  
  public String getLstFreeActionId()
  {
    return this.lstFreeActionId;
  }
  
  public void setLstFreeActionId(String lstFreeActionId)
  {
    this.lstFreeActionId = lstFreeActionId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getFilterAllowName()
  {
    return this.filterAllowName;
  }
  
  public void setFilterAllowName(String filterAllowName)
  {
    this.filterAllowName = filterAllowName;
  }
  
  public String getFilterRestrictName()
  {
    return this.filterRestrictName;
  }
  
  public void setFilterRestrictName(String filterRestrictName)
  {
    this.filterRestrictName = filterRestrictName;
  }
  
  public String getFilterFreeName()
  {
    return this.filterFreeName;
  }
  
  public void setFilterFreeName(String filterFreeName)
  {
    this.filterFreeName = filterFreeName;
  }
}
