package com.seiu.web.dao;

import com.seiu.web.common.DBHelper;

public class TemplateDAO
{
  public static String getTemplate(String key, String lang)
  {
    String sql = String.format("SELECT `content_%s` FROM `template` WHERE `key` = ?", new Object[] { lang });
    return DBHelper.getString(sql, new Object[] { key });
  }
}