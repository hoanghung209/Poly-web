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
public class FileBean implements Serializable {
    
	private String id;	    
	private String nameVn;	
	private String nameEn;		
	private String albumId;	   
	private String isShow;
	private String isVideo;
	private String url;
	private String embed;
	private String title;
	private String alt;
	private String status;
	private String updatedBy;
	private String updatedTime;
	private List<Map<String, String>> listAlbum; 
	private String captcha;
	
	private int count;
	private List<Map<String, String>> list;
    private List<Map<String, String>> pageBar;
    private Map<String, String> selectedPage;
    private Map<String, String> selectedItem;
    
	private Map<String, String> disabled = new HashMap<String, String>();
	
	private String filterName;
	private String filterVideo;
	private String filterShow;
	private String filterAlbumId;
	
	public void resetModel(){
		id = "0";	
		nameVn = "";
		nameEn = "";
		albumId = "0";
		isShow = "0";			
		isVideo = "0";
		url = "";
		embed = "";
		title = "";
		alt= "";
		status = "0";
		updatedBy = "0";
		updatedTime = "";	
	}
	public Map<String, String> serializeModel() {		
		Map<String, String> map = new HashMap<String, String>();	

		map.put("id", id);		
		map.put("album_id", albumId);	
		map.put("status", status);	
		map.put("name_vn", nameVn);
		map.put("name_en", nameEn);
		map.put("is_video", isVideo);	
		map.put("is_show", isShow);
		map.put("url", url);
		map.put("title", title);
		map.put("alt", alt);
		map.put("embed", embed);
		map.put("updated_by", updatedBy);
		map.put("updated_time", updatedTime);
		return map;		
	}
	public void deserializeModel(Map<String, String> map) {

		id = map.get("id");	
		albumId = map.get("album_id");		
		status = map.get("status");		
		nameVn = map.get("name_vn");
		nameEn = map.get("name_en");
		isVideo = map.get("is_video");
		isShow = map.get("is_show");
		url = map.get("url");
		title = map.get("title");
		alt = map.get("alt");
		embed = map.get("embed");
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
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsVideo() {
		return isVideo;
	}
	public void setIsVideo(String isVideo) {
		this.isVideo = isVideo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmbed() {
		return embed;
	}
	public void setEmbed(String embed) {
		this.embed = embed;
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
	public String getFilterAlbumId() {
		return filterAlbumId;
	}
	public void setFilterAlbumId(String filterAlbumId) {
		this.filterAlbumId = filterAlbumId;
	}
	public List<Map<String, String>> getListAlbum() {
		return listAlbum;
	}
	public void setListAlbum(List<Map<String, String>> listAlbum) {
		this.listAlbum = listAlbum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	

	
}