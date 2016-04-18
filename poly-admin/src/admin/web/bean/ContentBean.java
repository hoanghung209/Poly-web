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
public class ContentBean
  implements Serializable
{
  private String id;
  private String titleVn;
  private String contentVn;
  private String titleEn;
  private String contentEn;
  private String descriptionVn;
  private String descriptionEn;
  private String image;
  private String img_pos;
  private String width;
  private String ord;
  private String menuId;
  private String menuName;
  private String status;
  private String type;
  private String anchor;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterTitle;
  private String filterDescription;
  private String filterMenuId;
  private String filterStatus;
  private String filterType;
  private String filterContent;
  
  public void resetModel()
  {
    this.id = "0";
    this.titleVn = "";
    this.contentVn = "";
    this.titleEn = "";
    this.contentEn = "";
    this.ord = "0";
    this.status = "0";
    this.descriptionVn = "";
    this.descriptionEn = "";
    this.image = "";
    this.img_pos = "0";
    this.width = "0";
    this.menuId = "0";
    this.type = "0";
    this.anchor = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("title_vn", this.titleVn);
    map.put("content_vn", this.contentVn);
    map.put("title_en", this.titleEn);
    map.put("content_en", this.contentEn);
    map.put("ord", this.ord);
    map.put("status", this.status);
    map.put("description_vn", this.descriptionVn);
    map.put("description_en", this.descriptionEn);
    map.put("image", this.image);
    map.put("img_pos", this.img_pos);
    map.put("width", this.width);
    map.put("menu_id", this.menuId);
    map.put("type", this.type);
    map.put("anchor", this.anchor);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.titleVn = ((String)map.get("title_vn"));
    this.contentVn = ((String)map.get("content_vn"));
    this.titleEn = ((String)map.get("title_en"));
    this.contentEn = ((String)map.get("content_en"));
    this.ord = ((String)map.get("ord"));
    this.status = ((String)map.get("status"));
    this.descriptionVn = ((String)map.get("description_vn"));
    this.descriptionEn = ((String)map.get("description_en"));
    this.image = ((String)map.get("image"));
    this.img_pos = ((String)map.get("img_pos"));
    this.width = ((String)map.get("width"));
    this.menuId = ((String)map.get("menu_id"));
    this.type = ((String)map.get("type"));
    this.anchor = ((String)map.get("anchor"));
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
  
  public String getTitleVn()
  {
    return this.titleVn;
  }
  
  public void setTitleVn(String titleVn)
  {
    this.titleVn = titleVn;
  }
  
  public String getContentVn()
  {
    return this.contentVn;
  }
  
  public void setContentVn(String contentVn)
  {
    this.contentVn = contentVn;
  }
  
  public String getTitleEn()
  {
    return this.titleEn;
  }
  
  public void setTitleEn(String titleEn)
  {
    this.titleEn = titleEn;
  }
  
  public String getContentEn()
  {
    return this.contentEn;
  }
  
  public void setContentEn(String contentEn)
  {
    this.contentEn = contentEn;
  }
  
  public String getDescriptionVn()
  {
    return this.descriptionVn;
  }
  
  public void setDescriptionVn(String descriptionVn)
  {
    this.descriptionVn = descriptionVn;
  }
  
  public String getDescriptionEn()
  {
    return this.descriptionEn;
  }
  
  public void setDescriptionEn(String descriptionEn)
  {
    this.descriptionEn = descriptionEn;
  }
  
  public String getImage()
  {
    return this.image;
  }
  
  public void setImage(String image)
  {
    this.image = image;
  }
  
  public String getImg_pos()
  {
    return this.img_pos;
  }
  
  public void setImg_pos(String img_pos)
  {
    this.img_pos = img_pos;
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
  
  public String getFilterDescription()
  {
    return this.filterDescription;
  }
  
  public void setFilterDescription(String filterDescription)
  {
    this.filterDescription = filterDescription;
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
  
  public String getMenuName()
  {
    return this.menuName;
  }
  
  public void setMenuName(String menuName)
  {
    this.menuName = menuName;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getAnchor()
  {
    return this.anchor;
  }
  
  public void setAnchor(String anchor)
  {
    this.anchor = anchor;
  }
  
  public String getFilterType()
  {
    return this.filterType;
  }
  
  public void setFilterType(String filterType)
  {
    this.filterType = filterType;
  }
  
  public String getFilterContent()
  {
    return this.filterContent;
  }
  
  public void setFilterContent(String filterContent)
  {
    this.filterContent = filterContent;
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
  
  public String getWidth()
  {
    return this.width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
}
