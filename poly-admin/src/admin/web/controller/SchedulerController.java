package admin.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.vega.security.AccessControl;
import vg.core.common.StringUtils;
import admin.web.bc.LanguageBC;
import admin.web.bean.SchedulerBean;
import admin.web.common.Enum.ContentType;
import admin.web.dao.CourseDAO;
import admin.web.dao.SchedulerDAO;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SchedulerController implements Serializable {	
	private String userId = AccessControl.getUserid(ContextUtils.getRequest());
	@ManagedProperty(value = "#{schedulerBean}")
	private SchedulerBean schedulerBean;
	
	@PostConstruct
	protected void init(){					
		bind();	
	}			
	
	protected void bind(){
		loadDetail();
		loadCourse();
		loadList();
	}	
	
	private void loadDetail() {
		schedulerBean.getDisabled().put("disabled-insert", "false");
		schedulerBean.getDisabled().put("disabled-update", "true");			
		schedulerBean.resetModel();	
	}
	
	private void loadCourse(){
		List<Map<String,String>> lstCourse = CourseDAO.getAll();
		schedulerBean.setListCourse(lstCourse);
	}
	
	public void loadList() {
		String chuongtrinhId = schedulerBean.getFilterChuongtrinhId();
		System.out.println(chuongtrinhId); 
		if(StringUtils.isBlank(chuongtrinhId)){
			chuongtrinhId = "1";
		}
		String language = schedulerBean.getFilterLanguage();
		if(StringUtils.isBlank(language)){
			language = "vn";
		}
		
		//get column Header
		List<Map<String,String>> lstHeader = SchedulerDAO.getHeader();
		for(Map<String,String> map:lstHeader){
			map.put("content", map.get("content_" + language));
			map.put("col_name", "col"+map.get("column"));
		}
		schedulerBean.setListHeader(lstHeader); 
		
		//get content scheduler			
		List<Map<String,String>> lstScheduler = SchedulerDAO.getByChuongtrinh(chuongtrinhId);		
		
		//sync new list scheduler		
		List<Map<String,String>> lstNewScheduler = new ArrayList<Map<String,String>>();
		Map<String,String> mapRow = new HashMap<String, String>();
		for(Map<String,String> map:lstScheduler){			
			if(map.get("column").equals("1")){
				mapRow = new HashMap<String, String>(); 
				mapRow.put("row", map.get("row"));
				mapRow.put("chuongtrinh_id", chuongtrinhId);
				mapRow.put("col1", map.get("content_"+language));
			}else if(map.get("column").equals(String.valueOf(lstHeader.size()))){
				mapRow.put("col"+map.get("column"), map.get("content_"+language));
				lstNewScheduler.add(mapRow);
			}else{
				if(Integer.valueOf(map.get("column"))<lstHeader.size()){
					mapRow.put("col"+map.get("column"), map.get("content_"+language));
				}
			}
			
		}
		schedulerBean.setList(lstNewScheduler);
		
	}
	
	
	//#region "Function is used by VIEW layer"	
	public void add() {
		schedulerBean.resetModel();	
		schedulerBean.getDisabled().put("disabled-insert", "true");
		schedulerBean.getDisabled().put("disabled-update", "false");	
		
		String chuongtrinhId = schedulerBean.getFilterChuongtrinhId();
		String maxRow = SchedulerDAO.getMaxRow(chuongtrinhId);
		int nextRow = 1;
		if(maxRow!=null){
			nextRow = Integer.valueOf(maxRow)+1;
		}
		List<Map<String,String>> lstAdd = new ArrayList<Map<String,String>>();
		for(int i=1;i<=schedulerBean.getListHeader().size();i++){
			Map<String,String> mapAdd = new HashMap<String, String>();
			Map<String,String> mapHeader = getHeaderName(schedulerBean.getListHeader(),String.valueOf(i));
			mapAdd.put("header_vn", mapHeader.get("content_vn"));
			mapAdd.put("header_en", mapHeader.get("content_en"));
			mapAdd.put("row", String.valueOf(nextRow));
			mapAdd.put("column", String.valueOf(i));
			mapAdd.put("updated_by", userId);
			mapAdd.put("status", "0");
			mapAdd.put("chuongtrinh_id", chuongtrinhId);
			
			lstAdd.add(mapAdd);
		}
		schedulerBean.setListEdit(lstAdd); 
		schedulerBean.setActionRow("0"); 
	}
	public void edit() {
		Map<String, String> selectedItem = schedulerBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage(LanguageBC.getValue("message.RequiredUpdateSelection"));
		} else {
			schedulerBean.getDisabled().put("disabled-insert", "true");
			schedulerBean.getDisabled().put("disabled-update", "false");	
			schedulerBean.setSelectedItem(null); 
			String chuongtrinhId = selectedItem.get("chuongtrinh_id");
			String row = selectedItem.get("row");
			List<Map<String,String>> lstItem = SchedulerDAO.getByChuongtrinh(chuongtrinhId,row);
			List<Map<String,String>> lstHeader = SchedulerDAO.getHeader();
			for(Map<String,String> map:lstItem){
				Map<String,String> mapHeader = getHeaderName(lstHeader, map.get("column"));
				map.put("header_vn", mapHeader.get("content_vn"));
				map.put("header_en", mapHeader.get("content_en"));
			}
			schedulerBean.setListEdit(lstItem);
			schedulerBean.setActionRow("1"); 
			
		}		
	}
	
	
	public void editHeader() {
		
			schedulerBean.getDisabled().put("disabled-insert", "true");
			schedulerBean.getDisabled().put("disabled-update", "false");	
									
			List<Map<String,String>> lstItem = SchedulerDAO.getHeader();			
			schedulerBean.setListEdit(lstItem);
			schedulerBean.setActionRow("2");			
			
	}
	public Map<String,String> getHeaderName(List<Map<String,String>> lstHeader,String column){
		for(Map<String,String> map : lstHeader){
			if(column.equals(map.get("column"))){
				return map;
			}
		}
		return null;
	}
	public void update() {
		if(schedulerBean.getActionRow().equals("1")){
			for(Map<String,String> edit : schedulerBean.getListEdit()){
				edit.put("updated_by", userId);
				SchedulerDAO.update(edit);
			}
			bind();
			ContextUtils.addMessage("Cập nhật thành công");
		}else if(schedulerBean.getActionRow().equals("0")){
			for(Map<String,String> edit : schedulerBean.getListEdit()){				
				SchedulerDAO.insert(edit);
			}
			bind();
			ContextUtils.addMessage("Thêm mới thành công");
		}else if(schedulerBean.getActionRow().equals("2")){
			for(Map<String,String> edit : schedulerBean.getListEdit()){
				edit.put("updated_by", userId);
				SchedulerDAO.update(edit);
			}
			bind();
			ContextUtils.addMessage("Cập nhật thành công");
		}else{
			ContextUtils.addMessage("Hệ thống đang bận. Vui lòng thử lại sau");
		}
	}
	
	public void delete(){
		Map<String, String> selectedItem = schedulerBean.getSelectedItem();
		if(selectedItem == null) {
			ContextUtils.addMessage("Bạn chưa chọn mục để xóa");
		} else {
				schedulerBean.setSelectedItem(null);
				if(!SchedulerDAO.deleteLogic(selectedItem.get("chuongtrinh_id"),selectedItem.get("row"),userId)) {
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
		schedulerBean.resetModel();
		schedulerBean.setSelectedItem(null);		
		schedulerBean.getDisabled().put("disabled-insert", "false");
		schedulerBean.getDisabled().put("disabled-update", "true");		
	}	
	public String getKeywords() {
		return "menu-keyword";
	}

	public String getDescription() {
		return "menu-description";
	}		
	//#endRegion
	
	//#region "Getter & Setter"	
	public SchedulerBean getSchedulerBean() {
		return schedulerBean;
	}
	public void setSchedulerBean(SchedulerBean value) {
		schedulerBean = value;
	}
	//#endRegion
}