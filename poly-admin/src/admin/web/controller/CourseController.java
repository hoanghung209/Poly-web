package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.CourseBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.CourseDAO;
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

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CourseController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{courseBean}")
  private CourseBean courseBean;
  
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
    if (!AccessControl.HasPermission("course", req)) {
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
    this.courseBean.getDisabled().put("disabled-insert", "false");
    this.courseBean.getDisabled().put("disabled-update", "true");
    this.courseBean.getDisabled().put("disabled-show", "false");
    this.courseBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status = 0", new Object[0]);
    String sqlOrder = " ORDER BY id DESC";
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = CourseDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap) {
      map.put("menu_name", MenuDAO.getMenuTree((String)map.get("menu_id"), "_vn"));
    }
    this.courseBean.setList(lstMap);
    setRowOrder(this.courseBean.getList());
    
    this.courseBean.setCount(CourseDAO.countList(sqlWhere));
    this.courseBean.setPageBar(Paginator.getPageBar(page, this.courseBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.courseBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.courseBean.resetModel();
    this.courseBean.getDisabled().put("disabled-insert", "true");
    this.courseBean.getDisabled().put("disabled-update", "false");
    this.courseBean.getDisabled().put("disabled-show", "true");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.courseBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.courseBean.deserializeModel(selectedItem);
      this.courseBean.setMenuName(MenuDAO.getFieldById((String)selectedItem.get("menu_id"), "name_vn"));
      this.courseBean.getDisabled().put("disabled-insert", "true");
      this.courseBean.getDisabled().put("disabled-update", "false");
      this.courseBean.getDisabled().put("disabled-show", "true");
    }
  }
  
  public void update()
  {
    this.courseBean.getDisabled().put("disabled-insert", "false");
    this.courseBean.getDisabled().put("disabled-update", "true");
    this.courseBean.setUpdatedBy(this.userId);
    if (this.courseBean.getSelectedItem() == null)
    {
      if (!CourseDAO.insert(this.courseBean.serializeModel()))
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
      this.courseBean.setSelectedItem(null);
      if (!CourseDAO.update(this.courseBean.serializeModel()))
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
    Map<String, String> selectedItem = this.courseBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.courseBean.setSelectedItem(null);
      if (!CourseDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.courseBean.resetModel();
    this.courseBean.setSelectedItem(null);
    this.courseBean.getDisabled().put("disabled-insert", "false");
    this.courseBean.getDisabled().put("disabled-update", "true");
    this.courseBean.getDisabled().put("disabled-show", "false");
  }
  
  public String getKeywords()
  {
    return "menu-keyword";
  }
  
  public String getDescription()
  {
    return "menu-description";
  }
  
  public CourseBean getCourseBean()
  {
    return this.courseBean;
  }
  
  public void setCourseBean(CourseBean value)
  {
    this.courseBean = value;
  }
}
