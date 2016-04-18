package com.seiu.web.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ContextUtils
{
  private static Hashids hash = new Hashids();
  
  public static String getCurrentPath(HttpServletRequest request)
  {
    String query = request.getQueryString();
    if ((query != null) && (query != "")) {
      query = "?" + query;
    } else {
      query = "";
    }
    return request.getRequestURL().toString() + query;
  }
  
  public static boolean isBlank(String str)
  {
    if ((str != null) && 
      (!str.trim().equals(""))) {
      return false;
    }
    return true;
  } 
  
  
  public static void systemOutPrint(Map<String, String> map)
  {
    int dodai = 0;
    if (map != null)
    {
      for (String key : map.keySet()) {
        if (dodai < key.length()) {
          dodai = key.length();
        }
      }
      for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println(String.format("%" + dodai + "s:%s", new Object[] { entry.getKey(), entry.getValue() }));
      }
    }
  }
  
  public static String removeHtml(String content, int posCut)
  {
    for (;;)
    {
      int bg = content.indexOf("<");
      if (bg < 0) {
        return content;
      }
      if (bg > posCut) {
        return content;
      }
      String temp = content.substring(bg);
      int en = temp.indexOf(">");
      content = content.substring(0, bg) + content.substring(bg + 1 + en);
    }
  }
  
  public static String getMonth(int value)
  {
    switch (value)
    {
    case 1: 
      return "JAN";
    case 2: 
      return "FEB";
    case 3: 
      return "MAR";
    case 4: 
      return "APR";
    case 5: 
      return "MAY";
    case 6: 
      return "JUN";
    case 7: 
      return "JUL";
    case 8: 
      return "AUG";
    case 9: 
      return "SEP";
    case 10: 
      return "OCT";
    case 11: 
      return "NOV";
    case 12: 
      return "DEC";
    }
    return "NULL";
  }
  
  public static String convertToSlug(String input)
  {
    String str = StringUtils.stripAccents(input);
    str = str.toLowerCase();
    str = str.replaceAll("[^a-zA-Z0-9\\s]", "");
    str = str.replaceAll("-", "");
    str = str.replaceAll("\\\\", "-");
    str = str.replaceAll("\\s+", "-");
    str = str.replaceAll("/", "-");
    str = str.replaceAll("Đ", "D");
    str = str.replaceAll("đ", "d");
    return str;
  }
  
  public static String syncSlug(String slug)
  {
    if (isBlank(slug)) {
      return "blank-slug";
    }
    return slug;
  }
  
  public static String deHash(String id)
  {
    return hash.decodeHex(id);
  }
  
  public static String enHash(String id)
  {
    return hash.encodeHex(id);
  }
}
