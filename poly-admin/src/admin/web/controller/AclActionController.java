package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.AclActionBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AclActionDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AclActionController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{aclActionBean}")
  private AclActionBean aclActionBean;
  
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
  
  protected void bind()
  {
    loadDetail();
    loadList(1);
  }
  
  private void loadDetail()
  {
    this.aclActionBean.getDisabled().put("disabled-insert", "false");
    this.aclActionBean.getDisabled().put("disabled-update", "true");
    this.aclActionBean.getDisabled().put("disabled-update-key", "true");
    this.aclActionBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = "";
    String sqlOrder = " ORDER BY last_updated_time DESC";
    

    String filterName = this.aclActionBean.getFilterName();
    if (!StringUtils.isBlank(filterName)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name", "%", DbProxy.antiInjection(filterName), "%" });
    }
    String filterDescription = this.aclActionBean.getFilterDescription();
    if (!StringUtils.isBlank(filterDescription)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "description", "%", DbProxy.antiInjection(filterDescription), "%" });
    }
    String filterStatus = this.aclActionBean.getFilterStatus();
    if (!StringUtils.isBlank(filterStatus)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "status", DbProxy.antiInjection(filterStatus) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AclActionDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap) {
      map.put("id", (String)map.get("name"));
    }
    this.aclActionBean.setList(lstMap);
    setRowOrder(this.aclActionBean.getList());
    
    this.aclActionBean.setCount(AclActionDAO.countList(sqlWhere));
    this.aclActionBean.setPageBar(Paginator.getPageBar(page, this.aclActionBean.getCount(), ""));
  }
  
  private void setRowOrder(List<Map<String, String>> lstMap)
  {
    int i = 0;
    if (lstMap == null) {
      return;
    }
    for (Map<String, String> map : lstMap)
    {
      if (i % 2 == 0) {
        map.put("rowOrder", "odd");
      } else {
        map.put("rowOrder", "");
      }
      i++;
    }
  }
  
  private boolean checkPermission()
  {
    HttpServletRequest req = ContextUtils.getRequest();
    if (!AccessControl.HasPermission("action", req)) {
      return false;
    }
    return true;
  }
  
  public void loadList()
  {
    Map<String, String> selectedPage = this.aclActionBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.aclActionBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.aclActionBean.deserializeModel(selectedItem);
      
      this.aclActionBean.getDisabled().put("disabled-insert", "true");
      this.aclActionBean.getDisabled().put("disabled-update", "false");
      this.aclActionBean.getDisabled().put("disabled-update-key", "true");
    }
  }
  
  public void add()
  {
    this.aclActionBean.resetModel();
    
    this.aclActionBean.getDisabled().put("disabled-insert", "true");
    this.aclActionBean.getDisabled().put("disabled-update", "false");
    this.aclActionBean.getDisabled().put("disabled-update-key", "false");
  }
  
  public void update()
  {
    this.aclActionBean.getDisabled().put("disabled-insert", "false");
    this.aclActionBean.getDisabled().put("disabled-update", "true");
    this.aclActionBean.getDisabled().put("disabled-update-key", "true");
    
    this.aclActionBean.setLastUpdatedBy(this.userId);
    if (this.aclActionBean.getSelectedItem() == null)
    {
      if (AclActionDAO.getById(this.aclActionBean.getName()) != null)
      {
        bind();
        ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectExist"), new Object[] { LanguageBC.getValue("label.action") }));
        return;
      }
      this.aclActionBean.setCreatedBy(this.userId);
      this.aclActionBean.setId(AclActionDAO.insert(this.aclActionBean.serializeModel()) ? this.aclActionBean.getName() : "");
      if (StringUtils.isBlank(this.aclActionBean.getId()))
      {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      }
      else
      {
        bind();
        ContextUtils.addMessage(LanguageBC.getValue("message.InsertedSucess"));
      }
    }
    else
    {
      this.aclActionBean.setSelectedItem(null);
      if ((!this.aclActionBean.getId().equals(this.aclActionBean.getName())) && 
        (AclActionDAO.getById(this.aclActionBean.getName()) != null))
      {
        bind();
        ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectExist"), new Object[] { LanguageBC.getValue("label.action") }));
        return;
      }
      this.aclActionBean.setLastUpdatedBy(this.userId);
      this.aclActionBean.setId(this.aclActionBean.getName());
      if (!AclActionDAO.update(this.aclActionBean.serializeModel()))
      {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      }
      else
      {
        bind();
        ContextUtils.addMessage(LanguageBC.getValue("message.UpdatedSuccess"));
      }
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.aclActionBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredDeleteSelection"));
    }
    else if (!AccessControl.HasPermission("acl_action:delete", ContextUtils.getRequest()))
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.CantDeleteBecauseNotPermission"));
    }
    else
    {
      this.aclActionBean.setSelectedItem(null);
      if (!AclActionDAO.delete((String)selectedItem.get("id")))
      {
        ContextUtils.addMessage(LanguageBC.getValue("message.SystemBusy"));
      }
      else
      {
        bind();
        ContextUtils.addMessage(LanguageBC.getValue("message.DeletedSuccess"));
      }
    }
  }
  
  public void cancel()
  {
    this.aclActionBean.resetModel();
    this.aclActionBean.setSelectedItem(null);
    
    this.aclActionBean.getDisabled().put("disabled-insert", "false");
    this.aclActionBean.getDisabled().put("disabled-update", "true");
    this.aclActionBean.getDisabled().put("disabled-update-key", "true");
  }
  
  public String getKeywords()
  {
    return "action-keyword";
  }
  
  public String getDescription()
  {
    return "action-description";
  }
  
  public AclActionBean getAclActionBean()
  {
    return this.aclActionBean;
  }
  
  public void setAclActionBean(AclActionBean value)
  {
    this.aclActionBean = value;
  }
}
