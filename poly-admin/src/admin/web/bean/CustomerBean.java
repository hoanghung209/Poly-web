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
public class CustomerBean
  implements Serializable
{
  private String id;
  private String email;
  private String password;
  private String fullname;
  private String phone;
  private String address;
  private String sex;
  private String status;
  private String created_time;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private String sqlWhere;
  private String sqlOrder;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterEmail;
  private String filterPhone;
  private String filterCreatedTime;
  
  public void resetModel() {}
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    return map;
  }
  
  public void deserializeModel(Map<String, String> map) {}
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
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
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getCreated_time()
  {
    return this.created_time;
  }
  
  public void setCreated_time(String created_time)
  {
    this.created_time = created_time;
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
  
  public String getFilterEmail()
  {
    return this.filterEmail;
  }
  
  public void setFilterEmail(String filterEmail)
  {
    this.filterEmail = filterEmail;
  }
  
  public String getFilterPhone()
  {
    return this.filterPhone;
  }
  
  public void setFilterPhone(String filterPhone)
  {
    this.filterPhone = filterPhone;
  }
  
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
  
  public String getFilterCreatedTime()
  {
    return this.filterCreatedTime;
  }
  
  public void setFilterCreatedTime(String filterCreatedTime)
  {
    this.filterCreatedTime = filterCreatedTime;
  }
}
