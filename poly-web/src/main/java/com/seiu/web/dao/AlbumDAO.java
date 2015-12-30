package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;
import com.seiu.web.utils.ContextUtils;

public class AlbumDAO {	
	public static List<Map<String,String>> getList(String is_video){
		
		String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0 AND is_video=? ORDER BY ord LIMIT 6";
		return DBHelper.executeQuery(sqlCommand,is_video);
	}
	public static Map<String,String> getAlbumById(String id){
		String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0 AND id =?";
		List<Map<String,String>> lstMap = DBHelper.executeQuery(sqlCommand,id);
		if(lstMap.size()>0){
			return lstMap.get(0);
		}
		return null;
	}
	public static List<Map<String,String>> getListNolimit(String is_video){
		String sqlVideo = "";
		if(!ContextUtils.isBlank(is_video)){
			sqlVideo = " AND is_video= "+is_video;
		}
		String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0"+sqlVideo+" ORDER BY ord";
		return DBHelper.executeQuery(sqlCommand);
	}
	public static List<Map<String,String>> getFile(String is_video){
		String sqlCommand = "SELECT * FROM file WHERE status = 0 AND is_show = 0 AND is_video= ? ORDER BY updated_time DESC";
		return DBHelper.executeQuery(sqlCommand,is_video);
	}	
	public static List<Map<String,String>> getFile(String is_video,String albumId){
		String sqlCommand = "SELECT * FROM file WHERE status = 0 AND is_show = 0 AND is_video= ? AND album_id=? ORDER BY updated_time DESC";
		return DBHelper.executeQuery(sqlCommand,is_video,albumId);
	}
	
}
