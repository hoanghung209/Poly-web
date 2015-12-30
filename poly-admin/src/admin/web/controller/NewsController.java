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
import admin.web.bean.NewsBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.NewsDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NewsController implements Serializable {	
	private String reqContentId = ContextUtils.getRequest("id", "0");	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	private String label = "";
	@ManagedProperty(value = "#{newsBean}")
	private NewsBean newsBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){		
		loadList();
	}	
	
	
	public void loadList() {
		if(reqContentId.equals("0")) {			
			label = "Thêm tin ";
		}else{
			label = "Sửa tin";
		}
		String sqlWhere = String.format(" AND `is_deleted` = 0");
		String sqlOrder = "";
		
		//menu_id		
		sqlWhere = String.format("%s AND %s = %s", sqlWhere, "id", DbProxy.antiInjection(reqContentId));
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = NewsDAO.getList(1, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		if(lstMap.size()>0){
			newsBean.deserializeModel(lstMap.get(0));		 
		}		
	}
	
	public void update() {		
		newsBean.getDisabled().put("disabled-insert", "false");
		newsBean.getDisabled().put("disabled-update", "true");			
		newsBean.setUpdatedBy(userId);		
		// {{ query-database				
		if(reqContentId.equals("0")) {	
			if(!NewsDAO.insert(newsBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {		
				ContextUtils.setSession("contentMessage", "Thêm mới thành công");
				ContextUtils.redirect(RewriteURL.makeNewsListURL("tab-1", "14")); 
			}				
		} else {	
			if(!NewsDAO.update(newsBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {	
				ContextUtils.setSession("contentMessage", "Cập nhật thành công");
				ContextUtils.redirect(RewriteURL.makeNewsListURL("tab-1", "14")); 
			}			
		}
		// }}
				
	}
	
	public void cancel() {		
		newsBean.resetModel();
		ContextUtils.redirect(RewriteURL.makeNewsURL("tab-1", "13", reqContentId)); 	
	}	
	public String getKeywords() {
		return "news-keyword";
	}

	public String getDescription() {
		return "news-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public NewsBean getNewsBean() {
		return newsBean;
	}
	public void setNewsBean(NewsBean value) {
		newsBean = value;
	}
	//#endRegion

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}