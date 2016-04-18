package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.NewsBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AlbumDAO;
import admin.web.dao.NewsDAO;
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
public class NewsListController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{newsBean}")
  private NewsBean newsBean;
  
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
    if (!AccessControl.HasPermission("news", req)) {
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
    this.newsBean.getDisabled().put("disabled-insert", "false");
    this.newsBean.getDisabled().put("disabled-update", "true");
    this.newsBean.resetModel();
    String message = ContextUtils.getSession("contentMessage", "");
    if (!StringUtils.isBlank(message))
    {
      ContextUtils.addMessage(message);
      ContextUtils.removeSession("contentMessage");
    }
    this.newsBean.setLstAlbum(AlbumDAO.getAlbumByType("2"));
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND `is_deleted` = 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    


    String filterTitle = this.newsBean.getFilterTitle();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "title_vn", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterType = this.newsBean.getFilterShow();
    if (!StringUtils.isBlank(filterType)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "is_show", DbProxy.antiInjection(filterType) });
    }
    String filterAlbum = this.newsBean.getFilterAlbum();
    if (ContextUtils.getSession("news_category") != null)
    {
      filterAlbum = ContextUtils.getSession("news_category").toString();
      this.newsBean.setFilterAlbum(filterAlbum);
      ContextUtils.removeSession("news_category");
    }
    if (!StringUtils.isBlank(filterAlbum)) {
      sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "album_id", DbProxy.antiInjection(filterAlbum) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = NewsDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap) {
      if (((String)map.get("is_show")).equals("0")) {
        map.put("show", "Show");
      } else {
        map.put("show", "Hide");
      }
    }
    this.newsBean.setList(lstMap);
    setRowOrder(this.newsBean.getList());
    
    this.newsBean.setCount(NewsDAO.countList(sqlWhere));
    this.newsBean.setPageBar(Paginator.getPageBar(page, this.newsBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.newsBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.newsBean.resetModel();
    this.newsBean.getDisabled().put("disabled-insert", "true");
    this.newsBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.newsBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      String url = RewriteURL.makeNewsURL("tab-1", "13", (String)selectedItem.get("id"));
      ContextUtils.setSession("news_category", this.newsBean.getFilterAlbum());
      ContextUtils.redirect(url);
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.newsBean.getSelectedItem();
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
      this.newsBean.setSelectedItem(null);
      if (!NewsDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.newsBean.resetModel();
    this.newsBean.setSelectedItem(null);
    this.newsBean.getDisabled().put("disabled-insert", "false");
    this.newsBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "news-keyword";
  }
  
  public String getDescription()
  {
    return "news-description";
  }
  
  public NewsBean getNewsBean()
  {
    return this.newsBean;
  }
  
  public void setNewsBean(NewsBean value)
  {
    this.newsBean = value;
  }
}
