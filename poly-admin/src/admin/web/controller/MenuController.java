package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.MenuBean;
import admin.web.common.Enum;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
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
public class MenuController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String reqParentId = ContextUtils.getRequest("parentId", "-10");
  @ManagedProperty("#{menuBean}")
  private MenuBean menuBean;
  
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
    if (!AccessControl.HasPermission("menu", req)) {
      return false;
    }
    return true;
  }
  
  protected void bind()
  {
    loadDetail();
    loadList(1);
  }
  
  private void loadDetail()
  {
    this.menuBean.getDisabled().put("disabled-insert", "false");
    this.menuBean.getDisabled().put("disabled-update", "true");
    this.menuBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status <> -1 AND id > 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    if (Integer.valueOf(this.reqParentId).intValue() >= -1) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "parent", DbProxy.antiInjection(this.reqParentId) });
    }
    String filterNameVn = this.menuBean.getFilterNameVn();
    if (!StringUtils.isBlank(filterNameVn)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name_vn", "%", DbProxy.antiInjection(filterNameVn), "%" });
    }
    String filterNameEn = this.menuBean.getFilterNameEn();
    if (!StringUtils.isBlank(filterNameEn)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name_en", "%", DbProxy.antiInjection(filterNameEn), "%" });
    }
    String filterPosition = this.menuBean.getFilterPosition();
    if (!StringUtils.isBlank(filterPosition)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "position", DbProxy.antiInjection(filterPosition) });
    }
    String filterStatus = this.menuBean.getFilterStatus();
    if (!StringUtils.isBlank(filterStatus)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "status", DbProxy.antiInjection(filterStatus) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = MenuDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      map.put("status_name", Enum.getStatus(Integer.valueOf((String)map.get("status")).intValue()));
      map.put("position_name", Enum.getPosition(Integer.valueOf((String)map.get("position")).intValue()));
    }
    this.menuBean.setList(lstMap);
    setRowOrder(this.menuBean.getList());
    
    this.menuBean.setCount(MenuDAO.countList(sqlWhere));
    this.menuBean.setPageBar(Paginator.getPageBar(page, this.menuBean.getCount(), ""));
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
  
  public void loadList()
  {
    Map<String, String> selectedPage = this.menuBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.menuBean.resetModel();
    this.menuBean.getDisabled().put("disabled-insert", "true");
    this.menuBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.menuBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.menuBean.deserializeModel(selectedItem);
      this.menuBean.getDisabled().put("disabled-insert", "true");
      this.menuBean.getDisabled().put("disabled-update", "false");
      this.menuBean.setParentName(MenuDAO.getFieldById(this.menuBean.getParent(), "name_vn"));
    }
  }
  
  public void update()
  {
    this.menuBean.getDisabled().put("disabled-insert", "false");
    this.menuBean.getDisabled().put("disabled-update", "true");
    
    this.menuBean.setSlugVn(ContextUtils.convertToSlug(this.menuBean.getNameVn()));
    this.menuBean.setSlugEn(ContextUtils.convertToSlug(this.menuBean.getNameEn()));
    if (this.menuBean.getSelectedItem() == null)
    {
      this.menuBean.setCreatedBy(this.userId);
      this.menuBean.setUrl("intro");
      if (!MenuDAO.insert(this.menuBean.serializeModel()))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        bind();
        ContextUtils.addMessage("Thêm mới thành công");
      }
    }
    else
    {
      this.menuBean.setSelectedItem(null);
      if (this.menuBean.getId().equals("-1"))
      {
        ContextUtils.addMessage("Không được phép thay đổi root");
        return;
      }
      int inpage = 0;
      try
      {
        inpage = Integer.valueOf(this.menuBean.getInpage()).intValue();
      }
      catch (Exception ex)
      {
        this.menuBean.setInpage("0");
      }
      if (inpage > 0)
      {
        Map<String, String> menuLink = MenuDAO.getById(String.valueOf(inpage));
        this.menuBean.setUrl((String)menuLink.get("url"));
      }
      if (!MenuDAO.update(this.menuBean.serializeModel()))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        bind();
        ContextUtils.addMessage("Cập nhật thành công");
      }
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.menuBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      if (((String)selectedItem.get("id")).equals("-1"))
      {
        ContextUtils.addMessage("Không được phép thay đổi root");
        return;
      }
      this.menuBean.setSelectedItem(null);
      if (!MenuDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        bind();
        ContextUtils.addMessage("Xóa thành công");
      }
    }
  }
  
  public void cancel()
  {
    this.menuBean.resetModel();
    this.menuBean.setSelectedItem(null);
    this.menuBean.getDisabled().put("disabled-insert", "false");
    this.menuBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "menu-keyword";
  }
  
  public String getDescription()
  {
    return "menu-description";
  }
  
  public MenuBean getmenuBean()
  {
    return this.menuBean;
  }
  
  public void setmenuBean(MenuBean value)
  {
    this.menuBean = value;
  }
}
