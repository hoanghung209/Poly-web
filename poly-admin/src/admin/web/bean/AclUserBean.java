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
public class AclUserBean
  implements Serializable
{
  private String id;
  private String username;
  private String password;
  private String fullName;
  private String sex;
  private String birthDay;
  private String email;
  private String mobile;
  private String status;
  private String deleted;
  private String createdBy;
  private String createdTime;
  private String lastUpdatedBy;
  private String lastUpdatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterUsername;
  private String filterFullName;
  private String filterStatus;
  
  public void resetModel()
  {
    this.id = "0";
    this.username = " ";
    this.password = " ";
    this.fullName = " ";
    this.sex = "0";
    this.birthDay = "";
    this.email = "";
    this.mobile = "";
    this.status = "0";
    this.deleted = "0";
    this.createdBy = "0";
    this.createdTime = "";
    this.lastUpdatedBy = "0";
    this.lastUpdatedTime = "";
  }
  
  public void resetModel2()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", "0");
    map.put("username", "");
    map.put("password", "");
    map.put("full_name", "");
    map.put("sex", "0");
    map.put("birth_day", "null");
    map.put("email", "");
    map.put("mobile", "");
    map.put("status", "0");
    map.put("deleted", "0");
    map.put("cp_id", "0");
    map.put("created_by", "0");
    map.put("created_time", "null");
    map.put("last_updated_by", "0");
    map.put("last_updated_time", "null");
    deserializeModel(map);
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("username", this.username.trim());
    map.put("password", this.password);
    map.put("full_name", this.fullName);
    map.put("sex", this.sex);
    map.put("birth_day", this.birthDay);
    map.put("email", this.email);
    map.put("mobile", this.mobile);
    map.put("status", this.status);
    map.put("deleted", this.deleted);
    map.put("created_by", this.createdBy);
    map.put("created_time", this.createdTime);
    map.put("last_updated_by", this.lastUpdatedBy);
    map.put("last_updated_time", this.lastUpdatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.username = ((String)map.get("username"));
    this.password = ((String)map.get("password"));
    this.fullName = ((String)map.get("full_name"));
    this.sex = ((String)map.get("sex"));
    this.birthDay = ((String)map.get("birthDay"));
    this.email = ((String)map.get("email"));
    this.mobile = ((String)map.get("mobile"));
    this.status = ((String)map.get("status"));
    this.deleted = ((String)map.get("deleted"));
    this.createdBy = ((String)map.get("created_by"));
    this.createdTime = ((String)map.get("created_time"));
    this.lastUpdatedBy = ((String)map.get("last_updated_by"));
    this.lastUpdatedTime = ((String)map.get("last_updated_time"));
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getFullName()
  {
    return this.fullName;
  }
  
  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  
  public String getBirthDay()
  {
    return this.birthDay;
  }
  
  public void setBirthDay(String birthDay)
  {
    this.birthDay = birthDay;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getDeleted()
  {
    return this.deleted;
  }
  
  public void setDeleted(String deleted)
  {
    this.deleted = deleted;
  }
  
  public String getCreatedBy()
  {
    return this.createdBy;
  }
  
  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }
  
  public String getCreatedTime()
  {
    return this.createdTime;
  }
  
  public void setCreatedTime(String createdTime)
  {
    this.createdTime = createdTime;
  }
  
  public String getLastUpdatedBy()
  {
    return this.lastUpdatedBy;
  }
  
  public void setLastUpdatedBy(String lastUpdatedBy)
  {
    this.lastUpdatedBy = lastUpdatedBy;
  }
  
  public String getLastUpdatedTime()
  {
    return this.lastUpdatedTime;
  }
  
  public void setLastUpdatedTime(String lastUpdatedTime)
  {
    this.lastUpdatedTime = lastUpdatedTime;
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
  
  public String getFilterUsername()
  {
    return this.filterUsername;
  }
  
  public void setFilterUsername(String filterUsername)
  {
    this.filterUsername = filterUsername;
  }
  
  public String getFilterFullName()
  {
    return this.filterFullName;
  }
  
  public void setFilterFullName(String filterFullName)
  {
    this.filterFullName = filterFullName;
  }
  
  public String getFilterStatus()
  {
    return this.filterStatus;
  }
  
  public void setFilterStatus(String filterStatus)
  {
    this.filterStatus = filterStatus;
  }
}
