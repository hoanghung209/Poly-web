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
public class MenuBean
  implements Serializable
{
  private String id;
  private String nameVn;
  private String slugVn;
  private String nameEn;
  private String slugEn;
  private String ord;
  private String parent;
  private String parentName;
  private String position;
  private String url;
  private String anchor;
  private String inpage;
  private String banner;
  private String status;
  private String createdBy;
  private String createdTime;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterNameVn;
  private String filterNameEn;
  private String filterPosition;
  private String filterStatus;
  
  public void resetModel()
  {
    this.id = "0";
    this.nameVn = "";
    this.slugVn = "";
    this.nameEn = "";
    this.slugEn = "";
    this.ord = "0";
    this.parent = "0";
    this.position = "0";
    this.url = "";
    this.anchor = "";
    this.inpage = "0";
    this.banner = "0";
    this.status = "0";
    this.createdBy = "0";
    this.createdTime = "";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("name_vn", this.nameVn);
    map.put("slug_vn", this.slugVn);
    map.put("name_en", this.nameEn);
    map.put("slug_en", this.slugEn);
    map.put("ord", this.ord);
    map.put("parent", this.parent);
    map.put("position", this.position);
    map.put("url", this.url);
    map.put("anchor", this.anchor);
    map.put("inpage", this.inpage);
    map.put("banner", this.banner);
    map.put("status", this.status);
    map.put("created_by", this.createdBy);
    map.put("created_time", this.createdTime);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.nameVn = ((String)map.get("name_vn"));
    this.slugVn = ((String)map.get("slug_vn"));
    this.nameEn = ((String)map.get("name_en"));
    this.slugEn = ((String)map.get("slug_en"));
    this.ord = ((String)map.get("ord"));
    this.parent = ((String)map.get("parent"));
    this.position = ((String)map.get("position"));
    this.url = ((String)map.get("url"));
    this.anchor = ((String)map.get("anchor"));
    this.inpage = ((String)map.get("inpage"));
    this.banner = ((String)map.get("banner"));
    this.status = ((String)map.get("status"));
    this.createdBy = ((String)map.get("created_by"));
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
  
  public String getNameVn()
  {
    return this.nameVn;
  }
  
  public void setNameVn(String nameVn)
  {
    this.nameVn = nameVn;
  }
  
  public String getSlugVn()
  {
    return this.slugVn;
  }
  
  public void setSlugVn(String slugVn)
  {
    this.slugVn = slugVn;
  }
  
  public String getNameEn()
  {
    return this.nameEn;
  }
  
  public void setNameEn(String nameEn)
  {
    this.nameEn = nameEn;
  }
  
  public String getSlugEn()
  {
    return this.slugEn;
  }
  
  public void setSlugEn(String slugEn)
  {
    this.slugEn = slugEn;
  }
  
  public String getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(String ord)
  {
    this.ord = ord;
  }
  
  public String getParent()
  {
    return this.parent;
  }
  
  public void setParent(String parent)
  {
    this.parent = parent;
  }
  
  public String getPosition()
  {
    return this.position;
  }
  
  public void setPosition(String position)
  {
    this.position = position;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getInpage()
  {
    return this.inpage;
  }
  
  public void setInpage(String inpage)
  {
    this.inpage = inpage;
  }
  
  public String getBanner()
  {
    return this.banner;
  }
  
  public void setBanner(String banner)
  {
    this.banner = banner;
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
  
  public String getFilterNameVn()
  {
    return this.filterNameVn;
  }
  
  public void setFilterNameVn(String filterNameVn)
  {
    this.filterNameVn = filterNameVn;
  }
  
  public String getFilterNameEn()
  {
    return this.filterNameEn;
  }
  
  public void setFilterNameEn(String filterNameEn)
  {
    this.filterNameEn = filterNameEn;
  }
  
  public String getFilterPosition()
  {
    return this.filterPosition;
  }
  
  public void setFilterPosition(String filterPosition)
  {
    this.filterPosition = filterPosition;
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
  
  public String getFilterStatus()
  {
    return this.filterStatus;
  }
  
  public void setFilterStatus(String filterStatus)
  {
    this.filterStatus = filterStatus;
  }
  
  public String getParentName()
  {
    return this.parentName;
  }
  
  public void setParentName(String parentName)
  {
    this.parentName = parentName;
  }
  
  public String getAnchor()
  {
    return this.anchor;
  }
  
  public void setAnchor(String anchor)
  {
    this.anchor = anchor;
  }
}
