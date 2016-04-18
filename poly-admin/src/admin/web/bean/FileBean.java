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
public class FileBean
  implements Serializable
{
  private String id;
  private String nameVn;
  private String nameEn;
  private String albumId;
  private String isShow;
  private String isVideo;
  private String url;
  private String embed;
  private String title;
  private String alt;
  private String status;
  private String ord;
  private String updatedBy;
  private String updatedTime;
  private List<Map<String, String>> listAlbum;
  private String captcha;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterName;
  private String filterVideo;
  private String filterShow;
  private String filterAlbumId;
  
  public void resetModel()
  {
    this.id = "0";
    this.nameVn = "";
    this.nameEn = "";
    this.albumId = "0";
    this.isShow = "0";
    this.isVideo = "0";
    this.url = "";
    this.embed = "";
    this.title = "";
    this.alt = "";
    this.status = "0";
    this.ord = "0";
    this.updatedBy = "0";
    this.updatedTime = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("album_id", this.albumId);
    map.put("status", this.status);
    map.put("name_vn", this.nameVn);
    map.put("name_en", this.nameEn);
    map.put("is_video", this.isVideo);
    map.put("is_show", this.isShow);
    map.put("url", this.url);
    map.put("title", this.title);
    map.put("alt", this.alt);
    map.put("embed", this.embed);
    map.put("ord", this.ord);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.albumId = ((String)map.get("album_id"));
    this.status = ((String)map.get("status"));
    this.nameVn = ((String)map.get("name_vn"));
    this.nameEn = ((String)map.get("name_en"));
    this.isVideo = ((String)map.get("is_video"));
    this.isShow = ((String)map.get("is_show"));
    this.url = ((String)map.get("url"));
    this.title = ((String)map.get("title"));
    this.alt = ((String)map.get("alt"));
    this.embed = ((String)map.get("embed"));
    this.ord = ((String)map.get("ord"));
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
  
  public String getAlbumId()
  {
    return this.albumId;
  }
  
  public void setAlbumId(String albumId)
  {
    this.albumId = albumId;
  }
  
  public String getIsShow()
  {
    return this.isShow;
  }
  
  public void setIsShow(String isShow)
  {
    this.isShow = isShow;
  }
  
  public String getIsVideo()
  {
    return this.isVideo;
  }
  
  public void setIsVideo(String isVideo)
  {
    this.isVideo = isVideo;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getEmbed()
  {
    return this.embed;
  }
  
  public void setEmbed(String embed)
  {
    this.embed = embed;
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
  
  public String getFilterAlbumId()
  {
    return this.filterAlbumId;
  }
  
  public void setFilterAlbumId(String filterAlbumId)
  {
    this.filterAlbumId = filterAlbumId;
  }
  
  public List<Map<String, String>> getListAlbum()
  {
    return this.listAlbum;
  }
  
  public void setListAlbum(List<Map<String, String>> listAlbum)
  {
    this.listAlbum = listAlbum;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getAlt()
  {
    return this.alt;
  }
  
  public void setAlt(String alt)
  {
    this.alt = alt;
  }
  
  public String getCaptcha()
  {
    return this.captcha;
  }
  
  public void setCaptcha(String captcha)
  {
    this.captcha = captcha;
  }
  
  public String getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(String ord)
  {
    this.ord = ord;
  }
}
