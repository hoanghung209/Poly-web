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
public class CourseBean
  implements Serializable
{
  private String id;
  private String nameVn;
  private String nameEn;
  private String contentVn;
  private String contentEn;
  private String ord;
  private String menuId;
  private String menuName;
  private String status;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterName;
  private String filterMenuId;
  private String filterStatus;
  private String filterContent;
  
  public void resetModel()
  {
    this.id = "0";
    this.nameVn = "";
    this.contentVn = "";
    this.nameEn = "";
    this.contentEn = "";
    this.ord = "0";
    this.status = "0";
    this.menuId = "0";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("name_vn", this.nameVn);
    map.put("content_vn", this.contentVn);
    map.put("name_en", this.nameEn);
    map.put("content_en", this.contentEn);
    map.put("ord", this.ord);
    map.put("status", this.status);
    map.put("menu_id", this.menuId);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.nameVn = ((String)map.get("name_vn"));
    this.contentVn = ((String)map.get("content_vn"));
    this.nameEn = ((String)map.get("name_en"));
    this.contentEn = ((String)map.get("content_en"));
    this.ord = ((String)map.get("ord"));
    this.status = ((String)map.get("status"));
    this.menuId = ((String)map.get("menu_id"));
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
  
  public String getNameVn()
  {
    return this.nameVn;
  }
  
  public void setNameVn(String nameVn)
  {
    this.nameVn = nameVn;
  }
  
  public String getNameEn()
  {
    return this.nameEn;
  }
  
  public void setNameEn(String nameEn)
  {
    this.nameEn = nameEn;
  }
  
  public String getContentVn()
  {
    return this.contentVn;
  }
  
  public void setContentVn(String contentVn)
  {
    this.contentVn = contentVn;
  }
  
  public String getContentEn()
  {
    return this.contentEn;
  }
  
  public void setContentEn(String contentEn)
  {
    this.contentEn = contentEn;
  }
  
  public String getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(String ord)
  {
    this.ord = ord;
  }
  
  public String getMenuId()
  {
    return this.menuId;
  }
  
  public void setMenuId(String menuId)
  {
    this.menuId = menuId;
  }
  
  public String getMenuName()
  {
    return this.menuName;
  }
  
  public void setMenuName(String menuName)
  {
    this.menuName = menuName;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getFilterMenuId()
  {
    return this.filterMenuId;
  }
  
  public void setFilterMenuId(String filterMenuId)
  {
    this.filterMenuId = filterMenuId;
  }
  
  public String getFilterStatus()
  {
    return this.filterStatus;
  }
  
  public void setFilterStatus(String filterStatus)
  {
    this.filterStatus = filterStatus;
  }
  
  public String getFilterContent()
  {
    return this.filterContent;
  }
  
  public void setFilterContent(String filterContent)
  {
    this.filterContent = filterContent;
  }
}
