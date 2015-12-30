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
public class NewsBean implements Serializable {
    
	private String id;	    
	private String titleVn;	
	private String titleEn;	
	private String contentVn;
	private String contentEn;	
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
	
	private String filterTitle;	
	private String filterShow;
	
	public void resetModel(){
		id = "0";	
		titleVn = "";
		titleEn = "";
		contentVn = "";
		contentEn = "";
		isDeleted = "0";
		isShow = "0";
		updatedBy = "0";
		updatedTime = "";	
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);
		map.put("title_vn", titleVn);
		map.put("title_en", titleEn);
		map.put("content_vn", contentVn);
		map.put("content_en", contentEn);
		map.put("is_deleted", isDeleted);	
		map.put("is_show", isShow);	
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");				
		titleVn = map.get("title_vn");
		titleEn = map.get("title_en");
		contentVn = map.get("content_vn");
		contentEn = map.get("content_en");
		isDeleted = map.get("is_deleted");
		isShow = map.get("is_show");
		updatedBy = map.get("updated_by");
		updatedTime = map.get("updated_time");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitleVn() {
		return titleVn;
	}
	public void setTitleVn(String titleVn) {
		this.titleVn = titleVn;
	}
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	public String getContentVn() {
		return contentVn;
	}
	public void setContentVn(String contentVn) {
		this.contentVn = contentVn;
	}
	public String getContentEn() {
		return contentEn;
	}
	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
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
	public String getFilterTitle() {
		return filterTitle;
	}
	public void setFilterTitle(String filterTitle) {
		this.filterTitle = filterTitle;
	}
	public String getFilterShow() {
		return filterShow;
	}
	public void setFilterShow(String filterShow) {
		this.filterShow = filterShow;
	}
	
	
}