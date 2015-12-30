package com.seiu.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.ui.ModelMap;
import com.seiu.web.dao.MenuDAO;

public class MenuHelper {
	private static String lang= "vn";
	private static List<Map<String,String>> lstMenu ;
	public static List<Map<String,String>> getAll(){
		if(lstMenu == null){
			lstMenu = MenuDAO.getAll();
		}
		return lstMenu;
	}
	public static void clear(){
		lang = "vn";
		lstMenu = null;
	}
	public static List<Map<String,String>> getMenu(String parent,String position){		
		List<Map<String,String>> lstNewMenu = new ArrayList<Map<String,String>>();
		List<Map<String,String>> lstAllMenu = getAll();
		for(Map<String,String> map :lstAllMenu){
			if(map.get("parent").equals(parent)&&map.get("position").equals(position)){
				map.put("name", map.get("name_"+lang));	
				map.put("slug", map.get("slug_"+lang));
				if(ContextUtils.isBlank(map.get("url"))){
					map.put("urlnew", "intro");
				}else{
					map.put("urlnew", map.get("url")); 
				}
				lstNewMenu.add(map);
			}
		}
		return lstNewMenu;
	}
	public static boolean existSub(String parent,String position){
		List<Map<String,String>> lstAllMenu = getAll();
		for(Map<String,String> map :lstAllMenu){
			if(map.get("parent").equals(parent)&&map.get("position").equals(position)){
				return true;
			}
		}
		return false;
	}
	public static Map<String,String> getBySlug(String slug){
		List<Map<String,String>> lstMenu = getAll();
		for(Map<String,String> menu: lstMenu){
			if(slug.equals(menu.get("slug_"+getLang()))){
				return menu;
			}
		}
		return null;
	}
	
	public static List<Map<String,String>> getTopMenu(String actId){
		List<Map<String,String>> lstMap = new ArrayList<Map<String,String>>();
		Map<String,String> act = MenuHelper.getById(actId);
		while(true){	
			if(act.get("parent").equals("-1")){
				break;
			}
			Map<String,String> parent = MenuHelper.getById(act.get("parent"));
			if(parent!=null){
				lstMap.add(parent);
				act = parent;
			}else{
				break;
			}
		}
		return lstMap;
	}
	
	
	public static Map<String,String> getById (String menuId){
		List<Map<String,String>> lstMap = getAll();
		for(Map<String,String> map : lstMap){
			if(map.get("id").equals(menuId)){
				return map;
			}
		}
		return null;
	}
	
    public static String leftMenu(ModelMap model,String menuId,String activeId){
		
		Map<String,String> menuChinh = getById(menuId);	
		Map<String,String> menuLop = getById(activeId);	
		if(menuLop==null||menuChinh==null){
			return "404";
		}else{
			
			Map<String,String> menuCap = getById(menuLop.get("parent"));
			Map<String,String> menuChuongtrinh = getById(menuCap.get("parent"));			
			
			menuChinh.put("name",menuChinh.get("name_"+getLang()));
			menuChuongtrinh.put("name",menuChuongtrinh.get("name_"+getLang()));
			menuCap.put("name",menuCap.get("name_"+getLang()));
			menuLop.put("name",menuLop.get("name_"+getLang()));
			
			
			model.addAttribute("menuChinh", menuChinh);
			model.addAttribute("menuChuongtrinh", menuChuongtrinh);
			model.addAttribute("menuCap", menuCap);
			model.addAttribute("menuLop", menuLop);			
		}
		return "";
	}
    
    public static String getRootMenu(String id){
    	int max = 0;
    	return getParentMenu(id, max);
    }
    
    public static String getParentMenu(String id,int max){
    	Map<String,String> mapMenu = getById(id);
    	if(max>9){
    		return "0";
    	}
    	if(mapMenu!=null){
    		if(mapMenu.get("parent").equals("-1")){
    			return mapMenu.get("id");
    		}
    		return getParentMenu(mapMenu.get("parent"),++max);
    	}else{
    		return "0";
    	}
    }
	
	public static void refresh(){
		lstMenu = null;
	}
	public static String getLang() {
		return lang;
	}
	public static void setLang(String lang) {
		MenuHelper.lang = lang;
	}
}
