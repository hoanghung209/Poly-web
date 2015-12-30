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
import admin.web.bean.FeedbackBean;
import admin.web.common.AccessControl;
import admin.web.common.Paginator;
import admin.web.dao.FeedbackDAO;
import admin.web.dao.UserDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FeedBackController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{feedbackBean}")
	private FeedbackBean feedbackBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		feedbackBean.getDisabled().put("disabled-insert", "false");
		feedbackBean.getDisabled().put("disabled-update", "true");			
		feedbackBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status <> -1");
		String sqlOrder = " ORDER BY status,updated_time DESC";
		
			
		//title
		String filterTitle = feedbackBean.getFilterName();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "name", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//Video
		String filterPhone = feedbackBean.getFilterPhone();
		if(!StringUtils.isBlank(filterPhone)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "phone", "%", DbProxy.antiInjection(filterPhone), "%");
		}	
		
		//Status
		String filterStatus = feedbackBean.getFilterStatus();
		if(!StringUtils.isBlank(filterStatus)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "status", DbProxy.antiInjection(filterStatus));
		}	
		//Status
		String filterShow = feedbackBean.getFilterShow();
		if(!StringUtils.isBlank(filterShow)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_show", DbProxy.antiInjection(filterShow));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		List<Map<String, String>> lstUser = UserDAO.getAll();
		List<Map<String, String>> lstMap = FeedbackDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String,String> map:lstMap){			
			if(map.get("is_show").equals("0")){
				map.put("show", "Show");
			}else{
				map.put("show", "Hidden");
			}
			if(map.get("status").equals("0")){
				map.put("status_name", "Chưa xử lý");
			}else if(map.get("status").equals("1")){
				map.put("status_name", "Đã xử lý");
			} else if(map.get("status").equals("2")){
				map.put("status_name", "Đánh dấu");
			}
			map.put("user", getUserName(lstUser, map.get("updated_by")));
		}
		System.out.println(sqlWhere+"|"+lstMap.size());
		feedbackBean.setList(lstMap);
		setRowOrder(feedbackBean.getList());
		
		feedbackBean.setCount(FeedbackDAO.countList(sqlWhere));		
		feedbackBean.setPageBar(Paginator.getPageBar(page, feedbackBean.getCount(), ""));
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
		Map<String, String> selectedPage = feedbackBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		feedbackBean.resetModel();	
		feedbackBean.getDisabled().put("disabled-insert", "true");
		feedbackBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = feedbackBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			feedbackBean.deserializeModel(selectedItem);	
			feedbackBean.getDisabled().put("disabled-insert", "true");
			feedbackBean.getDisabled().put("disabled-update", "false");				
		}		
	}	
	public void update() {		
		feedbackBean.getDisabled().put("disabled-insert", "false");
		feedbackBean.getDisabled().put("disabled-update", "true");			
		feedbackBean.setUpdatedBy(userId);		
		// {{ query-database				
		if(feedbackBean.getSelectedItem() == null) {	
			if(!FeedbackDAO.insert(feedbackBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			feedbackBean.setSelectedItem(null);
			if(!FeedbackDAO.update(feedbackBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = feedbackBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				feedbackBean.setSelectedItem(null);
				if(!FeedbackDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		feedbackBean.resetModel();
		feedbackBean.setSelectedItem(null);		
		feedbackBean.getDisabled().put("disabled-insert", "false");
		feedbackBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "album-keyword";
	}

	public String getDescription() {
		return "album-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public FeedbackBean getFeedbackBean() {
		return feedbackBean;
	}
	public void setFeedbackBean(FeedbackBean value) {
		feedbackBean = value;
	}
	//#endRegion
}