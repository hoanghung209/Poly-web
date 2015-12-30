package admin.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import admin.web.bean.AdsBean;
import admin.web.common.Paginator;
import admin.web.dao.AdsBannerDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AdsController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	@ManagedProperty(value = "#{adsBean}")
	private AdsBean adsBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		adsBean.getDisabled().put("disabled-insert", "false");
		adsBean.getDisabled().put("disabled-update", "true");			
		adsBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND is_deleted = 0");
		String sqlOrder = " ORDER BY updated_time DESC";
		
			
		//title
		String filterTitle = adsBean.getFilterName();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "name", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//Video
		String filterVideo = adsBean.getFilterType();
		if(!StringUtils.isBlank(filterVideo)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "type", DbProxy.antiInjection(filterVideo));
		}	
		
		//Video
		String filterShow = adsBean.getFilterShow();
		if(!StringUtils.isBlank(filterShow)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "is_show", DbProxy.antiInjection(filterShow));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = AdsBannerDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
		for(Map<String,String> map:lstMap){			
			if(map.get("is_show").equals("0")){
				map.put("show", "Show");
			}else{
				map.put("show", "Hidden");
			}
			if(map.get("type").equals("0")){
				map.put("type_name", "Banner");
			}else{
				map.put("type_name", "Popup");
			}
			if(map.get("is_once").equals("0")){
				map.put("once", "Once");
			}else{
				map.put("once", "Many");
			}
			try{
				map.put("start", sdf2.format(sdf.parse(map.get("date_start"))));
				map.put("end", sdf2.format(sdf.parse(map.get("date_end"))));
				}catch(Exception ex){
					
				}
		}
				
		adsBean.setList(lstMap);
		setRowOrder(adsBean.getList());
		
		adsBean.setCount(AdsBannerDAO.countList(sqlWhere));		
		adsBean.setPageBar(Paginator.getPageBar(page, adsBean.getCount(), ""));
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
		Map<String, String> selectedPage = adsBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		adsBean.resetModel();	
		adsBean.getDisabled().put("disabled-insert", "true");
		adsBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = adsBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			
			adsBean.deserializeModel(selectedItem);	
			adsBean.getDisabled().put("disabled-insert", "true");
			adsBean.getDisabled().put("disabled-update", "false");	
			try {
				adsBean.setDate_start(sdf2.format(sdf.parse(adsBean.getDate_start())));
				adsBean.setDate_end(sdf2.format(sdf.parse(adsBean.getDate_end())));
			} catch (Exception e) {	
			}
			
		}		
	}	
	public void update() {		
		adsBean.getDisabled().put("disabled-insert", "false");
		adsBean.getDisabled().put("disabled-update", "true");	
		
		String date_start = adsBean.getDate_start();
		try{
			Date start = sdf2.parse(date_start);
			date_start = sdf.format(start);
		}catch(Exception ex){
			date_start = sdf.format(new Date());
		}
		String date_end = adsBean.getDate_end();
		try{
			Date end = sdf2.parse(date_end);
			date_end = sdf.format(end);
		}catch(Exception ex){
			date_end = sdf.format(new Date());
		}
		
		adsBean.setDate_start(date_start);
		adsBean.setDate_end(date_end); 
		adsBean.setUpdated_by(userId);
				
		// {{ query-database				
		if(adsBean.getSelectedItem() == null) {	
			if(!AdsBannerDAO.insert(adsBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			adsBean.setSelectedItem(null);
			if(!AdsBannerDAO.update(adsBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = adsBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				adsBean.setSelectedItem(null);
				if(!AdsBannerDAO.deleteLogic(selectedItem.get("id"),userId)) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		adsBean.resetModel();
		adsBean.setSelectedItem(null);		
		adsBean.getDisabled().put("disabled-insert", "false");
		adsBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "ads-keyword";
	}

	public String getDescription() {
		return "ads-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public AdsBean getAdsBean() {
		return adsBean;
	}
	public void setAdsBean(AdsBean value) {
		adsBean = value;
	}
	//#endRegion
}