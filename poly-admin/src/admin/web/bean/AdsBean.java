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
public class AdsBean
  implements Serializable
{
  private String id;
  private String name;
  private String image;
  private String url;
  private String desc_vn;
  private String desc_en;
  private String more_vn;
  private String more_en;
  private String date_start;
  private String date_end;
  private String is_show;
  private String is_once;
  private String type;
  private String desc_top;
  private String desc_left;
  private String more_top;
  private String more_right;
  private String text_align;
  private String target;
  private String is_deleted;
  private String updated_by;
  private String updated_time;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterName;
  private String filterType;
  private String filterShow;
  
  public void resetModel()
  {
    this.id = "0";
    this.name = "";
    this.image = "";
    this.url = "";
    this.desc_vn = "";
    this.desc_en = "";
    this.more_vn = "";
    this.more_en = "";
    this.desc_top = "0";
    this.desc_left = "0";
    this.more_top = "0";
    this.more_right = "0";
    this.text_align = "";
    this.date_start = "";
    this.date_end = "";
    this.is_show = "0";
    this.is_once = "0";
    this.type = "0";
    this.is_deleted = "0";
    this.updated_by = "0";
    this.updated_time = "";
    this.target = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("name", this.name);
    map.put("image", this.image);
    map.put("url", this.url);
    map.put("desc_vn", this.desc_vn);
    map.put("desc_en", this.desc_en);
    map.put("more_vn", this.more_vn);
    map.put("more_en", this.more_en);
    map.put("desc_top", this.desc_top);
    map.put("desc_left", this.desc_left);
    map.put("more_top", this.more_top);
    map.put("more_right", this.more_right);
    map.put("text_align", this.text_align);
    map.put("date_start", this.date_start);
    map.put("date_end", this.date_end);
    map.put("is_show", this.is_show);
    map.put("is_once", this.is_once);
    map.put("type", this.type);
    map.put("is_deleted", this.is_deleted);
    map.put("updated_by", this.updated_by);
    map.put("updated_time", this.updated_time);
    map.put("target", this.target);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.name = ((String)map.get("name"));
    this.image = ((String)map.get("image"));
    this.url = ((String)map.get("url"));
    this.desc_vn = ((String)map.get("desc_vn"));
    this.desc_en = ((String)map.get("desc_en"));
    this.more_vn = ((String)map.get("more_vn"));
    this.more_en = ((String)map.get("more_en"));
    this.desc_top = ((String)map.get("desc_top"));
    this.desc_left = ((String)map.get("desc_left"));
    this.more_top = ((String)map.get("more_top"));
    this.more_right = ((String)map.get("more_right"));
    this.text_align = ((String)map.get("text_align"));
    this.date_start = ((String)map.get("date_start"));
    this.date_end = ((String)map.get("date_end"));
    this.is_show = ((String)map.get("is_show"));
    this.is_once = ((String)map.get("is_once"));
    this.type = ((String)map.get("type"));
    this.is_deleted = ((String)map.get("is_deleted"));
    this.updated_by = ((String)map.get("updated_by"));
    this.updated_time = ((String)map.get("updated_time"));
    this.target = ((String)map.get("target"));
  }
  
  public String getText_align()
  {
    return this.text_align;
  }
  
  public void setText_align(String text_align)
  {
    this.text_align = text_align;
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
  
  public String getDesc_vn()
  {
    return this.desc_vn;
  }
  
  public void setDesc_vn(String desc_vn)
  {
    this.desc_vn = desc_vn;
  }
  
  public String getDesc_en()
  {
    return this.desc_en;
  }
  
  public void setDesc_en(String desc_en)
  {
    this.desc_en = desc_en;
  }
  
  public String getDate_start()
  {
    return this.date_start;
  }
  
  public void setDate_start(String date_start)
  {
    this.date_start = date_start;
  }
  
  public String getDate_end()
  {
    return this.date_end;
  }
  
  public void setDate_end(String date_end)
  {
    this.date_end = date_end;
  }
  
  public String getIs_show()
  {
    return this.is_show;
  }
  
  public void setIs_show(String is_show)
  {
    this.is_show = is_show;
  }
  
  public String getIs_once()
  {
    return this.is_once;
  }
  
  public void setIs_once(String is_once)
  {
    this.is_once = is_once;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getIs_deleted()
  {
    return this.is_deleted;
  }
  
  public void setIs_deleted(String is_deleted)
  {
    this.is_deleted = is_deleted;
  }
  
  public String getUpdated_by()
  {
    return this.updated_by;
  }
  
  public void setUpdated_by(String updated_by)
  {
    this.updated_by = updated_by;
  }
  
  public String getUpdated_time()
  {
    return this.updated_time;
  }
  
  public void setUpdated_time(String updated_time)
  {
    this.updated_time = updated_time;
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
  
  public String getFilterType()
  {
    return this.filterType;
  }
  
  public void setFilterType(String filterType)
  {
    this.filterType = filterType;
  }
  
  public String getFilterShow()
  {
    return this.filterShow;
  }
  
  public void setFilterShow(String filterShow)
  {
    this.filterShow = filterShow;
  }
  
  public String getImage()
  {
    return this.image;
  }
  
  public void setImage(String image)
  {
    this.image = image;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getMore_vn()
  {
    return this.more_vn;
  }
  
  public void setMore_vn(String more_vn)
  {
    this.more_vn = more_vn;
  }
  
  public String getMore_en()
  {
    return this.more_en;
  }
  
  public void setMore_en(String more_en)
  {
    this.more_en = more_en;
  }
  
  public String getDesc_top()
  {
    return this.desc_top;
  }
  
  public void setDesc_top(String desc_top)
  {
    this.desc_top = desc_top;
  }
  
  public String getDesc_left()
  {
    return this.desc_left;
  }
  
  public void setDesc_left(String desc_left)
  {
    this.desc_left = desc_left;
  }
  
  public String getMore_top()
  {
    return this.more_top;
  }
  
  public void setMore_top(String more_top)
  {
    this.more_top = more_top;
  }
  
  public String getMore_right()
  {
    return this.more_right;
  }
  
  public void setMore_right(String more_right)
  {
    this.more_right = more_right;
  }
  
  public String getTarget()
  {
    return this.target;
  }
  
  public void setTarget(String target)
  {
    this.target = target;
  }
}
