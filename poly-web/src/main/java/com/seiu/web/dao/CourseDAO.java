package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class CourseDAO {	
	public static List<Map<String,String>> getList(String menuId){		
		String sqlCommand = "SELECT * FROM course WHERE menu_id =? AND `status` = 0 ORDER BY `ord` ASC";
		return DBHelper.executeQuery(sqlCommand,menuId);
	}	
}
