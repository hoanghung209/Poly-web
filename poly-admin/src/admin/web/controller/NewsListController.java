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
import admin.web.bean.NewsBean;
import admin.web.common.Paginator;
import admin.web.common.RewriteURL;
import admin.web.dao.NewsDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NewsListController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{newsBean}")
	private NewsBean newsBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		newsBean.getDisabled().put("disabled-insert", "false");
		newsBean.getDisabled().put("disabled-update", "true");			
		newsBean.resetModel();		
		String message = ContextUtils.getSession("contentMessage", "");
		if(!StringUtils.isBlank(message)){
			ContextUtils.addMessage(message);
			ContextUtils.removeSession("contentMessage"); 
		}
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND `is_deleted` = 0");
		String sqlOrder = " ORDER BY updated_time DESC";
		
				
		//title
		String filterTitle = newsBean.getFilterTitle();
		if(!StringUtils.isBlank(filterTitle)){			
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "title_vn", "%",DbProxy.antiInjection(filterTitle), "%");			
		}
		
		//type
		String filterType = newsBean.getFilterShow();
		if(!StringUtils.isBlank(filterType)){			
			
			sqlWhere = String.format("%s AND %s = %s", sqlWhere, "is_show", DbProxy.antiInjection(filterType));			
		}
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = NewsDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String, String> map :lstMap){
			if(map.get("is_show").equals("0")){
				map.put("show", "Show");
			}else{
				map.put("show", "Hide");
			}
		}
						
		newsBean.setList(lstMap);
		setRowOrder(newsBean.getList());
		
		newsBean.setCount(NewsDAO.countList(sqlWhere));		
		newsBean.setPageBar(Paginator.getPageBar(page, newsBean.getCount(), ""));
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
		Map<String, String> selectedPage = newsBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		newsBean.resetModel();	
		newsBean.getDisabled().put("disabled-insert", "true");
		newsBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = newsBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			String url = RewriteURL.makeNewsURL("tab-1", "13", selectedItem.get("id")); 
			ContextUtils.redirect(url); 
		}		
	}	
				
	
	public void delete(){
		Map<String, String> selectedItem = newsBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {	
			if(selectedItem.get("id").equals("-1")){
				ContextUtils.addMessage("Không được phép thay đổi root");	
				return;
			}
				newsBean.setSelectedItem(null);
				if(!NewsDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		newsBean.resetModel();
		newsBean.setSelectedItem(null);		
		newsBean.getDisabled().put("disabled-insert", "false");
		newsBean.getDisabled().put("disabled-update", "true");		
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
}