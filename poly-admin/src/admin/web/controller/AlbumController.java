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
import admin.web.bean.AlbumBean;
import admin.web.common.Paginator;
import admin.web.dao.AlbumDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AlbumController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{albumBean}")
	private AlbumBean albumBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		albumBean.getDisabled().put("disabled-insert", "false");
		albumBean.getDisabled().put("disabled-update", "true");			
		albumBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status = 0");
		String sqlOrder = " ORDER BY updated_time DESC";
		
			
		//title
		String filterTitle = albumBean.getFilterTitle();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "title_vn", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//Video
		String filterVideo = albumBean.getFilterVideo();
		if(!StringUtils.isBlank(filterVideo)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_video", DbProxy.antiInjection(filterVideo));
		}	
		
		//Video
		String filterShow = albumBean.getFilterShow();
		if(!StringUtils.isBlank(filterShow)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_show", DbProxy.antiInjection(filterShow));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = AlbumDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
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
		}
				
		albumBean.setList(lstMap);
		setRowOrder(albumBean.getList());
		
		albumBean.setCount(AlbumDAO.countList(sqlWhere));		
		albumBean.setPageBar(Paginator.getPageBar(page, albumBean.getCount(), ""));
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
		Map<String, String> selectedPage = albumBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		albumBean.resetModel();	
		albumBean.getDisabled().put("disabled-insert", "true");
		albumBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = albumBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			albumBean.deserializeModel(selectedItem);	
			albumBean.getDisabled().put("disabled-insert", "true");
			albumBean.getDisabled().put("disabled-update", "false");				
		}		
	}	
	public void update() {		
		albumBean.getDisabled().put("disabled-insert", "false");
		albumBean.getDisabled().put("disabled-update", "true");			
				
		// {{ query-database				
		if(albumBean.getSelectedItem() == null) {	
			if(!AlbumDAO.insert(albumBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			albumBean.setSelectedItem(null);
			if(!AlbumDAO.update(albumBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = albumBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				albumBean.setSelectedItem(null);
				if(!AlbumDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		albumBean.resetModel();
		albumBean.setSelectedItem(null);		
		albumBean.getDisabled().put("disabled-insert", "false");
		albumBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "album-keyword";
	}

	public String getDescription() {
		return "album-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public AlbumBean getAlbumBean() {
		return albumBean;
	}
	public void setAlbumBean(AlbumBean value) {
		albumBean = value;
	}
	//#endRegion
}