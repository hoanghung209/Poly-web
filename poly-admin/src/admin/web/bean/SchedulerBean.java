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
public class SchedulerBean implements Serializable {
    
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
	
	
	public void resetModel(){
		id = "0";		
		contentVn = "";		
		contentEn = "";
		ord = "0";		
		row = "0";
		column = "0";
		status ="0";
		chuongtrinhId = "0";
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);		
		map.put("content_vn", contentVn);		
		map.put("content_en", contentEn);
		map.put("ord", ord);	
		map.put("row", row);
		map.put("column", column);
		map.put("status", status);			
		map.put("chuongtrinh_id", chuongtrinhId);			
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");		
		contentVn = map.get("content_vn");		
		contentEn = map.get("content_en");
		ord = map.get("ord");	
		row = map.get("row");	
		column = map.get("column");	
		status = map.get("status");	
		chuongtrinhId = map.get("chuongtrinh_id");
		updatedBy =  map.get("updated_by");
		updatedTime = map.get("updated_time");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getChuongtrinhId() {
		return chuongtrinhId;
	}
	public void setChuongtrinhId(String chuongtrinhId) {
		this.chuongtrinhId = chuongtrinhId;
	}
	public String getChuongtrinhName() {
		return chuongtrinhName;
	}
	public void setChuongtrinhName(String chuongtrinhName) {
		this.chuongtrinhName = chuongtrinhName;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<Map<String, String>> getList() {
		return list;
	}
	public void setList(List<Map<String, String>> list) {
		this.list = list;
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
	public String getFilterChuongtrinhId() {
		return filterChuongtrinhId;
	}
	public void setFilterChuongtrinhId(String filterChuongtrinhId) {
		this.filterChuongtrinhId = filterChuongtrinhId;
	}
	public List<Map<String, String>> getListHeader() {
		return listHeader;
	}
	public void setListHeader(List<Map<String, String>> listHeader) {
		this.listHeader = listHeader;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public List<Map<String, String>> getListCourse() {
		return listCourse;
	}
	public void setListCourse(List<Map<String, String>> listCourse) {
		this.listCourse = listCourse;
	}
	public String getFilterLanguage() {
		return filterLanguage;
	}
	public void setFilterLanguage(String filterLanguage) {
		this.filterLanguage = filterLanguage;
	}
	public String getActionRow() {
		return actionRow;
	}
	public void setActionRow(String actionRow) {
		this.actionRow = actionRow;
	}
	public List<Map<String, String>> getListEdit() {
		return listEdit;
	}
	public void setListEdit(List<Map<String, String>> listEdit) {
		this.listEdit = listEdit;
	}
}