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
import admin.web.bc.LanguageBC;
import admin.web.bean.RegisterBean;
import admin.web.common.AccessControl;
import admin.web.common.Paginator;
import admin.web.dao.RegisterDAO;
import admin.web.dao.UserDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RegisterController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{registerBean}")
	private RegisterBean registerBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		registerBean.getDisabled().put("disabled-insert", "false");
		registerBean.getDisabled().put("disabled-update", "true");			
		registerBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND is_deleted = 0");
		String sqlOrder = " ORDER BY status ASC,updated_time DESC";
		
			
		//title
		String filterTitle = registerBean.getFilterName();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "fullname", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//phone
		String filterPhone = registerBean.getFilterPhone();
		if(!StringUtils.isBlank(filterPhone)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "phone", "%", DbProxy.antiInjection(filterPhone), "%");
		}
		//email
		String filterEmail = registerBean.getFilterEmail();
		if(!StringUtils.isBlank(filterEmail)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "email", "%", DbProxy.antiInjection(filterEmail), "%");
		}
		
		//Status
		String filterStatus = registerBean.getFilterStatus();
		if(!StringUtils.isBlank(filterStatus)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "status", DbProxy.antiInjection(filterStatus));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		List<Map<String, String>> lstUser = UserDAO.getAll();
		List<Map<String, String>> lstMap = RegisterDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String,String> map:lstMap){
			if(map.get("status").equals("0")){
				map.put("status_name", "Chưa xử lý");
			}else if(map.get("status").equals("1")){
				map.put("status_name", "Đang xử lý");
			}else if(map.get("status").equals("2")){
				map.put("status_name", "Đã xử lý");
			}else{
				map.put("status_name", map.get("status"));
			}
			map.put("username", getUserName(lstUser, map.get("updated_by"))); 
		}
				
		registerBean.setList(lstMap);
		setRowOrder(registerBean.getList());
		
		registerBean.setCount(RegisterDAO.countList(sqlWhere));		
		registerBean.setPageBar(Paginator.getPageBar(page, registerBean.getCount(), ""));
	}
	
	private String getUserName(List<Map<String,String>> lstUser,String id){
		if(StringUtils.isBlank(id)){
			return "";
		}
		for(Map<String,String> map:lstUser){
			if(map.get("id").equals(id)){
				return map.get("user");
			}
		}
		return id;
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
		Map<String, String> selectedPage = registerBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	
	public void start() {
		Map<String, String> selectedItem = registerBean.getSelectedItem();		
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			if(selectedItem.get("status").equals("2")){
				ContextUtils.addMessage("Đăng ký này đã được xử lý");
			}else{
				registerBean.setSelectedItem(null);
				if(!RegisterDAO.action(selectedItem.get("id"), "1", userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Bắt đầu xử lý đăng ký test");
				};	
			}
		}		
	}	
	public void edit() {
		Map<String, String> selectedItem = registerBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			registerBean.setSelectedItem(null);			
			if(!RegisterDAO.action(selectedItem.get("id"), "2", userId)) {
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
			} else {
				
				bind();
				ContextUtils.addMessage("Đã xử lý đăng ký test");
			};	
				
		}		
	}	
	
	public void delete(){
		Map<String, String> selectedItem = registerBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				registerBean.setSelectedItem(null);
				if(!RegisterDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		registerBean.resetModel();
		registerBean.setSelectedItem(null);		
		registerBean.getDisabled().put("disabled-insert", "false");
		registerBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "register-keyword";
	}

	public String getDescription() {
		return "register-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public RegisterBean getRegisterBean() {
		return registerBean;
	}
	public void setRegisterBean(RegisterBean value) {
		registerBean = value;
	}
	//#endRegion
}