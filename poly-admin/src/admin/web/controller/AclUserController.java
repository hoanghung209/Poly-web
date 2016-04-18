package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.AclUserBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AclUserDAO;
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
import vg.core.common.DateUtils;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AclUserController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{aclUserBean}")
  private AclUserBean aclUserBean;
  private String oldUsername = "";
  
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
    this.aclUserBean.getDisabled().put("disabled-insert", "false");
    this.aclUserBean.getDisabled().put("disabled-update", "true");
    this.aclUserBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND deleted = 0", new Object[0]);
    String sqlOrder = " ORDER BY last_updated_time DESC";
    

    String filterUsername = this.aclUserBean.getFilterUsername();
    if (!StringUtils.isBlank(filterUsername)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "username", "%", DbProxy.antiInjection(filterUsername), "%" });
    }
    String filterFullName = this.aclUserBean.getFilterFullName();
    if (!StringUtils.isBlank(filterFullName)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "full_name", "%", DbProxy.antiInjection(filterFullName), "%" });
    }
    String filterStatus = this.aclUserBean.getFilterStatus();
    if (!StringUtils.isBlank(filterStatus)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "status", DbProxy.antiInjection(filterStatus) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AclUserDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      map.put("created_time_vn", DateUtils.format("dd/MM/yyyy HH:mm:ss", DateUtils.convertMySQL2Date((String)map.get("created_time"))));
      if (((String)map.get("status")).equals("1")) {
        map.put("status_name", "Active");
      } else {
        map.put("status_name", "Lock");
      }
    }
    this.aclUserBean.setList(lstMap);
    setRowOrder(this.aclUserBean.getList());
    
    this.aclUserBean.setCount(AclUserDAO.countList(sqlWhere));
    this.aclUserBean.setPageBar(Paginator.getPageBar(page, this.aclUserBean.getCount(), ""));
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
    if (!AccessControl.HasPermission("user", req)) {
      return false;
    }
    return true;
  }
  
  public void loadList()
  {
    Map<String, String> selectedPage = this.aclUserBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.aclUserBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.aclUserBean.deserializeModel(selectedItem);
      

      this.oldUsername = this.aclUserBean.getUsername();
      
      this.aclUserBean.getDisabled().put("disabled-insert", "true");
      this.aclUserBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void add()
  {
    this.aclUserBean.resetModel();
    
    this.aclUserBean.getDisabled().put("disabled-insert", "true");
    this.aclUserBean.getDisabled().put("disabled-update", "false");
  }
  
  public void update()
  {
    this.aclUserBean.getDisabled().put("disabled-insert", "false");
    this.aclUserBean.getDisabled().put("disabled-update", "true");
    
    this.aclUserBean.setLastUpdatedBy(this.userId);
    if (this.aclUserBean.getSelectedItem() == null)
    {
      if (existName(this.aclUserBean.getUsername()))
      {
        ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectExist"), new Object[] { LanguageBC.getValue("label.userName") }));
        return;
      }
      this.aclUserBean.setCreatedBy(this.userId);
      this.aclUserBean.setId(AclUserDAO.insertIdentity(this.aclUserBean.serializeModel()));
      if (Integer.valueOf(this.aclUserBean.getId()).intValue() <= 0)
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
      this.aclUserBean.setSelectedItem(null);
      if ((!this.oldUsername.equals(this.aclUserBean.getUsername())) && (existName(this.aclUserBean.getUsername())))
      {
        ContextUtils.addMessage(MessageFormat.format(LanguageBC.getValue("message.objectExist"), new Object[] { LanguageBC.getValue("label.userName") }));
        return;
      }
      if (!AclUserDAO.update(this.aclUserBean.serializeModel()))
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
    Map<String, String> selectedItem = this.aclUserBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredDeleteSelection"));
    }
    else if (!AccessControl.HasPermission("acl_user:delete", ContextUtils.getRequest()))
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.CantDeleteBecauseNotPermission"));
    }
    else
    {
      this.aclUserBean.setSelectedItem(null);
      if (!AclUserDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.aclUserBean.resetModel();
    this.aclUserBean.setSelectedItem(null);
    
    this.aclUserBean.getDisabled().put("disabled-insert", "false");
    this.aclUserBean.getDisabled().put("disabled-update", "true");
  }
  
  private boolean existName(String username)
  {
    String sqlWhere = String.format(" AND deleted = 0", new Object[0]);
    String sqlOrder = "";
    

    sqlWhere = String.format("%s AND LOWER(%s) = '%s'", new Object[] { sqlWhere, "username", DbProxy.antiInjection(username.trim().toLowerCase()) });
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AclUserDAO.getList(1, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    return lstMap.size() > 0;
  }
  
  public String getKeywords()
  {
    return "user-keyword";
  }
  
  public String getDescription()
  {
    return "user-description";
  }
  
  public AclUserBean getAclUserBean()
  {
    return this.aclUserBean;
  }
  
  public void setAclUserBean(AclUserBean value)
  {
    this.aclUserBean = value;
  }
}
