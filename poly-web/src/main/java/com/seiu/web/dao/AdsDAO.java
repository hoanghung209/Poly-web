package com.seiu.web.dao;

import java.util.List;
import java.util.Map;

import com.seiu.web.common.DBHelper;

public class AdsDAO
{
  public static List<Map<String, String>> getList(String is_banner)
  {
    String sqlCommand = "SELECT * FROM ads_banner WHERE is_deleted = 0 AND is_show = 0 AND type= ? AND date_start<=NOW() AND date(NOW())<=date(date_end) ORDER BY updated_time DESC LIMIT 5";
    return DBHelper.executeQuery(sqlCommand, new Object[] { is_banner });
  }
}