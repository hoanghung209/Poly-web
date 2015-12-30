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
import admin.web.bean.ContentBean;
import admin.web.common.Enum;
import admin.web.common.Enum.ContentType;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.ContentDAO;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContentListController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{contentBean}")
	private ContentBean contentBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		contentBean.getDisabled().put("disabled-insert", "false");
		contentBean.getDisabled().put("disabled-update", "true");			
		contentBean.resetModel();		
		String message = ContextUtils.getSession("contentMessage", "");
		if(!StringUtils.isBlank(message)){
			ContextUtils.addMessage(message);
			ContextUtils.removeSession("contentMessage"); 
		}
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND `status` = 0");
		String sqlOrder = " ORDER BY updated_time DESC";
		
		//menu_id
		String filterMenuId = contentBean.getFilterMenuId();
		if(!StringUtils.isBlank(filterMenuId)){
				sqlWhere = String.format("%s AND %s = %s", sqlWhere, "menu_id", DbProxy.antiInjection(filterMenuId));
				sqlOrder = " ORDER BY ord ASC";
		}
		
		//title
		String filterTitle = contentBean.getFilterTitle();
		if(!StringUtils.isBlank(filterTitle)){			
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "title", "%",DbProxy.antiInjection(filterTitle), "%");			
		}
		//content
		String filterContent = contentBean.getFilterContent();
		if(!StringUtils.isBlank(filterContent)){			
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "content", "%",DbProxy.antiInjection(filterTitle), "%");			
		}
		//type
		String filterType = contentBean.getFilterType();
		if(!StringUtils.isBlank(filterType)){			
			
			sqlWhere = String.format("%s AND %s = %s", sqlWhere, "type", DbProxy.antiInjection(filterType));			
		}
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = ContentDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String, String> map :lstMap){
			if(map.get("content_vn").length()>50){
				map.put("content_vn", map.get("content_vn").substring(0,50));
			}
			map.put("menu_name", MenuDAO.getMenuTree(map.get("menu_id"),"_vn"));
			map.put("type_name", Enum.getContentType(ContextUtils.getInt(map.get("type"), 0)));			
		}
						
		contentBean.setList(lstMap);
		setRowOrder(contentBean.getList());
		
		contentBean.setCount(ContentDAO.countList(sqlWhere));		
		contentBean.setPageBar(Paginator.getPageBar(page, contentBean.getCount(), ""));
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
		Map<String, String> selectedPage = contentBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		contentBean.resetModel();	
		contentBean.getDisabled().put("disabled-insert", "true");
		contentBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = contentBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			String url = RewriteURL.makeContentURL("tab-1", "11", selectedItem.get("id")); 
			ContextUtils.redirect(url); 
		}		
	}	
				
	
	public void delete(){
		Map<String, String> selectedItem = contentBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {	
			if(selectedItem.get("id").equals("-1")){
				ContextUtils.addMessage("Không được phép thay đổi root");	
				return;
			}
				contentBean.setSelectedItem(null);
				if(!ContentDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public ContentType[] getContentType(){
		return ContentType.values();
	}
	public void cancel() {		
		contentBean.resetModel();
		contentBean.setSelectedItem(null);		
		contentBean.getDisabled().put("disabled-insert", "false");
		contentBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "menu-keyword";
	}

	public String getDescription() {
		return "menu-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public ContentBean getcontentBean() {
		return contentBean;
	}
	public void setcontentBean(ContentBean value) {
		contentBean = value;
	}
	//#endRegion
}