package com.seiu.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class ContentDAO
{
  public static List<Map<String, String>> getContentByMenu(String menuId, String type)
  {
    String sqlType = "AND type";
    if (!type.isEmpty())
    {
      if (type.contains(",")) {
        sqlType = sqlType + " IN(" + type + ")";
      } else {
        sqlType = sqlType + " = " + type;
      }
    }
    else {
      sqlType = "";
    }
    String sqlCommand = "SELECT * FROM content WHERE `menu_id` = ? AND `status` = 0 " + sqlType + " ORDER BY `ord` ASC";
    return DBHelper.executeQuery(sqlCommand, new Object[] { menuId });
  }
  
  public static Map<String, String> countType(String menuId)
  {
    String sqlCommand = "SELECT `type`,count(id) sl FROM content WHERE `menu_id` = ? AND `status` = 0 GROUP BY `type`";
    List<Map<String, String>> lstRs = DBHelper.executeQuery(sqlCommand, new Object[] { menuId });
    if (lstRs.size() > 0)
    {
      Map<String, String> mapType = new HashMap<String, String>();
      for (Map<String, String> map : lstRs) {
        if (map.get("type") != null) {
          mapType.put("type" + (String)map.get("type"), (String)map.get("sl"));
        }
      }
      return mapType;
    }
    return null;
  }
  
  public static List<Map<String, String>> search(String str, String lang)
  {
    String sqlCommand = String.format("SELECT * FROM content WHERE `status` = 0 AND content_%s LIKE '%s%s%s' ORDER BY `updated_time` DESC", new Object[] {
      lang, "%", str, "%" });
    return DBHelper.executeQuery(sqlCommand, new Object[0]);
  }
}
