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
public class MenuBean implements Serializable {
    
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
	
	public void resetModel(){
		id = "0";
		nameVn = "";
		slugVn = "";
		nameEn = "";
		slugEn = "";
		ord = "0";
		parent ="0";
		position = "0";
		url = "";
		inpage = "0";
		banner ="0";
		status ="0";	
		createdBy = "0";
		createdTime ="";
		updatedBy = "0";
		updatedTime = "";	
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);
		map.put("name_vn", nameVn);
		map.put("slug_vn", slugVn);
		map.put("name_en", nameEn);
		map.put("slug_en", slugEn);
		map.put("ord", ord);
		map.put("parent", parent);
		map.put("position", position);
		map.put("url", url);
		map.put("inpage", inpage);
		map.put("banner", banner);
		map.put("status", status);	
		map.put("created_by", createdBy);
		map.put("created_time", createdTime);
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);		
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");
		nameVn = map.get("name_vn");
		slugVn = map.get("slug_vn");
		nameEn = map.get("name_en");
		slugEn = map.get("slug_en");
		ord = map.get("ord");
		parent = map.get("parent");
		position = map.get("position");
		url = map.get("url");
		inpage = map.get("inpage");
		banner = map.get("banner");
		status = map.get("status");		
		createdBy = map.get("created_by");
		createdTime = map.get("created_time");
		updatedBy = map.get("updated_by");
		updatedTime = map.get("updated_time");		
	}	


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameVn() {
		return nameVn;
	}
	public void setNameVn(String nameVn) {
		this.nameVn = nameVn;
	}
	public String getSlugVn() {
		return slugVn;
	}
	public void setSlugVn(String slugVn) {
		this.slugVn = slugVn;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getSlugEn() {
		return slugEn;
	}
	public void setSlugEn(String slugEn) {
		this.slugEn = slugEn;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInpage() {
		return inpage;
	}
	public void setInpage(String inpage) {
		this.inpage = inpage;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getFilterNameVn() {
		return filterNameVn;
	}
	public void setFilterNameVn(String filterNameVn) {
		this.filterNameVn = filterNameVn;
	}
	public String getFilterNameEn() {
		return filterNameEn;
	}
	public void setFilterNameEn(String filterNameEn) {
		this.filterNameEn = filterNameEn;
	}
	public String getFilterPosition() {
		return filterPosition;
	}
	public void setFilterPosition(String filterPosition) {
		this.filterPosition = filterPosition;
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
	public String getFilterStatus() {
		return filterStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}	
}