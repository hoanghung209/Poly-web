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
public class FeedbackBean implements Serializable {
    
	private String id;	    
	private String name;	
	private String chuc;	
	private String phone;	
	private String content;
	private String avatar;
	private String status;
	private String isShow;
	private String updatedBy;
	private String updatedTime;

	private int count;
	private List<Map<String, String>> list;
    private List<Map<String, String>> pageBar;
    private Map<String, String> selectedPage;
    private Map<String, String> selectedItem;
    
	private Map<String, String> disabled = new HashMap<String, String>();
	
	private String filterName;
	private String filterPhone;
	private String filterStatus;
	private String filterShow;
	
	public void resetModel(){
		id = "0";
		 name="";	
		 chuc="";	
		phone="";	
		 content="";
		 avatar = "";
		 status="0";
		 isShow="0";
		updatedBy="0";
		 updatedTime="";
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);		
		map.put("name",name);	
		map.put("chuc", chuc);	
		map.put("phone", phone);
		map.put("content", content);
		map.put("avatar", avatar);
		map.put("status", status);	
		map.put("is_show", isShow);	
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");	
		name = map.get("name");		
		status = map.get("status");		
		chuc = map.get("chuc");
		content = map.get("content");
		avatar = map.get("avatar");
		phone = map.get("phone");
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChuc() {
		return chuc;
	}
	public void setChuc(String chuc) {
		this.chuc = chuc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterPhone() {
		return filterPhone;
	}
	public void setFilterPhone(String filterPhone) {
		this.filterPhone = filterPhone;
	}
	public String getFilterStatus() {
		return filterStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getFilterShow() {
		return filterShow;
	}
	public void setFilterShow(String filterShow) {
		this.filterShow = filterShow;
	}
	

	
}