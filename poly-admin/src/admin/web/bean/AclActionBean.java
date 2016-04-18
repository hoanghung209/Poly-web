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
public class AclActionBean
  implements Serializable
{
  private String id;
  private String name;
  private String description;
  private String status;
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
  private String filterName;
  private String filterDescription;
  private String filterStatus;
  
  public void resetModel()
  {
    this.id = "";
    this.name = "";
    this.description = "";
    this.status = "0";
    this.createdBy = "0";
    this.createdTime = "";
    this.lastUpdatedBy = "0";
    this.lastUpdatedTime = "";
  }
  
  public void resetModel2()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", "");
    map.put("name", "");
    map.put("description", "");
    map.put("status", "0");
    map.put("created_by", "0");
    map.put("created_time", "");
    map.put("last_updated_by", "0");
    map.put("last_updated_time", "");
    deserializeModel(map);
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("name", this.name);
    map.put("description", this.description);
    map.put("status", this.status);
    map.put("created_by", this.createdBy);
    map.put("created_time", this.createdTime);
    map.put("last_updated_by", this.lastUpdatedBy);
    map.put("last_updated_time", this.lastUpdatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.name = ((String)map.get("name"));
    this.description = ((String)map.get("description"));
    this.status = ((String)map.get("status"));
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
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getFilterName()
  {
    return this.filterName;
  }
  
  public void setFilterName(String filterName)
  {
    this.filterName = filterName;
  }
  
  public String getFilterDescription()
  {
    return this.filterDescription;
  }
  
  public void setFilterDescription(String filterDescription)
  {
    this.filterDescription = filterDescription;
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
