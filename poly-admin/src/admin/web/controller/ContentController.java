package admin.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.vega.security.AccessControl;

import vg.core.dbproxy.DbProxy;
import admin.web.bean.ContentBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.ContentDAO;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContentController implements Serializable {	
	private String reqContentId = ContextUtils.getRequest("id", "0");	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	private String label = "";
	@ManagedProperty(value = "#{contentBean}")
	private ContentBean contentBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){		
		loadList();
	}	
	
	
	public void loadList() {
		if(reqContentId.equals("0")) {			
			label = "Thêm nội dung";
		}else{
			label = "Sửa nội dung";
		}
		String sqlWhere = String.format(" AND `status` = 0");
		String sqlOrder = "";
		
		//menu_id		
		sqlWhere = String.format("%s AND %s = %s", sqlWhere, "id", DbProxy.antiInjection(reqContentId));
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = ContentDAO.getList(1, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		if(lstMap.size()>0){
			contentBean.deserializeModel(lstMap.get(0));
			contentBean.setMenuName(MenuDAO.getFieldById(lstMap.get(0).get("menu_id"), "name_vn"));  
		}		
	}
	
	public void update() {		
		contentBean.getDisabled().put("disabled-insert", "false");
		contentBean.getDisabled().put("disabled-update", "true");			
		contentBean.setUpdatedBy(userId);		
		// {{ query-database				
		if(reqContentId.equals("0")) {	
			if(!ContentDAO.insert(contentBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {		
				ContextUtils.setSession("contentMessage", "Thêm mới thành công");
				ContextUtils.redirect(RewriteURL.makeContentListURL("tab-1", "12")); 
			}				
		} else {		
			if(contentBean.getId().equals("-1")){
				ContextUtils.addMessage("Không được phép thay đổi root");	
				return;
			}
			if(!ContentDAO.update(contentBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {	
				ContextUtils.setSession("contentMessage", "Cập nhật thành công");
				ContextUtils.redirect(RewriteURL.makeContentListURL("tab-1", "12")); 
			}			
		}
		// }}
				
	}
	
	public void cancel() {		
		contentBean.resetModel();
		ContextUtils.redirect(RewriteURL.makeContentURL("tab-1", "10", reqContentId)); 	
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}