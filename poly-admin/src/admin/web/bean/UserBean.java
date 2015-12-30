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
public class UserBean implements Serializable {
    
	private String id;	    
	private String user;
	private String password;
	private String fullname;
	private String email;
	private String role;
	private String status;

	private int count;
	private List<Map<String, String>> list;
    private List<Map<String, String>> pageBar;
    private Map<String, String> selectedPage;
    private Map<String, String> selectedItem;
    
	private Map<String, String> disabled = new HashMap<String, String>();
	
	private String filterTitle;
	private String filterVideo;
	private String filterShow;
	
	public void resetModel(){
		id = "0";
		user = "";
		password="";
		fullname = "";
		email= "";
		role = "0";
		status ="0";	
		
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);		
		map.put("user", user);	
		map.put("password", password);
		map.put("fullname", fullname);
		map.put("email", email);
		map.put("role", role);	
		map.put("status", status);	
		
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");	
		user = map.get("user");
		password = map.get("password");
		fullname = map.get("fullname");
		email = map.get("email");
		role = map.get("role");
		status = map.get("status");	
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getFilterVideo() {
		return filterVideo;
	}
	public void setFilterVideo(String filterVideo) {
		this.filterVideo = filterVideo;
	}
	public String getFilterShow() {
		return filterShow;
	}
	public void setFilterShow(String filterShow) {
		this.filterShow = filterShow;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	


	
}