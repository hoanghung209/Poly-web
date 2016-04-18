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
public class SchedulerBean
  implements Serializable
{
  private String id;
  private String contentVn;
  private String contentEn;
  private String ord;
  private String chuongtrinhId;
  private String chuongtrinhName;
  private String row;
  private String column;
  private String status;
  private String updatedBy;
  private String updatedTime;
  private String language;
  private List<Map<String, String>> list;
  private List<Map<String, String>> listCourse;
  private List<Map<String, String>> listHeader;
  private List<Map<String, String>> listEdit;
  private Map<String, String> selectedItem;
  private Map<String, String> disabled = new HashMap<String, String>();
  private String filterChuongtrinhId;
  private String filterLanguage;
  private String actionRow;
  
  public void resetModel()
  {
    this.id = "0";
    this.contentVn = "";
    this.contentEn = "";
    this.ord = "0";
    this.row = "0";
    this.column = "0";
    this.status = "0";
    this.chuongtrinhId = "0";
  }
  
  public Map<String, String> serializeModel()
  {
    Map<String, String> map = new HashMap<String, String>();
    
    map.put("id", this.id);
    map.put("content_vn", this.contentVn);
    map.put("content_en", this.contentEn);
    map.put("ord", this.ord);
    map.put("row", this.row);
    map.put("column", this.column);
    map.put("status", this.status);
    map.put("chuongtrinh_id", this.chuongtrinhId);
    map.put("updated_by", this.updatedBy);
    map.put("updated_time", this.updatedTime);
    return map;
  }
  
  public void deserializeModel(Map<String, String> map)
  {
    this.id = ((String)map.get("id"));
    this.contentVn = ((String)map.get("content_vn"));
    this.contentEn = ((String)map.get("content_en"));
    this.ord = ((String)map.get("ord"));
    this.row = ((String)map.get("row"));
    this.column = ((String)map.get("column"));
    this.status = ((String)map.get("status"));
    this.chuongtrinhId = ((String)map.get("chuongtrinh_id"));
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
  
  public String getChuongtrinhId()
  {
    return this.chuongtrinhId;
  }
  
  public void setChuongtrinhId(String chuongtrinhId)
  {
    this.chuongtrinhId = chuongtrinhId;
  }
  
  public String getChuongtrinhName()
  {
    return this.chuongtrinhName;
  }
  
  public void setChuongtrinhName(String chuongtrinhName)
  {
    this.chuongtrinhName = chuongtrinhName;
  }
  
  public String getRow()
  {
    return this.row;
  }
  
  public void setRow(String row)
  {
    this.row = row;
  }
  
  public String getColumn()
  {
    return this.column;
  }
  
  public void setColumn(String column)
  {
    this.column = column;
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
  
  public List<Map<String, String>> getList()
  {
    return this.list;
  }
  
  public void setList(List<Map<String, String>> list)
  {
    this.list = list;
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
  
  public String getFilterChuongtrinhId()
  {
    return this.filterChuongtrinhId;
  }
  
  public void setFilterChuongtrinhId(String filterChuongtrinhId)
  {
    this.filterChuongtrinhId = filterChuongtrinhId;
  }
  
  public List<Map<String, String>> getListHeader()
  {
    return this.listHeader;
  }
  
  public void setListHeader(List<Map<String, String>> listHeader)
  {
    this.listHeader = listHeader;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setLanguage(String language)
  {
    this.language = language;
  }
  
  public List<Map<String, String>> getListCourse()
  {
    return this.listCourse;
  }
  
  public void setListCourse(List<Map<String, String>> listCourse)
  {
    this.listCourse = listCourse;
  }
  
  public String getFilterLanguage()
  {
    return this.filterLanguage;
  }
  
  public void setFilterLanguage(String filterLanguage)
  {
    this.filterLanguage = filterLanguage;
  }
  
  public String getActionRow()
  {
    return this.actionRow;
  }
  
  public void setActionRow(String actionRow)
  {
    this.actionRow = actionRow;
  }
  
  public List<Map<String, String>> getListEdit()
  {
    return this.listEdit;
  }
  
  public void setListEdit(List<Map<String, String>> listEdit)
  {
    this.listEdit = listEdit;
  }
}
