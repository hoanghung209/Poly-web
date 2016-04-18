package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;
import com.seiu.web.utils.ContextUtils;

public class AlbumDAO
{
  public static List<Map<String, String>> getList(String type)
  {
    String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0 AND type=? ORDER BY ord LIMIT 6";
    return DBHelper.executeQuery(sqlCommand, new Object[] { type });
  }
  
  public static Map<String, String> getFirst(String type)
  {
    String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0 AND type=? ORDER BY ord LIMIT 1";
    List<Map<String, String>> lst = DBHelper.executeQuery(sqlCommand, new Object[] { type });
    if (lst.size() > 0) {
      return lst.get(0);
    }
    return null;
  }
  
  public static Map<String, String> getAlbumById(String id)
  {
    String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0 AND id =?";
    List<Map<String, String>> lstMap = DBHelper.executeQuery(sqlCommand, new Object[] { id });
    if (lstMap.size() > 0) {
      return lstMap.get(0);
    }
    return null;
  }
  
  public static List<Map<String, String>> getListNolimit(String type)
  {
    String sqlVideo = "";
    if (!ContextUtils.isBlank(type)) {
      sqlVideo = " AND type= " + type;
    }
    String sqlCommand = "SELECT * FROM album WHERE status = 0 AND is_show = 0" + sqlVideo + " ORDER BY ord";
    return DBHelper.executeQuery(sqlCommand, new Object[0]);
  }
  
  public static List<Map<String, String>> getFile(String type)
  {
    String sqlCommand = "SELECT * FROM file WHERE status = 0 AND is_show = 0 AND is_video= ? ORDER BY ord";
    return DBHelper.executeQuery(sqlCommand, new Object[] { type });
  }
  
  public static List<Map<String, String>> getFile(String type, String albumId)
  {
    String sqlCommand = "SELECT * FROM file WHERE status = 0 AND is_show = 0 AND is_video= ? AND album_id=? ORDER BY ord";
    return DBHelper.executeQuery(sqlCommand, new Object[] { type, albumId });
  }
}

