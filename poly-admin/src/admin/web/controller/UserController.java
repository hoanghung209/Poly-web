package admin.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.vega.security.AccessControl;
import admin.web.bc.LanguageBC;
import admin.web.bean.UserBean;
import admin.web.common.Paginator;
import admin.web.dao.UserDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UserController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		userBean.getDisabled().put("disabled-insert", "false");
		userBean.getDisabled().put("disabled-update", "true");			
		userBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status = 0");
		String sqlOrder = " ORDER BY id ASC";
		
			
				
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = UserDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		
		userBean.setList(lstMap);
		setRowOrder(userBean.getList());
		
		userBean.setCount(UserDAO.countList(sqlWhere));		
		userBean.setPageBar(Paginator.getPageBar(page, userBean.getCount(), ""));
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
		Map<String, String> selectedPage = userBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		userBean.resetModel();	
		userBean.getDisabled().put("disabled-insert", "true");
		userBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = userBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			userBean.deserializeModel(selectedItem);	
			userBean.getDisabled().put("disabled-insert", "true");
			userBean.getDisabled().put("disabled-update", "false");				
		}		
	}	
	public void update() {		
		userBean.getDisabled().put("disabled-insert", "false");
		userBean.getDisabled().put("disabled-update", "true");			
				
		// {{ query-database				
		if(userBean.getSelectedItem() == null) {	
			if(UserDAO.check(userBean.getUser())){
				ContextUtils.addMessage("User đã tồn tại");
			}else{
				if(!UserDAO.insert(userBean.serializeModel())) {				
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
				} else {				
					bind();
					ContextUtils.addMessage("Thêm mới thành công");
				}				
			}
		} else {
			userBean.setSelectedItem(null);
			if(!UserDAO.update(userBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = userBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				userBean.setSelectedItem(null);
				if(!UserDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		userBean.resetModel();
		userBean.setSelectedItem(null);		
		userBean.getDisabled().put("disabled-insert", "false");
		userBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "user-keyword";
	}

	public String getDescription() {
		return "user-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean value) {
		userBean = value;
	}
	//#endRegion
}