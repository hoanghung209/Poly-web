package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class NewsDAO {	
	public static List<Map<String,String>> getList(String limit){
		String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 ORDER BY updated_time LIMIT "+limit;
		return  DBHelper.executeQuery(sqlCommand);
		
	}
	
	public static List<Map<String,String>> getListRelated(String id,String limit){
		String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND id <> ? ORDER BY updated_time LIMIT "+limit;
		return  DBHelper.executeQuery(sqlCommand,id);
		
	}
	
	public static List<Map<String,String>> getById(String id){
		String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND id = ?";
		return  DBHelper.executeQuery(sqlCommand,id);
		
	}
	
}
