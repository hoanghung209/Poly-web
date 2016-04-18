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
public class FeedbackBean
  implements Serializable
{
  private String id;
  private String name;
  private String chuc;
  private String phone;
  private String content;
  private String avatar;
  private String status;
  private String isShow;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private String sqlWhere;
  private String sqlOrder;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterName;
  private String filterPhone;
  private String filterStatus;
  private String filterShow;
  private String filterCreatedTime;
  
  public void resetModel()
  {
    this.id = "0";
    this.name = "";
    this.chuc = "";
    this.phone = "";
    this.content = "";
    this.avatar = "";
    this.status = "0";
    this.isShow = "0";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("name", this.name);
    map.put("chuc", this.chuc);
    map.put("phone", this.phone);
    map.put("content", this.content);
    map.put("avatar", this.avatar);
    map.put("status", this.status);
    map.put("is_show", this.isShow);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.name = ((String)map.get("name"));
    this.status = ((String)map.get("status"));
    this.chuc = ((String)map.get("chuc"));
    this.content = ((String)map.get("content"));
    this.avatar = ((String)map.get("avatar"));
    this.phone = ((String)map.get("phone"));
    this.isShow = ((String)map.get("is_show"));
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
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getChuc()
  {
    return this.chuc;
  }
  
  public void setChuc(String chuc)
  {
    this.chuc = chuc;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getIsShow()
  {
    return this.isShow;
  }
  
  public void setIsShow(String isShow)
  {
    this.isShow = isShow;
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
  
  public String getFilterStatus()
  {
    return this.filterStatus;
  }
  
  public void setFilterStatus(String filterStatus)
  {
    this.filterStatus = filterStatus;
  }
  
  public String getAvatar()
  {
    return this.avatar;
  }
  
  public void setAvatar(String avatar)
  {
    this.avatar = avatar;
  }
  
  public String getFilterShow()
  {
    return this.filterShow;
  }
  
  public void setFilterShow(String filterShow)
  {
    this.filterShow = filterShow;
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
