package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class SchoolSubjectDAO {	
	public static List<Map<String,String>> getSubjectByMenu(String menuId){
		String sqlCommand = "SELECT * FROM school_subject WHERE `menu_id` = ? AND `status` = 0 ORDER BY `ord` ASC";
		return DBHelper.executeQuery(sqlCommand,menuId);
	}	
}
