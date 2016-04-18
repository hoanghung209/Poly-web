package admin.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NewsBean
  implements Serializable
{
  private String id;
  private String titleVn;
  private String titleEn;
  private String contentVn;
  private String contentEn;
  private String avatar;
  private String slug_vn;
  private String slug_en;
  private String album_id;
  private String isDeleted;
  private String isShow;
  private String updatedBy;
  private String updatedTime;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private List<Map<String, String>> lstAlbum = new ArrayList<Map<String, String>>();
  private String filterTitle;
  private String filterShow;
  private String filterAlbum;
  
  public void resetModel()
  {
    this.id = "0";
    this.titleVn = "";
    this.titleEn = "";
    this.contentVn = "";
    this.contentEn = "";
    this.album_id = "0";
    this.avatar = "";
    this.slug_en = "";
    this.slug_vn = "";
    this.isDeleted = "0";
    this.isShow = "0";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("title_vn", this.titleVn);
    map.put("title_en", this.titleEn);
    map.put("content_vn", this.contentVn);
    map.put("content_en", this.contentEn);
    map.put("avatar", this.avatar);
    map.put("slug_vn", this.slug_vn);
    map.put("slug_en", this.slug_en);
    map.put("album_id", this.album_id);
    map.put("is_deleted", this.isDeleted);
    map.put("is_show", this.isShow);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.titleVn = ((String)map.get("title_vn"));
    this.titleEn = ((String)map.get("title_en"));
    this.contentVn = ((String)map.get("content_vn"));
    this.contentEn = ((String)map.get("content_en"));
    this.avatar = ((String)map.get("avatar"));
    this.slug_en = ((String)map.get("slug_en"));
    this.slug_vn = ((String)map.get("slug_vn"));
    this.album_id = ((String)map.get("album_id"));
    this.isDeleted = ((String)map.get("is_deleted"));
    this.isShow = ((String)map.get("is_show"));
    this.updatedBy = ((String)map.get("updated_by"));
    this.updatedTime = ((String)map.get("updated_time"));
  }
  
  public String getAlbum_id()
  {
    return this.album_id;
  }
  
  public void setAlbum_id(String album_id)
  {
    this.album_id = album_id;
  }
  
  public String getAvatar()
  {
    return this.avatar;
  }
  
  public void setAvatar(String avatar)
  {
    this.avatar = avatar;
  }
  
  public String getSlug_vn()
  {
    return this.slug_vn;
  }
  
  public void setSlug_vn(String slug_vn)
  {
    this.slug_vn = slug_vn;
  }
  
  public String getSlug_en()
  {
    return this.slug_en;
  }
  
  public void setSlug_en(String slug_en)
  {
    this.slug_en = slug_en;
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
  
  public String getIsDeleted()
  {
    return this.isDeleted;
  }
  
  public void setIsDeleted(String isDeleted)
  {
    this.isDeleted = isDeleted;
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
  
  public String getFilterTitle()
  {
    return this.filterTitle;
  }
  
  public void setFilterTitle(String filterTitle)
  {
    this.filterTitle = filterTitle;
  }
  
  public String getFilterShow()
  {
    return this.filterShow;
  }
  
  public void setFilterShow(String filterShow)
  {
    this.filterShow = filterShow;
  }
  
  public List<Map<String, String>> getLstAlbum()
  {
    return this.lstAlbum;
  }
  
  public void setLstAlbum(List<Map<String, String>> lstAlbum)
  {
    this.lstAlbum = lstAlbum;
  }
  
  public String getFilterAlbum()
  {
    return this.filterAlbum;
  }
  
  public void setFilterAlbum(String filterAlbum)
  {
    this.filterAlbum = filterAlbum;
  }
}
