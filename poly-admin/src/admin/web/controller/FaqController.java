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
import admin.web.bean.FaqBean;
import admin.web.common.Paginator;
import admin.web.dao.FaqDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FaqController implements Serializable {	
	@ManagedProperty(value = "#{faqBean}")
	private FaqBean faqBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadList(1);
	}	
	
	private void loadDetail() {
		faqBean.getDisabled().put("disabled-insert", "false");
		faqBean.getDisabled().put("disabled-update", "true");			
		faqBean.resetModel();		
	}
	
	public void loadList(int page) {
		String sqlWhere = String.format(" AND status = 0");
		String sqlOrder = " ORDER BY id DESC";
		
			
		//title
		String filterTitle = faqBean.getFilterTitle();
		if(!StringUtils.isBlank(filterTitle)) {
			sqlWhere = String.format("%s AND %s LIKE '%s%s%s'", sqlWhere, "title", "%", DbProxy.antiInjection(filterTitle), "%");
		}
		
		//Language
		String filterLang = faqBean.getFilterLang();
		if(!StringUtils.isBlank(filterLang)) {
			sqlWhere = String.format("%s AND %s = '%s'", sqlWhere, "language", DbProxy.antiInjection(filterLang));
		}	
		
		//standardized-sqlWhere
		if(sqlWhere.length() > 0) {
			sqlWhere = String.format(" WHERE %s", sqlWhere.substring(5)); //Remove First ' AND '
		}				
		
		List<Map<String, String>> lstMap = FaqDAO.getList(page, Paginator.DEFAULT_PAGE_SIZE, sqlWhere, sqlOrder);	
			
				
		faqBean.setList(lstMap);
		setRowOrder(faqBean.getList());
		
		faqBean.setCount(FaqDAO.countList(sqlWhere));		
		faqBean.setPageBar(Paginator.getPageBar(page, faqBean.getCount(), ""));
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
		Map<String, String> selectedPage = faqBean.getSelectedPage();
		if(selectedPage != null) {
			int page = Integer.valueOf(selectedPage.get("page"));
			loadList(page);
		}
	}	
	public void add() {
		faqBean.resetModel();	
		faqBean.getDisabled().put("disabled-insert", "true");
		faqBean.getDisabled().put("disabled-update", "false");	
	}
	public void edit() {
		Map<String, String> selectedItem = faqBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			faqBean.deserializeModel(selectedItem);	
			faqBean.getDisabled().put("disabled-insert", "true");
			faqBean.getDisabled().put("disabled-update", "false");				
		}		
	}	
	public void update() {		
		faqBean.getDisabled().put("disabled-insert", "false");
		faqBean.getDisabled().put("disabled-update", "true");			
				
		// {{ query-database				
		if(faqBean.getSelectedItem() == null) {	
			if(!FaqDAO.insert(faqBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
			} else {				
				bind();
				ContextUtils.addMessage("Thêm mới thành công");
			}				
		} else {
			faqBean.setSelectedItem(null);
			if(!FaqDAO.update(faqBean.serializeModel())) {				
				ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");				
			} else {				
				bind();
				ContextUtils.addMessage("Cập nhật thành công");
			}			
		}
		// }}
				
	}
	public void delete(){
		Map<String, String> selectedItem = faqBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {				
				faqBean.setSelectedItem(null);
				if(!FaqDAO.deleteLogic(selectedItem.get("id"))) {
					ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");					
				} else {
					
					bind();
					ContextUtils.addMessage("Xóa thành công");
				};						
		}	
	}	
	public void cancel() {		
		faqBean.resetModel();
		faqBean.setSelectedItem(null);		
		faqBean.getDisabled().put("disabled-insert", "false");
		faqBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "menu-keyword";
	}

	public String getDescription() {
		return "menu-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public FaqBean getfaqBean() {
		return faqBean;
	}
	public void setfaqBean(FaqBean value) {
		faqBean = value;
	}
	//#endRegion
}