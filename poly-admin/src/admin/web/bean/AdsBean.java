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
public class AdsBean implements Serializable {
    
	private String id;	    
	private String name;
	private String url_vn;
	private String url_en;
	private String desc_vn;
	private String desc_en;
	private String date_start;
	private String date_end;
	private String is_show;
	private String is_once;
	private String type;
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
	
	public void resetModel(){
		id="0";	    
		name="";
		url_vn="";
		url_en="";
		desc_vn="";
		desc_en="";
		date_start="";
		date_end="";
		is_show="0";
		is_once="0";
		type="0";
		is_deleted="0";
		updated_by="0";
		updated_time="";
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);	    
		map.put("name", name);
		map.put("url_vn", url_vn);
		map.put("url_en", url_en);
		map.put("desc_vn", desc_vn);
		map.put("desc_en", desc_en);
		map.put("date_start", date_start);
		map.put("date_end", date_end);
		map.put("is_show", is_show);
		map.put("is_once", is_once);
		map.put("type", type);
		map.put("is_deleted", is_deleted);
		map.put("updated_by", updated_by);
		map.put("updated_time", updated_time);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id=map.get("id");	    
		name=map.get("name");
		url_vn=map.get("url_vn");
		url_en=map.get("url_en");
		desc_vn=map.get("desc_vn");
		desc_en=map.get("desc_en");
		date_start=map.get("date_start");
		date_end=map.get("date_end");
		is_show=map.get("is_show");
		is_once=map.get("is_once");
		type=map.get("type");
		is_deleted=map.get("is_deleted");
		updated_by=map.get("updated_by");
		updated_time=map.get("updated_time");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl_vn() {
		return url_vn;
	}
	public void setUrl_vn(String url_vn) {
		this.url_vn = url_vn;
	}
	public String getUrl_en() {
		return url_en;
	}
	public void setUrl_en(String url_en) {
		this.url_en = url_en;
	}
	public String getDesc_vn() {
		return desc_vn;
	}
	public void setDesc_vn(String desc_vn) {
		this.desc_vn = desc_vn;
	}
	public String getDesc_en() {
		return desc_en;
	}
	public void setDesc_en(String desc_en) {
		this.desc_en = desc_en;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	public String getIs_once() {
		return is_once;
	}
	public void setIs_once(String is_once) {
		this.is_once = is_once;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Map<String, String>> getList() {
		return list;
	}
	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}
	public List<Map<String, String>> getPageBar() {
		return pageBar;
	}
	public void setPageBar(List<Map<String, String>> pageBar) {
		this.pageBar = pageBar;
	}
	public Map<String, String> getSelectedPage() {
		return selectedPage;
	}
	public void setSelectedPage(Map<String, String> selectedPage) {
		this.selectedPage = selectedPage;
	}
	public Map<String, String> getSelectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(Map<String, String> selectedItem) {
		this.selectedItem = selectedItem;
	}
	public Map<String, String> getDisabled() {
		return disabled;
	}
	public void setDisabled(Map<String, String> disabled) {
		this.disabled = disabled;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterShow() {
		return filterShow;
	}
	public void setFilterShow(String filterShow) {
		this.filterShow = filterShow;
	}
	
	
}