package com.seiu.web.dao;

import java.util.List;
import java.util.Map;
import com.seiu.web.common.DBHelper;

public class FeedBackDAO
{
  public static List<Map<String, String>> getList()
  {
    String sqlCommand = "SELECT * FROM feedback WHERE status = 0 AND is_show = 0 ORDER BY updated_time DESC";
    return DBHelper.executeQuery(sqlCommand, new Object[0]);
  }
  
  public static int insert(String name, String phone, String mes)
  {
    String sqlComand = "INSERT INTO feedback(name,phone,content,created_time) VALUES(?,?,?,NOW())";
    return DBHelper.insertOrUpdate(sqlComand, new Object[] { name, phone, mes });
  }
}
