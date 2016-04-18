package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.RegisterBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.RegisterDAO;
import admin.web.dao.UserDAO;
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
public class RegisterController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  @ManagedProperty("#{registerBean}")
  private RegisterBean registerBean;
  
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
    if (!AccessControl.HasPermission("register", req)) {
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
    this.registerBean.getDisabled().put("disabled-insert", "false");
    this.registerBean.getDisabled().put("disabled-update", "true");
    this.registerBean.resetModel();
  }
  
  public void loadList(int page)
  {
    String sqlWhere = String.format(" AND is_deleted = 0", new Object[0]);
    String sqlOrder = " ORDER BY status ASC,updated_time DESC";
    


    String filterTitle = this.registerBean.getFilterName();
    if (!StringUtils.isBlank(filterTitle)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "fullname", "%", DbProxy.antiInjection(filterTitle), "%" });
    }
    String filterPhone = this.registerBean.getFilterPhone();
    if (!StringUtils.isBlank(filterPhone)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "phone", "%", DbProxy.antiInjection(filterPhone), "%" });
    }
    String filterEmail = this.registerBean.getFilterEmail();
    if (!StringUtils.isBlank(filterEmail)) {
      sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", new Object[] { sqlWhere, "email", "%", DbProxy.antiInjection(filterEmail), "%" });
    }
    String filterStatus = this.registerBean.getFilterStatus();
    if (!StringUtils.isBlank(filterStatus)) {
      sqlWhere = String.format("%s AND %s = '%s'", new Object[] { sqlWhere, "status", DbProxy.antiInjection(filterStatus) });
    }
    String filterCreatedTime = this.registerBean.getFilterCreatedTime();
    SimpleDateFormat simDateF;
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
        simDateF = new SimpleDateFormat("dd/MM/yyyy");
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
    this.registerBean.setSqlWhere(sqlWhere);
    this.registerBean.setSqlOrder(sqlOrder);
    List<Map<String, String>> lstUser = UserDAO.getAll();
    List<Map<String, String>> lstMap = RegisterDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("status")).equals("0")) {
        map.put("status_name", "Chưa xử lý");
      } else if (((String)map.get("status")).equals("1")) {
        map.put("status_name", "Đang xử lý");
      } else if (((String)map.get("status")).equals("2")) {
        map.put("status_name", "Đã xử lý");
      } else {
        map.put("status_name", (String)map.get("status"));
      }
      map.put("username", getUserName(lstUser, (String)map.get("updated_by")));
      map.put("created_time_vn", formatDate((String)map.get("created_time")));
      map.put("updated_time_vn", formatDate((String)map.get("updated_time")));
    }
    this.registerBean.setList(lstMap);
    setRowOrder(this.registerBean.getList());
    
    this.registerBean.setCount(RegisterDAO.countList(sqlWhere));
    this.registerBean.setPageBar(Paginator.getPageBar(page, this.registerBean.getCount(), ""));
    RequestContext.getCurrentInstance().execute(" $('#txtDateRange').daterangepicker({ arrows: true });");
  }
  
  private String getUserName(List<Map<String, String>> lstUser, String id)
  {
    if (StringUtils.isBlank(id)) {
      return "";
    }
    for (Map<String, String> map : lstUser) {
      if (((String)map.get("id")).equals(id)) {
        return (String)map.get("user");
      }
    }
    return id;
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
    Map<String, String> selectedPage = this.registerBean.getSelectedPage();
    if (selectedPage != null)
    {
      int page = Integer.valueOf((String)selectedPage.get("page")).intValue();
      loadList(page);
    }
  }
  
  public void start()
  {
    Map<String, String> selectedItem = this.registerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else if (((String)selectedItem.get("status")).equals("2"))
    {
      ContextUtils.addMessage("Đăng ký này đã được xử lý");
    }
    else
    {
      this.registerBean.setSelectedItem(null);
      if (!RegisterDAO.action((String)selectedItem.get("id"), "1", this.userId))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        bind();
        ContextUtils.addMessage("Bắt đầu xử lý đăng ký test");
      }
    }
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.registerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.registerBean.setSelectedItem(null);
      if (!RegisterDAO.action((String)selectedItem.get("id"), "2", this.userId))
      {
        ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
      }
      else
      {
        bind();
        ContextUtils.addMessage("Đã xử lý đăng ký test");
      }
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.registerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.registerBean.setSelectedItem(null);
      if (!RegisterDAO.deleteLogic((String)selectedItem.get("id"), this.userId))
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
  
  public void processExcel()
  {
    List<Map<String, String>> lstMap = RegisterDAO.getListAll(this.registerBean.getSqlWhere(), this.registerBean.getSqlOrder());
    List<Map<String, String>> lstUser = UserDAO.getAll();
    for (Map<String, String> map : lstMap)
    {
      if (((String)map.get("status")).equals("0")) {
        map.put("status_name", "Chưa xử lý");
      } else if (((String)map.get("status")).equals("1")) {
        map.put("status_name", "Đang xử lý");
      } else if (((String)map.get("status")).equals("2")) {
        map.put("status_name", "Đã xử lý");
      } else {
        map.put("status_name", (String)map.get("status"));
      }
      map.put("updated_by_name", getUserName(lstUser, (String)map.get("updated_by")));
      map.put("created_time_vn", formatDate((String)map.get("created_time")));
      map.put("updated_time_vn", formatDate((String)map.get("updated_time")));
    }
    List<String> lstColumn = new ArrayList<String>();
    lstColumn.add("id");
    lstColumn.add("fullname");
    lstColumn.add("phone");
    lstColumn.add("email");
    lstColumn.add("status_name");
    lstColumn.add("created_time_vn");
    lstColumn.add("updated_by_name");
    lstColumn.add("updated_time_vn");
    
    ExcelTableHeper.postProcessXLS(lstMap, lstColumn);
  }
  
  public void cancel()
  {
    this.registerBean.resetModel();
    this.registerBean.setSelectedItem(null);
    this.registerBean.getDisabled().put("disabled-insert", "false");
    this.registerBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "register-keyword";
  }
  
  public String getDescription()
  {
    return "register-description";
  }
  
  public RegisterBean getRegisterBean()
  {
    return this.registerBean;
  }
  
  public void setRegisterBean(RegisterBean value)
  {
    this.registerBean = value;
  }
}
