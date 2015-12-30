package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class MenuDAO {	
	public static List<Map<String,String>> getAll(){
		String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent ASC,ord ASC";
		return DBHelper.executeQuery(sqlCommand);
	}
	public static List<Map<String,String>> getMenu(String parent,String position){
		String sqlCommand = "SELECT * FROM menu WHERE parent = ? AND position = ? AND status = 0 ORDER BY ord ASC";
		return DBHelper.executeQuery(sqlCommand, parent,position);
	}
	public static boolean existSub(String parent,String position){
		String sqlCommand = "SELECT count(id) FROM menu WHERE parent = ? AND position = ? AND status = 0";
		int rs = DBHelper.getInt(sqlCommand, parent,position);
		return rs>0;
	}
}
