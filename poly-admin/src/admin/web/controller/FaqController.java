package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.FaqBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.FaqDAO;
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
public class FaqController
  implements Serializable
{
  @ManagedProperty("#{faqBean}")
  private FaqBean faqBean;
  
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
    if (!AccessControl.HasPermission("faq", req)) {
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
    this.faqBean.getDisabled().put("disabled-insert", "false");
    this.faqBean.getDisabled().put("disabled-update", "true");
    this.faqBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND status = 0", new Object[0]);
    String sqlOrder = " ORDER BY id DESC";
    


    String filterTitle = this.faqBean.getFilterTitle();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "title", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterLang = this.faqBean.getFilterLang();
    if (!StringUtils.isBlank(filterLang)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "language", DbProxy.antiInjection(filterLang) });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    List<Map<String, String>> lstMap = FaqDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    

    this.faqBean.setList(lstMap);
    setRowOrder(this.faqBean.getList());
    
    this.faqBean.setCount(FaqDAO.countList(sqlWhere));
    this.faqBean.setPageBar(Paginator.getPageBar(page, this.faqBean.getCount(), ""));
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
    Map<String, String> selectedPage = this.faqBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void add()
  {
    this.faqBean.resetModel();
    this.faqBean.getDisabled().put("disabled-insert", "true");
    this.faqBean.getDisabled().put("disabled-update", "false");
    this.faqBean.setOrd(String.valueOf(FaqDAO.getOrder("vn")));
  }
  
  public void changeLanguage()
  {
    this.faqBean.setOrd(String.valueOf(FaqDAO.getOrder(this.faqBean.getLanguage())));
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.faqBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.faqBean.deserializeModel(selectedItem);
      this.faqBean.getDisabled().put("disabled-insert", "true");
      this.faqBean.getDisabled().put("disabled-update", "false");
    }
  }
  
  public void update()
  {
    this.faqBean.getDisabled().put("disabled-insert", "false");
    this.faqBean.getDisabled().put("disabled-update", "true");
    if (this.faqBean.getSelectedItem() == null)
    {
      if (!FaqDAO.insert(this.faqBean.serializeModel()))
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
      this.faqBean.setSelectedItem(null);
      if (!FaqDAO.update(this.faqBean.serializeModel()))
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
    Map<String, String> selectedItem = this.faqBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.faqBean.setSelectedItem(null);
      if (!FaqDAO.deleteLogic((String)selectedItem.get("id")))
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
    this.faqBean.resetModel();
    this.faqBean.setSelectedItem(null);
    this.faqBean.getDisabled().put("disabled-insert", "false");
    this.faqBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "menu-keyword";
  }
  
  public String getDescription()
  {
    return "menu-description";
  }
  
  public FaqBean getfaqBean()
  {
    return this.faqBean;
  }
  
  public void setfaqBean(FaqBean value)
  {
    this.faqBean = value;
  }
}
