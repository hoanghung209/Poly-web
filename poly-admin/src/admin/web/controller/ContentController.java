package admin.web.controller;

import admin.web.bean.ContentBean;
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
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContentController
  implements Serializable
{
  private String reqContentId = ContextUtils.getRequest("id", "0");
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String label = "";
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
    loadList();
  }
  
  public void loadList()
  {
    if (this.reqContentId.equals("0")) {
      this.label = "Thêm nội dung";
    } else {
      this.label = "Sửa nội dung";
    }
    String sqlWhere = String.format(" AND `status` = 0", new Object[0]);
    String sqlOrder = "";
    

    sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "id", DbProxy.antiInjection(this.reqContentId) });
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = ContentDAO.getList(1, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    if (lstMap.size() > 0)
    {
      this.contentBean.deserializeModel(lstMap.get(0));
      this.contentBean.setMenuName(MenuDAO.getFieldById(lstMap.get(0).get("menu_id"), "name_vn"));
    }
  }
  
  public void update()
  {
    this.contentBean.getDisabled().put("disabled-insert", "false");
    this.contentBean.getDisabled().put("disabled-update", "true");
    this.contentBean.setUpdatedBy(this.userId);
    if (this.reqContentId.equals("0"))
    {
      if (!ContentDAO.insert(this.contentBean.serializeModel()))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        ContextUtils.setSession("contentMessage", "Thêm mới thành công");
        ContextUtils.redirect(RewriteURL.makeContentListURL("tab-1", "12"));
      }
    }
    else
    {
      if (this.contentBean.getId().equals("-1"))
      {
        ContextUtils.addMessage("Không được phép thay đổi root");
        return;
      }
      if (!ContentDAO.update(this.contentBean.serializeModel()))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        ContextUtils.setSession("contentMessage", "Cập nhật thành công");
        ContextUtils.redirect(RewriteURL.makeContentListURL("tab-1", "12"));
      }
    }
  }
  
  public void cancel()
  {
    this.contentBean.resetModel();
    ContextUtils.redirect(RewriteURL.makeContentURL("tab-1", "10", this.reqContentId));
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
  
  public String getLabel()
  {
    return this.label;
  }
  
  public void setLabel(String label)
  {
    this.label = label;
  }
}
