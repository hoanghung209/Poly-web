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
public class ContentBean implements Serializable {
    
	private String id;	    
	private String titleVn;	
	private String contentVn;
	private String titleEn;
	private String contentEn; 
	private String descriptionVn;
	private String descriptionEn;
	private String image;
	private String img_pos;
	private String width;
	private String ord;	    
	private String menuId;	
	private String menuName;
	private String status;	 
	private String type;
	private String anchor;
	private String updatedBy;
	private String updatedTime;
	
	private int count;
	private List<Map<String, String>> list;
    private List<Map<String, String>> pageBar;
    private Map<String, String> selectedPage;
    private Map<String, String> selectedItem;
    
	private Map<String, String> disabled = new HashMap<String, String>();
	
	private String filterTitle;
	private String filterDescription;
	private String filterMenuId;
	private String filterStatus;
	private String filterType;
	private String filterContent;
	
	public void resetModel(){
		id = "0";
		titleVn = "";
		contentVn = "";
		titleEn = "";
		contentEn = "";
		ord = "0";		
		status ="0";	
		descriptionVn = "";
		descriptionEn = "";
		image = "";
		img_pos="0";
		width = "0";
		menuId = "0";
		type = "0";
		anchor= "";
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);
		map.put("title_vn", titleVn);
		map.put("content_vn", contentVn);
		map.put("title_en", titleEn);
		map.put("content_en", contentEn);
		map.put("ord", ord);		
		map.put("status", status);	
		map.put("description_vn", descriptionVn);	
		map.put("description_en", descriptionEn);
		map.put("image", image);	
		map.put("img_pos", img_pos);
		map.put("width", width);
		map.put("menu_id", menuId);	
		map.put("type", type);
		map.put("anchor", anchor);
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");
		titleVn = map.get("title_vn");
		contentVn = map.get("content_vn");
		titleEn = map.get("title_en");
		contentEn = map.get("content_en");
		ord = map.get("ord");		
		status = map.get("status");		
		descriptionVn = map.get("description_vn");
		descriptionEn = map.get("description_en");
		image = map.get("image");
		img_pos=map.get("img_pos");
		width=map.get("width");
		menuId = map.get("menu_id");	
		type = map.get("type");	
		anchor = map.get("anchor");
		updatedBy =  map.get("updated_by");
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
	public String getContentVn() {
		return contentVn;
	}
	public void setContentVn(String contentVn) {
		this.contentVn = contentVn;
	}
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	public String getContentEn() {
		return contentEn;
	}
	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}
	public String getDescriptionVn() {
		return descriptionVn;
	}
	public void setDescriptionVn(String descriptionVn) {
		this.descriptionVn = descriptionVn;
	}
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImg_pos() {
		return img_pos;
	}
	public void setImg_pos(String img_pos) {
		this.img_pos = img_pos;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getFilterDescription() {
		return filterDescription;
	}
	public void setFilterDescription(String filterDescription) {
		this.filterDescription = filterDescription;
	}
	public String getFilterMenuId() {
		return filterMenuId;
	}
	public void setFilterMenuId(String filterMenuId) {
		this.filterMenuId = filterMenuId;
	}
	public String getFilterStatus() {
		return filterStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterContent() {
		return filterContent;
	}
	public void setFilterContent(String filterContent) {
		this.filterContent = filterContent;
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
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}	
	
}