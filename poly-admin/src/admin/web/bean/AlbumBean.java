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
public class AlbumBean
  implements Serializable
{
  private String id;
  private String titleVn;
  private String titleEn;
  private String slugVn;
  private String slugEn;
  private String ord;
  private String img_avatar;
  private String status;
  private String type;
  private String isShow;
  private String updatedBy;
  private String updatedTime;
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
    this.titleVn = "";
    this.titleEn = "";
    this.slugVn = "";
    this.slugEn = "";
    this.ord = "0";
    this.img_avatar = "";
    this.status = "0";
    this.type = "0";
    this.isShow = "0";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("ord", this.ord);
    map.put("status", this.status);
    map.put("title_vn", this.titleVn);
    map.put("title_en", this.titleEn);
    map.put("slug_vn", this.slugVn);
    map.put("slug_en", this.slugEn);
    map.put("img_avatar", this.img_avatar);
    map.put("type", this.type);
    map.put("is_show", this.isShow);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.ord = ((String)map.get("ord"));
    this.status = ((String)map.get("status"));
    this.titleVn = ((String)map.get("title_vn"));
    this.titleEn = ((String)map.get("title_en"));
    this.slugVn = ((String)map.get("slug_vn"));
    this.slugEn = ((String)map.get("slug_en"));
    this.type = ((String)map.get("type"));
    this.img_avatar = ((String)map.get("img_avatar"));
    this.isShow = ((String)map.get("is_show"));
    this.updatedBy = ((String)map.get("updated_by"));
    this.updatedTime = ((String)map.get("updated_time"));
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
  
  public String getTitleEn()
  {
    return this.titleEn;
  }
  
  public void setTitleEn(String titleEn)
  {
    this.titleEn = titleEn;
  }
  
  public String getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(String ord)
  {
    this.ord = ord;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getSlugVn()
  {
    return this.slugVn;
  }
  
  public void setSlugVn(String slugVn)
  {
    this.slugVn = slugVn;
  }
  
  public String getSlugEn()
  {
    return this.slugEn;
  }
  
  public void setSlugEn(String slugEn)
  {
    this.slugEn = slugEn;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
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
  
  public String getImg_avatar()
  {
    return this.img_avatar;
  }
  
  public void setImg_avatar(String img_avatar)
  {
    this.img_avatar = img_avatar;
  }
}
