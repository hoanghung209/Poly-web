package admin.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.vega.security.AccessControl;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
import admin.web.bc.LanguageBC;
import admin.web.bean.FileBean;
import admin.web.common.Paginator;
import admin.web.dao.AlbumDAO;
import admin.web.dao.FileDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FileController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());	
	@ManagedProperty(value = "#{fileBean}")
	private FileBean fileBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		fileBean.getDisabled().put("disabled-insert", "false");
		fileBean.getDisabled().put("disabled-update", "true");			
		fileBean.resetModel();	
		fileBean.setListAlbum(AlbumDAO.getAll()); 				
	}
	
	
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status = 0");
		String sqlOrder = " ORDER BY updated_time DESC";
		
		//album
		String filterAlbum = fileBean.getFilterAlbumId();
		if(!StringUtils.isBlank(filterAlbum)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "album_id", "%", DbProxy.antiInjection(filterAlbum), "%");
		}
		
		//title
		String filterTitle = fileBean.getFilterName();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "name_vn", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//Video
		String filterVideo = fileBean.getFilterVideo();
		if(!StringUtils.isBlank(filterVideo)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_video", DbProxy.antiInjection(filterVideo));
		}	
		
		//Video
		String filterShow = fileBean.getFilterShow();
		if(!StringUtils.isBlank(filterShow)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_show", DbProxy.antiInjection(filterShow));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = FileDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);			
		for(Map<String,String> map:lstMap){
			if(map.get("is_video").equals("0")){
				map.put("type", "Image");
			}else{
				map.put("type", "Video");
			}
			if(map.get("is_show").equals("0")){
				map.put("show", "Show");
			}else{
				map.put("show", "Hidden");
			}
			Map<String,String> album = getAlbumName(fileBean.getListAlbum(), map.get("album_id"));
			if(album!=null){
				map.put("album_name", album.get("title_vn"));
			}
		}
				
		fileBean.setList(lstMap);
		setRowOrder(fileBean.getList());
		
		fileBean.setCount(FileDAO.countList(sqlWhere));		
		fileBean.setPageBar(Paginator.getPageBar(page, fileBean.getCount(), ""));
	}
	
	private Map<String,String> getAlbumName(List<Map<String,String>> lstAlbum,String id){
		for(Map<String,String> map:lstAlbum){
			if(map.get("id").equals(id)){				
				return map;
			}
		}
		return null;
	}
	
	private void setRowOrder(List<Map<String, String>> lstMap){
		int i = 0;
		if(lstMap == null) return;
		for (Map<String, String> map : lstMap) {
			if(i%2 == 0) {
				map.put("rowOrder", "odd");
			} else {
				map.put("rowOrder", "");
			}
			i++;
		}		
	}		
	
	//#region "Function is used by VIEW layer"
	public void loadList(){
		Map<String, String> selectedPage = fileBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		fileBean.resetModel();	
		fileBean.getDisabled().put("disabled-insert", "true");
		fileBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = fileBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			fileBean.deserializeModel(selectedItem);	
			fileBean.getDisabled().put("disabled-insert", "true");
			fileBean.getDisabled().put("disabled-update", "false");
		}		
	}	
	public void update() {		
		fileBean.getDisabled().put("disabled-insert", "false");
		fileBean.getDisabled().put("disabled-update", "true");			
				
		// {{ query-database				
		if(fileBean.getSelectedItem() == null) {	
			if(!FileDAO.insert(fileBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			fileBean.setSelectedItem(null);
			if(!FileDAO.update(fileBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = fileBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				fileBean.setSelectedItem(null);
				if(!FileDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		fileBean.resetModel();
		fileBean.setSelectedItem(null);		
		fileBean.getDisabled().put("disabled-insert", "false");
		fileBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "file-keyword";
	}

	public String getDescription() {
		return "file-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public FileBean getFileBean() {
		return fileBean;
	}
	public void setFileBean(FileBean value) {
		fileBean = value;
	}
	//#endRegion
	
}