package admin.web.controller;

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
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NewsController
  implements Serializable
{
  private String reqContentId = ContextUtils.getRequest("id", "0");
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  private String label = "";
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
    loadList();
  }
  
  public void loadList()
  {
    if (this.reqContentId.equals("0")) {
      this.label = "Thêm tin ";
    } else {
      this.label = "Sửa tin";
    }
    this.newsBean.setLstAlbum(AlbumDAO.getAlbumByType("2"));
    String sqlWhere = String.format(" AND `is_deleted` = 0", new Object[0]);
    String sqlOrder = "";
    

    sqlWhere = String.format("%s AND %s = %s", new Object[] { sqlWhere, "id", DbProxy.antiInjection(this.reqContentId) });
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = NewsDAO.getList(1, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    if (lstMap.size() > 0) {
      this.newsBean.deserializeModel(lstMap.get(0));
    }
  }
  
  public void update()
  {
    this.newsBean.getDisabled().put("disabled-insert", "false");
    this.newsBean.getDisabled().put("disabled-update", "true");
    this.newsBean.setUpdatedBy(this.userId);
    this.newsBean.setSlug_vn(ContextUtils.convertToSlug(this.newsBean.getTitleVn()));
    this.newsBean.setSlug_en(ContextUtils.convertToSlug(this.newsBean.getTitleEn()));
    if (this.reqContentId.equals("0"))
    {
      if (!NewsDAO.insert(this.newsBean.serializeModel()))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        ContextUtils.setSession("contentMessage", "Thêm mới thành công");
        ContextUtils.redirect(RewriteURL.makeNewsListURL("tab-1", "14"));
      }
    }
    else if (!NewsDAO.update(this.newsBean.serializeModel()))
    {
      ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
    }
    else
    {
      ContextUtils.setSession("contentMessage", "Cập nhật thành công");
      ContextUtils.redirect(RewriteURL.makeNewsListURL("tab-1", "14"));
    }
  }
  
  public void cancel()
  {
    this.newsBean.resetModel();
    ContextUtils.redirect(RewriteURL.makeNewsURL("tab-1", "13", this.reqContentId));
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
  
  public String getLabel()
  {
    return this.label;
  }
  
  public void setLabel(String label)
  {
    this.label = label;
  }
}
