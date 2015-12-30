package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class FaqDAO {	
	public static List<Map<String,String>> getList(String language){
		String sqlCommand = "SELECT * FROM faq WHERE status = 0 AND language = ? ORDER BY ord";
		return DBHelper.executeQuery(sqlCommand,language);
	}	
	
}
