package com.seiu.web.dao;

import java.util.List;
import java.util.Map;
import com.seiu.web.common.DBHelper;

public class NewsDAO
{
  public static List<Map<String, String>> getList(String albumId, int page, int pagesize)
  {
    int offset = (page - 1) * pagesize;
    String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND album_id=? ORDER BY updated_time LIMIT " + offset + "," + pagesize;
    return DBHelper.executeQuery(sqlCommand, new Object[] { albumId });
  }
  
  public static int countList(String albumId)
  {
    String sqlCommand = "SELECT COUNT(id) FROM news WHERE is_deleted = 0 AND is_show = 0 AND album_id=?";
    return DBHelper.getInt(sqlCommand, new Object[] { albumId });
  }
  
  public static List<Map<String, String>> getListRelated(String id, String limit)
  {
    String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND id <> ? ORDER BY updated_time LIMIT " + limit;
    return DBHelper.executeQuery(sqlCommand, new Object[] { id });
  }
  
  public static Map<String, String> getById(String id)
  {
    String sqlCommand = "SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND id = ?";
    List<Map<String, String>> lstNews = DBHelper.executeQuery(sqlCommand, new Object[] { id });
    if (lstNews.size() > 0) {
      return lstNews.get(0);
    }
    return null;
  }
  
  public static List<Map<String, String>> search(String value, String lang)
  {
    String sqlCommand = String.format("SELECT * FROM news WHERE is_deleted = 0 AND is_show = 0 AND content_%s LIKE '%s%s%s'", new Object[] { lang, "%", value, "%" });
    return DBHelper.executeQuery(sqlCommand, new Object[0]);
  }
}
