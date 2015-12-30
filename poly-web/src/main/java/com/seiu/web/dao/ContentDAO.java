package com.seiu.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class ContentDAO {	
	public static List<Map<String,String>> getContentByMenu(String menuId,String type){
		String sqlType = "AND type";
		if(!type.isEmpty()){
			if(type.contains(",")){
				sqlType += " IN("+type+")";
			}else{
				sqlType+= " = "+type;
			}
		}else{
			sqlType = "";
		}
		String sqlCommand = "SELECT * FROM content WHERE `menu_id` = ? AND `status` = 0 "+sqlType+" ORDER BY `ord` ASC";
		return DBHelper.executeQuery(sqlCommand,menuId);
	}	
	public static Map<String,String> countType(String menuId){		
		String sqlCommand = "SELECT `type`,count(id) sl FROM content WHERE `menu_id` = ? AND `status` = 0 GROUP BY `type`";
		List<Map<String,String>> lstRs = DBHelper.executeQuery(sqlCommand,menuId);
		if(lstRs.size()>0){
			Map<String,String> mapType = new HashMap<String, String>();
			for(Map<String,String> map:lstRs){
				if(map.get("type")!=null){
					mapType.put("type"+map.get("type"), map.get("sl"));
				}
			}
			return mapType;
		}
		return null;
	}	
}
