package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class SchedulerDAO {	
	public static List<Map<String,String>> getScheduler(){	
		String sqlCommand = "SELECT * FROM scheduler WHERE `status` = 0 ORDER BY `row`,`column` ASC";
		return DBHelper.executeQuery(sqlCommand);
	}	
	public static List<Map<String,String>> getHeader(){	
		String sqlCommand = "SELECT * FROM scheduler WHERE `status` = 3 ORDER BY `row`,`column` ASC";
		return DBHelper.executeQuery(sqlCommand);
	}	
}
