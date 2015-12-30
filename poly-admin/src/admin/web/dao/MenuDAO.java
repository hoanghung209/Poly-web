package admin.web.dao;
import java.util.Map;

import admin.web.common.HandleCache;
import admin.web.util.ContextUtils;

import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class MenuDAO {
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getAll(){
		String key = "MenuDAO.getAll";
		Object obj = HandleCache.get(key);
		if(obj!=null){
			return (List<Map<String,String>>) obj;
		}else{
			String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent,ord";
			String[] params = {};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
			HandleCache.put(key, lstRs);
			return lstRs;
		}
	}
	public static String getFieldById(String id,String field){
		List<Map<String,String>> lstMenu = getAll();
		for(Map<String,String> map:lstMenu ){
			if(map.get("id").equals(id)){
				return map.get(field);
			}
		}
		return "";
	}
	public static Map<String,String> getById(String id){
		List<Map<String,String>> lstMenu = getAll();
		for(Map<String,String> map:lstMenu ){
			if(map.get("id").equals(id)){
				return map;
			}
		}
		return null;
	}
	public static String getMenuTree(String id,String language){
		Map<String,String> menuCur = getById(id);
		String name = "";
		if(menuCur!=null){
			name = menuCur.get("name"+language);
			Map<String,String> menuParent = getById(menuCur.get("parent"));
			name = menuParent.get("name"+language) + " -> " + name;
		}
		return name;
	}
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM menu {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM menu {WHERE}";
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
			
		String[] sqlParameters = {};
		String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
		
		if(StringUtils.isBlank(count)) {
			return 0;
		} else {
			return Integer.valueOf(count); 
		}        	
	}
	public static boolean insert(Map<String,String> detail){
		HandleCache.removeContain("MenuDAO.");		
		String sqlCommand = "INSERT INTO menu(name_vn,slug_vn,name_en,slug_en,ord,parent,position,url,inpage,banner,status,created_by,created_time,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW())";
		String[] param = {detail.get("name_vn"),detail.get("slug_vn"),detail.get("name_en"),detail.get("slug_en"),detail.get("ord"),detail.get("parent"),detail.get("position"),detail.get("url"),detail.get("inpage"),detail.get("banner"),detail.get("status"),detail.get("created_by"),detail.get("updated_by")};
		boolean rs = DbProxy.executeUpdate(sqlCommand, param);
		ContextUtils.clearMenu();
		return rs;
	}
	public static boolean update(Map<String,String> detail){
		HandleCache.removeContain("MenuDAO.");
		String sqlCommand = "UPDATE menu SET name_vn=?,slug_vn=?,name_en=?,slug_en=?,ord=?,parent=?,position=?,url=?,inpage=?,banner=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("name_vn"),detail.get("slug_vn"),detail.get("name_en"),detail.get("slug_en"),detail.get("ord"),detail.get("parent"),detail.get("position"),detail.get("url"),detail.get("inpage"),detail.get("banner"),detail.get("status"),detail.get("updated_by"),detail.get("id")};
		boolean rs = DbProxy.executeUpdate(sqlCommand, param);
		ContextUtils.clearMenu();
		return rs;
	}
	
	public static boolean deleteLogic(String id,String userId){
		HandleCache.removeContain("MenuDAO.");
		String sqlCommand = "UPDATE menu SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param ={userId,id};
		boolean rs = DbProxy.executeUpdate(sqlCommand, param);
		ContextUtils.clearMenu();
		return rs;
	}
	
}
