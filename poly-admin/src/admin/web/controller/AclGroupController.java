package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.AclGroupBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AclGroupDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import vg.core.common.DateUtils;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AclGroupController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{aclGroupBean}")
  private AclGroupBean aclGroupBean;
  
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
    this.aclGroupBean.getDisabled().put("disabled-insert", "false");
    this.aclGroupBean.getDisabled().put("disabled-update", "true");
    this.aclGroupBean.deserializeModel(new HashMap<String, String>());
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND deleted = 0", new Object[0]);
    String sqlOrder = " ORDER BY last_updated_time DESC";
    

    String filterName = this.aclGroupBean.getFilterName();
    if (!StringUtils.isBlank(filterName)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name", "%", DbProxy.antiInjection(filterName), "%" });
    }
    String filterStatus = this.aclGroupBean.getFilterStatus();
    if (!StringUtils.isBlank(filterStatus)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "status", DbProxy.antiInjection(filterStatus) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AclGroupDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      map.put("created_time_vn", DateUtils.format("dd/MM/yyyy HH:mm:ss", DateUtils.convertMySQL2Date((String)map.get("created_time"))));
      if (((String)map.get("status")).equals("1")) {
        map.put("status_name", "Active");
      } else {
        map.put("status_name", "Lock");
      }
    }
    this.aclGroupBean.setList(lstMap);
    setRowOrder(this.aclGroupBean.getList());
    
    this.aclGroupBean.setCount(AclGroupDAO.countList(sqlWhere));
    this.aclGroupBean.setPageBar(Paginator.getPageBar(page, this.aclGroupBean.getCount(), ""));
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
    if (!AccessControl.HasPermission("group", req)) {
      return false;
    }
    return true;
  }
  
  public void loadList()
  {
    Map<String, String> selectedPage = this.aclGroupBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.aclGroupBean.resetModel();
    
    this.aclGroupBean.getDisabled().put("disabled-insert", "true");
    this.aclGroupBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.aclGroupBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.aclGroupBean.deserializeModel(selectedItem);
      
      this.aclGroupBean.getDisabled().put("disabled-insert", "true");
      this.aclGroupBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void update()
  {
    this.aclGroupBean.getDisabled().put("disabled-insert", "false");
    this.aclGroupBean.getDisabled().put("disabled-update", "true");
    
    this.aclGroupBean.setLastUpdatedBy(this.userId);
    if (this.aclGroupBean.getSelectedItem() == null)
    {
      this.aclGroupBean.setCreatedBy(this.userId);
      this.aclGroupBean.setId(AclGroupDAO.insertIdentity(this.aclGroupBean.serializeModel()));
      if (Integer.valueOf(this.aclGroupBean.getId()).intValue() <= 0)
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
      this.aclGroupBean.setSelectedItem(null);
      if (!AclGroupDAO.update(this.aclGroupBean.serializeModel()))
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
    Map<String, String> selectedItem = this.aclGroupBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredDeleteSelection"));
    }
    else if (!AccessControl.HasPermission("acl_group:delete", ContextUtils.getRequest()))
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.CantDeleteBecauseNotPermission"));
    }
    else
    {
      this.aclGroupBean.setSelectedItem(null);
      if (!AclGroupDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.aclGroupBean.resetModel();
    this.aclGroupBean.setSelectedItem(null);
    
    this.aclGroupBean.getDisabled().put("disabled-insert", "false");
    this.aclGroupBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "group-keyword";
  }
  
  public String getDescription()
  {
    return "group-description";
  }
  
  public AclGroupBean getAclGroupBean()
  {
    return this.aclGroupBean;
  }
  
  public void setAclGroupBean(AclGroupBean value)
  {
    this.aclGroupBean = value;
  }
}
