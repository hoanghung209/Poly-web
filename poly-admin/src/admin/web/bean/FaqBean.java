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
public class FaqBean
  implements Serializable
{
  private String id;
  private String title;
  private String content;
  private String ord;
  private String status;
  private String language;
  private int count;
  private List<Map<String, String>> list;
  private List<Map<String, String>> pageBar;
  private Map<String, String> selectedPage;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterTitle;
  private String filterLang;
  
  public void resetModel()
  {
    this.id = "0";
    this.title = "";
    this.content = "";
    this.ord = "0";
    this.status = "0";
    this.language = "";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("ord", this.ord);
    map.put("status", this.status);
    map.put("title", this.title);
    map.put("content", this.content);
    map.put("language", this.language);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.ord = ((String)map.get("ord"));
    this.status = ((String)map.get("status"));
    this.title = ((String)map.get("title"));
    this.content = ((String)map.get("content"));
    this.language = ((String)map.get("language"));
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
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
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setLanguage(String language)
  {
    this.language = language;
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
  
  public String getFilterLang()
  {
    return this.filterLang;
  }
  
  public void setFilterLang(String filterLang)
  {
    this.filterLang = filterLang;
  }
}
