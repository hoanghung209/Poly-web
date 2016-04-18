package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.UserBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.UserDAO;
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
public class UserController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{userBean}")
  private UserBean userBean;
  
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
  
  protected void bind()
  {
    loadDetail();
    loadList(1);
  }
  
  private void loadDetail()
  {
    this.userBean.getDisabled().put("disabled-insert", "false");
    this.userBean.getDisabled().put("disabled-update", "true");
    this.userBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status = 0", new Object[0]);
    String sqlOrder = " ORDER BY id ASC";
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = UserDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    
    this.userBean.setList(lstMap);
    setRowOrder(this.userBean.getList());
    
    this.userBean.setCount(UserDAO.countList(sqlWhere));
    this.userBean.setPageBar(Paginator.getPageBar(page, this.userBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.userBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.userBean.resetModel();
    this.userBean.getDisabled().put("disabled-insert", "true");
    this.userBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.userBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.userBean.deserializeModel(selectedItem);
      this.userBean.getDisabled().put("disabled-insert", "true");
      this.userBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void update()
  {
    this.userBean.getDisabled().put("disabled-insert", "false");
    this.userBean.getDisabled().put("disabled-update", "true");
    if (this.userBean.getSelectedItem() == null)
    {
      if (UserDAO.check(this.userBean.getUser()))
      {
        ContextUtils.addMessage("User đã tồn tại");
      }
      else if (!UserDAO.insert(this.userBean.serializeModel()))
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
      this.userBean.setSelectedItem(null);
      if (!UserDAO.update(this.userBean.serializeModel()))
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
    Map<String, String> selectedItem = this.userBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.userBean.setSelectedItem(null);
      if (!UserDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.userBean.resetModel();
    this.userBean.setSelectedItem(null);
    this.userBean.getDisabled().put("disabled-insert", "false");
    this.userBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "user-keyword";
  }
  
  public String getDescription()
  {
    return "user-description";
  }
  
  public UserBean getUserBean()
  {
    return this.userBean;
  }
  
  public void setUserBean(UserBean value)
  {
    this.userBean = value;
  }
}
