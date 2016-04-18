package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.AdsBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.AdsBannerDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AdsController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
  @ManagedProperty("#{adsBean}")
  private AdsBean adsBean;
  
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
    if (!AccessControl.HasPermission("ads", req)) {
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
    this.adsBean.getDisabled().put("disabled-insert", "false");
    this.adsBean.getDisabled().put("disabled-update", "true");
    this.adsBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND is_deleted = 0", new Object[0]);
    String sqlOrder = " ORDER BY updated_time DESC";
    


    String filterTitle = this.adsBean.getFilterName();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "name", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterVideo = this.adsBean.getFilterType();
    if (!StringUtils.isBlank(filterVideo)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "type", DbProxy.antiInjection(filterVideo) });
    }
    String filterShow = this.adsBean.getFilterShow();
    if (!StringUtils.isBlank(filterShow)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "is_show", DbProxy.antiInjection(filterShow) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = AdsBannerDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("is_show")).equals("0")) {
        map.put("show", "Show");
      } else {
        map.put("show", "Hidden");
      }
      if (((String)map.get("type")).equals("0")) {
        map.put("type_name", "Banner");
      } else {
        map.put("type_name", "Popup");
      }
      if (((String)map.get("is_once")).equals("0")) {
        map.put("once", "Once");
      } else {
        map.put("once", "Many");
      }
      try
      {
        map.put("start", this.sdf2.format(this.sdf.parse((String)map.get("date_start"))));
        map.put("end", this.sdf2.format(this.sdf.parse((String)map.get("date_end"))));
      }
      catch (Exception localException) {}
    }
    this.adsBean.setList(lstMap);
    setRowOrder(this.adsBean.getList());
    
    this.adsBean.setCount(AdsBannerDAO.countList(sqlWhere));
    this.adsBean.setPageBar(Paginator.getPageBar(page, this.adsBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.adsBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.adsBean.resetModel();
    this.adsBean.getDisabled().put("disabled-insert", "true");
    this.adsBean.getDisabled().put("disabled-update", "false");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.adsBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.adsBean.deserializeModel(selectedItem);
      this.adsBean.getDisabled().put("disabled-insert", "true");
      this.adsBean.getDisabled().put("disabled-update", "false");
      try
      {
        this.adsBean.setDate_start(this.sdf2.format(this.sdf.parse(this.adsBean.getDate_start())));
        this.adsBean.setDate_end(this.sdf2.format(this.sdf.parse(this.adsBean.getDate_end())));
      }
      catch (Exception localException) {}
    }
  }
  
  public void update()
  {
    this.adsBean.getDisabled().put("disabled-insert", "false");
    this.adsBean.getDisabled().put("disabled-update", "true");
    
    String date_start = this.adsBean.getDate_start();
    try
    {
      Date start = this.sdf2.parse(date_start);
      date_start = this.sdf.format(start);
    }
    catch (Exception ex)
    {
      date_start = this.sdf.format(new Date());
    }
    String date_end = this.adsBean.getDate_end();
    try
    {
      Date end = this.sdf2.parse(date_end);
      date_end = this.sdf.format(end);
    }
    catch (Exception ex)
    {
      date_end = this.sdf.format(new Date());
    }
    this.adsBean.setDate_start(date_start);
    this.adsBean.setDate_end(date_end);
    this.adsBean.setUpdated_by(this.userId);
    if (this.adsBean.getSelectedItem() == null)
    {
      if (!AdsBannerDAO.insert(this.adsBean.serializeModel()))
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
      this.adsBean.setSelectedItem(null);
      if (!AdsBannerDAO.update(this.adsBean.serializeModel()))
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
    Map<String, String> selectedItem = this.adsBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.adsBean.setSelectedItem(null);
      if (!AdsBannerDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
    this.adsBean.resetModel();
    this.adsBean.setSelectedItem(null);
    this.adsBean.getDisabled().put("disabled-insert", "false");
    this.adsBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "ads-keyword";
  }
  
  public String getDescription()
  {
    return "ads-description";
  }
  
  public AdsBean getAdsBean()
  {
    return this.adsBean;
  }
  
  public void setAdsBean(AdsBean value)
  {
    this.adsBean = value;
  }
}
