package admin.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import admin.web.bc.LanguageBC;
import admin.web.bean.CourseBean;import admin.web.common.AccessControl;
import admin.web.common.Paginator;
import admin.web.dao.CourseDAO;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CourseController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	
	@ManagedProperty(value = "#{courseBean}")
	private CourseBean courseBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		courseBean.getDisabled().put("disabled-insert", "false");
		courseBean.getDisabled().put("disabled-update", "true");
		courseBean.getDisabled().put("disabled-show", "false");
		courseBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status = 0");
		String sqlOrder = " ORDER BY id DESC";
					
		
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = CourseDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String, String> map :lstMap){			
			map.put("menu_name", MenuDAO.getMenuTree(map.get("menu_id"),"_vn"));			
		}	
				
		courseBean.setList(lstMap);
		setRowOrder(courseBean.getList());
		
		courseBean.setCount(CourseDAO.countList(sqlWhere));		
		courseBean.setPageBar(Paginator.getPageBar(page, courseBean.getCount(), ""));
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
		Map<String, String> selectedPage = courseBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		courseBean.resetModel();	
		courseBean.getDisabled().put("disabled-insert", "true");
		courseBean.getDisabled().put("disabled-update", "false");
		courseBean.getDisabled().put("disabled-show", "true");
	}
	public void edit() {
		Map<String, String> selectedItem = courseBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			courseBean.deserializeModel(selectedItem);	
			courseBean.setMenuName(MenuDAO.getFieldById(selectedItem.get("menu_id"), "name_vn"));  
			courseBean.getDisabled().put("disabled-insert", "true");
			courseBean.getDisabled().put("disabled-update", "false");
			courseBean.getDisabled().put("disabled-show", "true");
		}		
	}	
	public void update() {		
		courseBean.getDisabled().put("disabled-insert", "false");
		courseBean.getDisabled().put("disabled-update", "true");			
		courseBean.setUpdatedBy(userId);	 	
		// {{ query-database				
		if(courseBean.getSelectedItem() == null) {	
			if(!CourseDAO.insert(courseBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			courseBean.setSelectedItem(null);
			if(!CourseDAO.update(courseBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = courseBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				courseBean.setSelectedItem(null);
				if(!CourseDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		courseBean.resetModel();
		courseBean.setSelectedItem(null);		
		courseBean.getDisabled().put("disabled-insert", "false");
		courseBean.getDisabled().put("disabled-update", "true");	
		courseBean.getDisabled().put("disabled-show", "false");
	}	
	public String getKeywords() {
		return "menu-keyword";
	}

	public String getDescription() {
		return "menu-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public CourseBean getCourseBean() {
		return courseBean;
	}
	public void setCourseBean(CourseBean value) {
		courseBean = value;
	}
	//#endRegion
}