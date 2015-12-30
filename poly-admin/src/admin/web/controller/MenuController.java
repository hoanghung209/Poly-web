package admin.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
import com.vega.security.AccessControl;
import admin.web.bc.LanguageBC;
import admin.web.bean.MenuBean;
import admin.web.common.Enum;
import admin.web.common.Paginator;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MenuController implements Serializable {
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	private String reqParentId = ContextUtils.getRequest("parentId", "-10");	
	
	@ManagedProperty(value = "#{menuBean}")
	private MenuBean menuBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		menuBean.getDisabled().put("disabled-insert", "false");
		menuBean.getDisabled().put("disabled-update", "true");			
		menuBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status <> -1");
		String sqlOrder = " ORDER BY updated_time DESC";
		
		//parent_id
		if(Integer.valueOf(reqParentId) >=-1) {
			sqlWhere = String.format("%s AND %s = %s", sqlWhere, "parent", DbProxy.antiInjection(reqParentId));
		}
		
		//name
		String filterNameVn = menuBean.getFilterNameVn();
		if(!StringUtils.isBlank(filterNameVn)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "name_vn", "%", DbProxy.antiInjection(filterNameVn), "%");
		}
		String filterNameEn = menuBean.getFilterNameEn();
		if(!StringUtils.isBlank(filterNameEn)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "name_en", "%", DbProxy.antiInjection(filterNameEn), "%");
		}
		
		//position
		String filterPosition = menuBean.getFilterPosition();
		if(!StringUtils.isBlank(filterPosition)) {
			sqlWhere = String.format("%s AND %s = %s", sqlWhere, "position", DbProxy.antiInjection(filterPosition));
		}	
		//status
		String filterStatus = menuBean.getFilterStatus();
		if(!StringUtils.isBlank(filterStatus)) {
			sqlWhere = String.format("%s AND %s = %s", sqlWhere, "status", DbProxy.antiInjection(filterStatus));
		}
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = MenuDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		
		for (Map<String, String> map : lstMap) {
			map.put("status_name", Enum.getStatus(Integer.valueOf(map.get("status"))));	
			map.put("position_name", Enum.getPosition(Integer.valueOf(map.get("position"))));					
		}		
				
		menuBean.setList(lstMap);
		setRowOrder(menuBean.getList());
		
		menuBean.setCount(MenuDAO.countList(sqlWhere));		
		menuBean.setPageBar(Paginator.getPageBar(page, menuBean.getCount(), ""));
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
		Map<String, String> selectedPage = menuBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		menuBean.resetModel();	
		menuBean.getDisabled().put("disabled-insert", "true");
		menuBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = menuBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			menuBean.deserializeModel(selectedItem);	
			menuBean.getDisabled().put("disabled-insert", "true");
			menuBean.getDisabled().put("disabled-update", "false");	
			menuBean.setParentName(MenuDAO.getFieldById(menuBean.getParent(), "name_vn")); 
		}		
	}	
	public void update() {		
		menuBean.getDisabled().put("disabled-insert", "false");
		menuBean.getDisabled().put("disabled-update", "true");			
				
		// {{ query-database				
		if(menuBean.getSelectedItem() == null) {				
			menuBean.setCreatedBy(userId);			
			if(!MenuDAO.insert(menuBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			menuBean.setSelectedItem(null);		
			if(menuBean.getId().equals("-1")){
				ContextUtils.addMessage("Không được phép thay đổi root");	
				return;
			}
			if(!MenuDAO.update(menuBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = menuBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {	
			if(selectedItem.get("id").equals("-1")){
				ContextUtils.addMessage("Không được phép thay đổi root");	
				return;
			}
				menuBean.setSelectedItem(null);
				if(!MenuDAO.deleteLogic(selectedItem.get("id"), userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		menuBean.resetModel();
		menuBean.setSelectedItem(null);		
		menuBean.getDisabled().put("disabled-insert", "false");
		menuBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "menu-keyword";
	}

	public String getDescription() {
		return "menu-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public MenuBean getmenuBean() {
		return menuBean;
	}
	public void setmenuBean(MenuBean value) {
		menuBean = value;
	}
	//#endRegion
}