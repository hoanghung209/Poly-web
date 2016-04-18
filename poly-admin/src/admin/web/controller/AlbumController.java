package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.AlbumBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AlbumDAO;
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
public class AlbumController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{albumBean}")
  private AlbumBean albumBean;
  
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
    if (!AccessControl.HasPermission("album", req)) {
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
    this.albumBean.getDisabled().put("disabled-insert", "false");
    this.albumBean.getDisabled().put("disabled-update", "true");
    this.albumBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status = 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    


    String filterTitle = this.albumBean.getFilterTitle();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "title_vn", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterVideo = this.albumBean.getFilterVideo();
    if (!StringUtils.isBlank(filterVideo)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "type", DbProxy.antiInjection(filterVideo) });
    }
    String filterShow = this.albumBean.getFilterShow();
    if (!StringUtils.isBlank(filterShow)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "is_show", DbProxy.antiInjection(filterShow) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AlbumDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("type")).equals("0")) {
        map.put("type", "Image");
      } else if (((String)map.get("type")).equals("1")) {
        map.put("type", "Video");
      } else {
        map.put("type", "News");
      }
      if (((String)map.get("is_show")).equals("0")) {
        map.put("show", "Show");
      } else {
        map.put("show", "Hidden");
      }
    }
    this.albumBean.setList(lstMap);
    setRowOrder(this.albumBean.getList());
    
    this.albumBean.setCount(AlbumDAO.countList(sqlWhere));
    this.albumBean.setPageBar(Paginator.getPageBar(page, this.albumBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.albumBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.albumBean.resetModel();
    this.albumBean.getDisabled().put("disabled-insert", "true");
    this.albumBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.albumBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.albumBean.deserializeModel(selectedItem);
      this.albumBean.getDisabled().put("disabled-insert", "true");
      this.albumBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void update()
  {
    this.albumBean.getDisabled().put("disabled-insert", "false");
    this.albumBean.getDisabled().put("disabled-update", "true");
    this.albumBean.setUpdatedBy(this.userId);
    this.albumBean.setSlugVn(ContextUtils.convertToSlug(this.albumBean.getTitleVn()));
    this.albumBean.setSlugEn(ContextUtils.convertToSlug(this.albumBean.getTitleEn()));
    if (this.albumBean.getSelectedItem() == null)
    {
      if (!AlbumDAO.insert(this.albumBean.serializeModel()))
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
      this.albumBean.setSelectedItem(null);
      if (!AlbumDAO.update(this.albumBean.serializeModel()))
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
    Map<String, String> selectedItem = this.albumBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.albumBean.setSelectedItem(null);
      if (!AlbumDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.albumBean.resetModel();
    this.albumBean.setSelectedItem(null);
    this.albumBean.getDisabled().put("disabled-insert", "false");
    this.albumBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "album-keyword";
  }
  
  public String getDescription()
  {
    return "album-description";
  }
  
  public AlbumBean getAlbumBean()
  {
    return this.albumBean;
  }
  
  public void setAlbumBean(AlbumBean value)
  {
    this.albumBean = value;
  }
}
