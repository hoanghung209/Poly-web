package admin.web.controller;

import admin.web.bc.LanguageBC;
import admin.web.bean.SchedulerBean;
import admin.web.common.RewriteURL;
import admin.web.dao.CourseDAO;
import admin.web.dao.SchedulerDAO;
import admin.web.util.ContextUtils;
import com.vega.security.AccessControl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import vg.core.common.StringUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SchedulerController
  implements Serializable
{
  private String userId = AccessControl.getUserid(ContextUtils.getRequest());
  @ManagedProperty("#{schedulerBean}")
  private SchedulerBean schedulerBean;
  
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
    if (!AccessControl.HasPermission("scheduler", req)) {
      return false;
    }
    return true;
  }
  
  protected void bind()
  {
    loadDetail();
    loadCourse();
    loadList();
  }
  
  private void loadDetail()
  {
    this.schedulerBean.getDisabled().put("disabled-insert", "false");
    this.schedulerBean.getDisabled().put("disabled-update", "true");
    this.schedulerBean.resetModel();
  }
  
  private void loadCourse()
  {
    List<Map<String, String>> lstCourse = CourseDAO.getAll();
    this.schedulerBean.setListCourse(lstCourse);
  }
  
  public void loadList()
  {
    String chuongtrinhId = this.schedulerBean.getFilterChuongtrinhId();
    System.out.println(chuongtrinhId);
    if (StringUtils.isBlank(chuongtrinhId)) {
      chuongtrinhId = "1";
    }
    String language = this.schedulerBean.getFilterLanguage();
    if (StringUtils.isBlank(language)) {
      language = "vn";
    }
    List<Map<String, String>> lstHeader = SchedulerDAO.getHeader();
    for (Map<String, String> map : lstHeader)
    {
      map.put("content", (String)map.get("content_" + language));
      map.put("col_name", "col" + (String)map.get("column"));
    }
    this.schedulerBean.setListHeader(lstHeader);
    

    List<Map<String, String>> lstScheduler = SchedulerDAO.getByChuongtrinh(chuongtrinhId);
    

    List<Map<String, String>> lstNewScheduler = new ArrayList<Map<String, String>>();
    Map<String, String> mapRow = new HashMap<String, String>();
    for (Map<String, String> map : lstScheduler) {
      if (map.get("column").equals("1"))
      {
        mapRow = new HashMap<String, String>();
        mapRow.put("row", (String)map.get("row"));
        mapRow.put("chuongtrinh_id", chuongtrinhId);
        mapRow.put("col1", (String)map.get("content_" + language));
      }
      else if (((String)map.get("column")).equals(String.valueOf(lstHeader.size())))
      {
        mapRow.put("col" + (String)map.get("column"), (String)map.get("content_" + language));
        lstNewScheduler.add(mapRow);
      }
      else if (Integer.valueOf((String)map.get("column")).intValue() < lstHeader.size())
      {
        mapRow.put("col" + (String)map.get("column"), (String)map.get("content_" + language));
      }
    }
    this.schedulerBean.setList(lstNewScheduler);
  }
  
  public void add()
  {
    this.schedulerBean.resetModel();
    this.schedulerBean.getDisabled().put("disabled-insert", "true");
    this.schedulerBean.getDisabled().put("disabled-update", "false");
    
    String chuongtrinhId = this.schedulerBean.getFilterChuongtrinhId();
    String maxRow = SchedulerDAO.getMaxRow(chuongtrinhId);
    int nextRow = 1;
    if (maxRow != null) {
      nextRow = Integer.valueOf(maxRow).intValue() + 1;
    }
    List<Map<String, String>> lstAdd = new ArrayList<Map<String, String>>();
    for (int i = 1; i <= this.schedulerBean.getListHeader().size(); i++)
    {
      Map<String, String> mapAdd = new HashMap<String, String>();
      Map<String, String> mapHeader = getHeaderName(this.schedulerBean.getListHeader(), String.valueOf(i));
      mapAdd.put("header_vn", (String)mapHeader.get("content_vn"));
      mapAdd.put("header_en", (String)mapHeader.get("content_en"));
      mapAdd.put("row", String.valueOf(nextRow));
      mapAdd.put("column", String.valueOf(i));
      mapAdd.put("updated_by", this.userId);
      mapAdd.put("status", "0");
      mapAdd.put("chuongtrinh_id", chuongtrinhId);
      
      lstAdd.add(mapAdd);
    }
    this.schedulerBean.setListEdit(lstAdd);
    this.schedulerBean.setActionRow("0");
  }
  
  public void edit()
  {
    Map<String, String> selectedItem = this.schedulerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
    }
    else
    {
      this.schedulerBean.getDisabled().put("disabled-insert", "true");
      this.schedulerBean.getDisabled().put("disabled-update", "false");
      this.schedulerBean.setSelectedItem(null);
      String chuongtrinhId = (String)selectedItem.get("chuongtrinh_id");
      String row = (String)selectedItem.get("row");
      List<Map<String, String>> lstItem = SchedulerDAO.getByChuongtrinh(chuongtrinhId, row);
      List<Map<String, String>> lstHeader = SchedulerDAO.getHeader();
      for (Map<String, String> map : lstItem)
      {
        Map<String, String> mapHeader = getHeaderName(lstHeader, (String)map.get("column"));
        map.put("header_vn", (String)mapHeader.get("content_vn"));
        map.put("header_en", (String)mapHeader.get("content_en"));
      }
      this.schedulerBean.setListEdit(lstItem);
      this.schedulerBean.setActionRow("1");
    }
  }
  
  public void editHeader()
  {
    this.schedulerBean.getDisabled().put("disabled-insert", "true");
    this.schedulerBean.getDisabled().put("disabled-update", "false");
    
    List<Map<String, String>> lstItem = SchedulerDAO.getHeader();
    this.schedulerBean.setListEdit(lstItem);
    this.schedulerBean.setActionRow("2");
  }
  
  public Map<String, String> getHeaderName(List<Map<String, String>> lstHeader, String column)
  {
    for (Map<String, String> map : lstHeader) {
      if (column.equals(map.get("column"))) {
        return map;
      }
    }
    return null;
  }
  
  public void update()
  {
    if (this.schedulerBean.getActionRow().equals("1"))
    {
      for (Map<String, String> edit : this.schedulerBean.getListEdit())
      {
        edit.put("updated_by", this.userId);
        SchedulerDAO.update(edit);
      }
      bind();
      ContextUtils.addMessage("Cập nhật thành công");
    }
    else if (this.schedulerBean.getActionRow().equals("0"))
    {
      for (Map<String, String> edit : this.schedulerBean.getListEdit()) {
        SchedulerDAO.insert(edit);
      }
      bind();
      ContextUtils.addMessage("Thêm mới thành công");
    }
    else if (this.schedulerBean.getActionRow().equals("2"))
    {
      for (Map<String, String> edit : this.schedulerBean.getListEdit())
      {
        edit.put("updated_by", this.userId);
        SchedulerDAO.update(edit);
      }
      bind();
      ContextUtils.addMessage("Cập nhật thành công");
    }
    else
    {
      ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
    }
  }
  
  public void delete()
  {
    Map<String, String> selectedItem = this.schedulerBean.getSelectedItem();
    if (selectedItem == null)
    {
      ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
    }
    else
    {
      this.schedulerBean.setSelectedItem(null);
      if (!SchedulerDAO.deleteLogic((String)selectedItem.get("chuongtrinh_id"), (String)selectedItem.get("row"), this.userId))
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
    this.schedulerBean.resetModel();
    this.schedulerBean.setSelectedItem(null);
    this.schedulerBean.getDisabled().put("disabled-insert", "false");
    this.schedulerBean.getDisabled().put("disabled-update", "true");
  }
  
  public String getKeywords()
  {
    return "menu-keyword";
  }
  
  public String getDescription()
  {
    return "menu-description";
  }
  
  public SchedulerBean getSchedulerBean()
  {
    return this.schedulerBean;
  }
  
  public void setSchedulerBean(SchedulerBean value)
  {
    this.schedulerBean = value;
  }
}
