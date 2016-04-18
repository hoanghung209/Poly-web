package admin.web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RegisterBean
  implements Serializable
{
  private String id;
  private String fullname;
  private String phone;
  private String email;
  private String address;
  private String status;
  private String isDeleted;
  private String createdTime;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private String sqlWhere;
  private String sqlOrder;
  
  public String getSqlWhere()
  {
    return this.sqlWhere;
  }
  
  public void setSqlWhere(String sqlWhere)
  {
    this.sqlWhere = sqlWhere;
  }
  
  public String getSqlOrder()
  {
    return this.sqlOrder;
  }
  
  public void setSqlOrder(String sqlOrder)
  {
    this.sqlOrder = sqlOrder;
  }
  
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterName;
  private String filterPhone;
  private String filterEmail;
  private String filterStatus;
  private String filterCreatedTime;
  
  public void resetModel()
  {
    this.id = "0";
    this.fullname = "";
    this.phone = "";
    this.email = "";
    this.address = "";
    this.status = "0";
    this.isDeleted = "0";
    this.createdTime = "";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("fullname", this.fullname);
    map.put("phone", this.phone);
    map.put("email", this.email);
    map.put("address", this.address);
    map.put("is_deleted", this.isDeleted);
    map.put("created_time", this.createdTime);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.fullname = ((String)map.get("fullname"));
    this.phone = ((String)map.get("phone"));
    this.email = ((String)map.get("email"));
    this.address = ((String)map.get("address"));
    this.status = ((String)map.get("status"));
    this.isDeleted = ((String)map.get("is_deleted"));
    this.createdTime = ((String)map.get("created_time"));
    this.updatedBy = ((String)map.get("updated_by"));
    this.updatedTime = ((String)map.get("updated_time"));
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getFullname()
  {
    return this.fullname;
  }
  
  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getIsDeleted()
  {
    return this.isDeleted;
  }
  
  public void setIsDeleted(String isDeleted)
  {
    this.isDeleted = isDeleted;
  }
  
  public String getCreatedTime()
  {
    return this.createdTime;
  }
  
  public void setCreatedTime(String createdTime)
  {
    this.createdTime = createdTime;
  }
  
  public String getUpdatedBy()
  {
    return this.updatedBy;
  }
  
  public void setUpdatedBy(String updatedBy)
  {
    this.updatedBy = updatedBy;
  }
  
  public String getUpdatedTime()
  {
    return this.updatedTime;
  }
  
  public void setUpdatedTime(String updatedTime)
  {
    this.updatedTime = updatedTime;
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public void setCount(int count)
  {
    this.count = count;
  }
  
  public List<Map<String, String>> getList()
  {
    return this.list;
  }
  
  public void setList(List<Map<String, String>> list)
  {
    this.list = list;
  }
  
  public List<Map<String, String>> getPageBar()
  {
    return this.pageBar;
  }
  
  public void setPageBar(List<Map<String, String>> pageBar)
  {
    this.pageBar = pageBar;
  }
  
  public Map<String, String> getSelectedPage()
  {
    return this.selectedPage;
  }
  
  public void setSelectedPage(Map<String, String> selectedPage)
  {
    this.selectedPage = selectedPage;
  }
  
  public Map<String, String> getSelectedItem()
  {
    return this.selectedItem;
  }
  
  public void setSelectedItem(Map<String, String> selectedItem)
  {
    this.selectedItem = selectedItem;
  }
  
  public Map<String, String> getDisabled()
  {
    return this.disabled;
  }
  
  public void setDisabled(Map<String, String> disabled)
  {
    this.disabled = disabled;
  }
  
  public String getFilterName()
  {
    return this.filterName;
  }
  
  public void setFilterName(String filterName)
  {
    this.filterName = filterName;
  }
  
  public String getFilterPhone()
  {
    return this.filterPhone;
  }
  
  public void setFilterPhone(String filterPhone)
  {
    this.filterPhone = filterPhone;
  }
  
  public String getFilterEmail()
  {
    return this.filterEmail;
  }
  
  public void setFilterEmail(String filterEmail)
  {
    this.filterEmail = filterEmail;
  }
  
  public String getFilterStatus()
  {
    return this.filterStatus;
  }
  
  public void setFilterStatus(String filterStatus)
  {
    this.filterStatus = filterStatus;
  }
  
  public String getFilterCreatedTime()
  {
    return this.filterCreatedTime;
  }
  
  public void setFilterCreatedTime(String filterCreatedTime)
  {
    this.filterCreatedTime = filterCreatedTime;
  }
}
