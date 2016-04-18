package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.FileBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AlbumDAO;
import admin.web.dao.FileDAO;
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
public class FileController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{fileBean}")
  private FileBean fileBean;
  
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
    if (!AccessControl.HasPermission("file", req)) {
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
    this.fileBean.getDisabled().put("disabled-insert", "false");
    this.fileBean.getDisabled().put("disabled-update", "true");
    this.fileBean.resetModel();
    this.fileBean.setListAlbum(AlbumDAO.getAll());
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status = 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    

    String filterAlbum = this.fileBean.getFilterAlbumId();
    if (!StringUtils.isBlank(filterAlbum)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "album_id", "%", DbProxy.antiInjection(filterAlbum), "%" });
    }
    String filterTitle = this.fileBean.getFilterName();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name_vn", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterVideo = this.fileBean.getFilterVideo();
    if (!StringUtils.isBlank(filterVideo)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "is_video", DbProxy.antiInjection(filterVideo) });
    }
    String filterShow = this.fileBean.getFilterShow();
    if (!StringUtils.isBlank(filterShow)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "is_show", DbProxy.antiInjection(filterShow) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = FileDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("is_video")).equals("0")) {
        map.put("type", "Image");
      } else {
        map.put("type", "Video");
      }
      if (((String)map.get("is_show")).equals("0")) {
        map.put("show", "Show");
      } else {
        map.put("show", "Hidden");
      }
      Map<String, String> album = getAlbumName(this.fileBean.getListAlbum(), (String)map.get("album_id"));
      if (album != null) {
        map.put("album_name", (String)album.get("title_vn"));
      }
    }
    this.fileBean.setList(lstMap);
    setRowOrder(this.fileBean.getList());
    
    this.fileBean.setCount(FileDAO.countList(sqlWhere));
    this.fileBean.setPageBar(Paginator.getPageBar(page, this.fileBean.getCount(), ""));
  }
  
  private Map<String, String> getAlbumName(List<Map<String, String>> lstAlbum, String id)
  {
    for (Map<String, String> map : lstAlbum) {
      if (((String)map.get("id")).equals(id)) {
        return map;
      }
    }
    return null;
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
    Map<String, String> selectedPage = this.fileBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.fileBean.resetModel();
    this.fileBean.getDisabled().put("disabled-insert", "true");
    this.fileBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.fileBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.fileBean.deserializeModel(selectedItem);
      this.fileBean.getDisabled().put("disabled-insert", "true");
      this.fileBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void update()
  {
    this.fileBean.getDisabled().put("disabled-insert", "false");
    this.fileBean.getDisabled().put("disabled-update", "true");
    if (this.fileBean.getSelectedItem() == null)
    {
      if (!FileDAO.insert(this.fileBean.serializeModel()))
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
      this.fileBean.setSelectedItem(null);
      if (!FileDAO.update(this.fileBean.serializeModel()))
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
    Map<String, String> selectedItem = this.fileBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.fileBean.setSelectedItem(null);
      if (!FileDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.fileBean.resetModel();
    this.fileBean.setSelectedItem(null);
    this.fileBean.getDisabled().put("disabled-insert", "false");
    this.fileBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "file-keyword";
  }
  
  public String getDescription()
  {
    return "file-description";
  }
  
  public FileBean getFileBean()
  {
    return this.fileBean;
  }
  
  public void setFileBean(FileBean value)
  {
    this.fileBean = value;
  }
}
