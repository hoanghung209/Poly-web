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
public class UserBean
  implements Serializable
{
  private String id;
  private String user;
  private String password;
  private String fullname;
  private String email;
  private String role;
  private String status;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterTitle;
  private String filterVideo;
  private String filterShow;
  
  public void resetModel()
  {
    this.id = "0";
    this.user = "";
    this.password = "";
    this.fullname = "";
    this.email = "";
    this.role = "0";
    this.status = "0";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("user", this.user);
    map.put("password", this.password);
    map.put("fullname", this.fullname);
    map.put("email", this.email);
    map.put("role", this.role);
    map.put("status", this.status);
    
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.user = ((String)map.get("user"));
    this.password = ((String)map.get("password"));
    this.fullname = ((String)map.get("fullname"));
    this.email = ((String)map.get("email"));
    this.role = ((String)map.get("role"));
    this.status = ((String)map.get("status"));
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getRole()
  {
    return this.role;
  }
  
  public void setRole(String role)
  {
    this.role = role;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getFilterTitle()
  {
    return this.filterTitle;
  }
  
  public void setFilterTitle(String filterTitle)
  {
    this.filterTitle = filterTitle;
  }
  
  public String getFilterVideo()
  {
    return this.filterVideo;
  }
  
  public void setFilterVideo(String filterVideo)
  {
    this.filterVideo = filterVideo;
  }
  
  public String getFilterShow()
  {
    return this.filterShow;
  }
  
  public void setFilterShow(String filterShow)
  {
    this.filterShow = filterShow;
  }
  
  public String getFullname()
  {
    return this.fullname;
  }
  
  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
}
