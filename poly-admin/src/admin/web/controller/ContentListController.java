package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.ContentBean;
import admin.web.common.Enum;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.ContentDAO;
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
public class ContentListController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{contentBean}")
  private ContentBean contentBean;
  
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
    if (!AccessControl.HasPermission("content", req)) {
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
    this.contentBean.getDisabled().put("disabled-insert", "false");
    this.contentBean.getDisabled().put("disabled-update", "true");
    this.contentBean.resetModel();
    String message = ContextUtils.getSession("contentMessage", "");
    if (!StringUtils.isBlank(message))
    {
      ContextUtils.addMessage(message);
      ContextUtils.removeSession("contentMessage");
    }
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND `status` = 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    

    String filterMenuId = this.contentBean.getFilterMenuId();
    if (ContextUtils.getSession("content_menu") != null)
    {
      filterMenuId = ContextUtils.getSession("content_menu").toString();
      this.contentBean.setFilterMenuId(filterMenuId);
      ContextUtils.removeSession("content_menu");
    }
    if (!StringUtils.isBlank(filterMenuId))
    {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "menu_id", DbProxy.antiInjection(filterMenuId) });
      sqlOrder = " ORDER BY ord ASC";
    }
    String filterTitle = this.contentBean.getFilterTitle();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "title_vn", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterContent = this.contentBean.getFilterContent();
    if (ContextUtils.getSession("content_content") != null)
    {
      filterContent = ContextUtils.getSession("content_content").toString();
      this.contentBean.setFilterContent(filterContent);
      ContextUtils.removeSession("content_content");
    }
    if (!StringUtils.isBlank(filterContent)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "content_vn", "%", DbProxy.antiInjection(filterContent), "%" });
    }
    String filterType = this.contentBean.getFilterType();
    if (!StringUtils.isBlank(filterType)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "type", DbProxy.antiInjection(filterType) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = ContentDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("content_vn")).length() > 50) {
        map.put("content_vn", ((String)map.get("content_vn")).substring(0, 50));
      }
      map.put("menu_name", MenuDAO.getMenuTree((String)map.get("menu_id"), "_vn"));
      map.put("type_name", Enum.getContentType(ContextUtils.getInt((String)map.get("type"), 0)));
    }
    this.contentBean.setList(lstMap);
    setRowOrder(this.contentBean.getList());
    
    this.contentBean.setCount(ContentDAO.countList(sqlWhere));
    this.contentBean.setPageBar(Paginator.getPageBar(page, this.contentBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.contentBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.contentBean.resetModel();
    this.contentBean.getDisabled().put("disabled-insert", "true");
    this.contentBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.contentBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      String url = RewriteURL.makeContentURL("tab-1", "11", (String)selectedItem.get("id"));
      ContextUtils.setSession("content_menu", this.contentBean.getFilterMenuId());
      ContextUtils.setSession("content_content", this.contentBean.getFilterContent());
      ContextUtils.redirect(url);
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.contentBean.getSelectedItem();
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
      this.contentBean.setSelectedItem(null);
      if (!ContentDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
  
  public Enum.ContentType[] getContentType()
  {
    return Enum.ContentType.values();
  }
  
  public void cancel()
  {
    this.contentBean.resetModel();
    this.contentBean.setSelectedItem(null);
    this.contentBean.getDisabled().put("disabled-insert", "false");
    this.contentBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "menu-keyword";
  }
  
  public String getDescription()
  {
    return "menu-description";
  }
  
  public ContentBean getcontentBean()
  {
    return this.contentBean;
  }
  
  public void setcontentBean(ContentBean value)
  {
    this.contentBean = value;
  }
}
