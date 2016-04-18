package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class MenuDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent ASC,ord ASC";
    return DBHelper.executeQuery(sqlCommand, new Object[0]);
  }
  
  public static List<Map<String, String>> getMenu(String parent, String position)
  {
    String sqlCommand = "SELECT * FROM menu WHERE parent = ? AND position = ? AND status = 0 ORDER BY ord ASC";
    return DBHelper.executeQuery(sqlCommand, new Object[] { parent, position });
  }
  
  public static boolean existSub(String parent, String position)
  {
    String sqlCommand = "SELECT count(id) FROM menu WHERE parent = ? AND position = ? AND status = 0";
    int rs = DBHelper.getInt(sqlCommand, new Object[] { parent, position });
    return rs > 0;
  }
}
