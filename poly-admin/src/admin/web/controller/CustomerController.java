package admin.web.controller;

import admin.web.bean.CustomerBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.CustomerDAO;
import admin.web.util.ContextUtils;
import admin.web.util.ExcelTableHeper;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CustomerController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  @ManagedProperty("#{customerBean}")
  private CustomerBean customerBean;
  
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
    if (!AccessControl.HasPermission("customer", req)) {
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
    this.customerBean.getDisabled().put("disabled-insert", "false");
    this.customerBean.getDisabled().put("disabled-update", "true");
    this.customerBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND `status` = 0", new Object[0]);
    String sqlOrder = " ORDER BY created_time DESC";
    

    String filterEmail = this.customerBean.getFilterEmail();
    if (!StringUtils.isBlank(filterEmail)) {
      sqlWhere = sqlWhere + " AND `email` LIKE '%" + DbProxy.antiInjection(filterEmail) + "%'";
    }
    String filterPhone = this.customerBean.getFilterPhone();
    if (!StringUtils.isBlank(filterPhone)) {
      sqlWhere = sqlWhere + " AND `phone` LIKE '%" + DbProxy.antiInjection(filterPhone) + "%'";
    }
    String filterCreatedTime = this.customerBean.getFilterCreatedTime();
    if (!StringUtils.isBlank(filterCreatedTime))
    {
    	String toDate;
    	String fromDate;
      try
      {
        String[] arrDate = filterCreatedTime.split("-");
        
        if (arrDate.length == 1)
        {
          fromDate = arrDate[0];
          toDate = fromDate;
        }
        else
        {
          fromDate = arrDate[0];
          toDate = arrDate[1];
        }
        SimpleDateFormat simDateF = new SimpleDateFormat("dd/MM/yyyy");
        Date fdate = simDateF.parse(fromDate);
        Date tdate = simDateF.parse(toDate);
        simDateF = new SimpleDateFormat("yyyy-MM-dd");
        fromDate = simDateF.format(fdate);
        toDate = simDateF.format(tdate);
      }
      catch (Exception ex)
      {
        ContextUtils.addMessage("Định dạng ngày bị lỗi:" + ex.getMessage()); return;
      }
      
      sqlWhere = String.format("%s AND (%s BETWEEN '%s 00:00:00' AND '%s 23:59:59')", new Object[] { sqlWhere, "created_time", fromDate, toDate });
    }
    if (sqlWhere.length() > 0) {
      sqlWhere = String.format(" WHERE %s", new Object[] { sqlWhere.substring(5) });
    }
    this.customerBean.setSqlWhere(sqlWhere);
    this.customerBean.setSqlOrder(sqlOrder);
    List<Map<String, String>> lstMap = CustomerDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap) {
      map.put("created_time_vn", formatDate((String)map.get("created_time")));
    }
    this.customerBean.setList(lstMap);
    setRowOrder(this.customerBean.getList());
    
    this.customerBean.setCount(CustomerDAO.countList(sqlWhere));
    this.customerBean.setPageBar(Paginator.getPageBar(page, this.customerBean.getCount(), ""));
    RequestContext.getCurrentInstance().execute(" $('#txtDateRange').daterangepicker({ arrows: true });");
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
    Map<String, String> selectedPage = this.customerBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.customerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.customerBean.setSelectedItem(null);
      if (!CustomerDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
  
  private String formatDate(String date)
  {
    try
    {
      if (date.length() > 19) {
        date = date.substring(0, 19);
      }
      Date newDate = this.sdf.parse(date);
      return this.sdf1.format(newDate);
    }
    catch (Exception ex) {}
    return date;
  }
  
  public void processExcel()
  {
    List<Map<String, String>> lstMap = CustomerDAO.getListAll(this.customerBean.getSqlWhere(), this.customerBean.getSqlOrder());
    for (Map<String, String> map : lstMap) {
      map.put("created_time_vn", formatDate((String)map.get("created_time")));
    }
    List<String> lstColumn = new ArrayList<String> ();
    lstColumn.add("id");
    lstColumn.add("email");
    lstColumn.add("fullname");
    lstColumn.add("phone");
    lstColumn.add("address");
    lstColumn.add("created_time_vn");
    
    ExcelTableHeper.postProcessXLS(lstMap, lstColumn);
  }
  
  public String getKeywords()
  {
    return "customer-keyword";
  }
  
  public String getDescription()
  {
    return "customer-description";
  }
  
  public CustomerBean getCustomerBean()
  {
    return this.customerBean;
  }
  
  public void setCustomerBean(CustomerBean value)
  {
    this.customerBean = value;
  }
}
