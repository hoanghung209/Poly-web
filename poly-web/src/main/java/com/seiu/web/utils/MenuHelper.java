package com.seiu.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.seiu.web.common.Config;
import com.seiu.web.dao.MenuDAO;

public class MenuHelper
{
 private static String lang = "vn";
 private static List<Map<String, String>> lstMenu;
 private static String contextPath = Config.contextPath;
 private static Hashids hash = new Hashids();
 
 public static List<Map<String, String>> getAll()
 {
   if (lstMenu == null)
   {
     lstMenu = MenuDAO.getAll();
     for (Map<String, String> map : lstMenu)
     {
       map.put("name", (String)map.get("name_" + lang));
       map.put("slug", ContextUtils.syncSlug((String)map.get("slug_" + lang)));
       String inpage = (String)map.get("id");
       if (!ContextUtils.isBlank((String)map.get("inpage"))) {
         try
         {
           if (Integer.valueOf((String)map.get("inpage")).intValue() > 0) {
             inpage = (String)map.get("inpage");
           }
         }
         catch (Exception localException) {}
       }
       map.put("hashid", hashid(inpage));
       if (ContextUtils.isBlank((String)map.get("url"))) {
         map.put("urlnew", "intro");
       } else {
         map.put("urlnew", (String)map.get("url"));
       }
       if (!ContextUtils.isBlank((String)map.get("anchor"))) {
         map.put("neo", "#" + (String)map.get("anchor"));
       } else {
         map.put("neo", "");
       }
     }
   }
   return lstMenu;
 }
 
 public static void clear()
 {
   lang = "vn";
   lstMenu = null;
 }
 
 public static List<Map<String, String>> getMenu(String parent, String position)
 {
   List<Map<String, String>> lstNewMenu = new ArrayList<Map<String,String>>();
   List<Map<String, String>> lstAllMenu = getAll();
   for (Map<String, String> map : lstAllMenu) {
     if ((((String)map.get("parent")).equals(parent)) && (((String)map.get("position")).equals(position))) {
       lstNewMenu.add(map);
     }
   }
   return lstNewMenu;
 }
 
 public static boolean existSub(String parent, String position)
 {
   List<Map<String, String>> lstAllMenu = getAll();
   for (Map<String, String> map : lstAllMenu) {
     if ((((String)map.get("parent")).equals(parent)) && (((String)map.get("position")).equals(position))) {
       return true;
     }
   }
   return false;
 }
 
 public static Map<String, String> getBySlug(String slug)
 {
   List<Map<String, String>> lstMenu = getAll();
   for (Map<String, String> menu : lstMenu) {
     if (slug.equals(menu.get("slug_" + getLang()))) {
       return menu;
     }
   }
   return null;
 }
 
 public static List<Map<String, String>> getTopMenu(String actId)
 {
   List<Map<String, String>> lstMap = new ArrayList<Map<String,String>>();
   Map<String, String> act = getById(actId);
   while (!((String)act.get("parent")).equals("-1"))
   {
     Map<String, String> parent = getById((String)act.get("parent"));
     if (parent == null) {
       break;
     }
     lstMap.add(parent);
     act = parent;
   }
   return lstMap;
 }
 
 public static Map<String, String> getById(String menuId)
 {
   List<Map<String, String>> lstMap = getAll();
   for (Map<String, String> map : lstMap) {
     if (((String)map.get("id")).equals(menuId)) {
       return map;
     }
   }
   return null;
 }
 
 public static String leftMenu(ModelMap model, String menuId, String activeId)
 {
   Map<String, String> menuChinh = getById(menuId);
   Map<String, String> menuLop = getById(activeId);
   if ((menuLop == null) || (menuChinh == null)) {
     return "404";
   }
   Map<String, String> menuCap = getById((String)menuLop.get("parent"));
   Map<String, String> menuChuongtrinh = getById((String)menuCap.get("parent"));
   
   model.addAttribute("menuChinh", menuChinh);
   model.addAttribute("menuChuongtrinh", menuChuongtrinh);
   model.addAttribute("menuCap", menuCap);
   model.addAttribute("menuLop", menuLop);
   
   return "";
 }
 
 public static String getRootMenu(String id)
 {
   int max = 0;
   return getParentMenu(id, max);
 }
 
 public static String getParentMenu(String id, int max)
 {
   Map<String, String> mapMenu = getById(id);
   if (max > 9) {
     return "0";
   }
   if (mapMenu != null)
   {
     if (((String)mapMenu.get("parent")).equals("-1")) {
       return (String)mapMenu.get("id");
     }
     return getParentMenu((String)mapMenu.get("parent"), ++max);
   }
   return "0";
 }
 
 public static void refresh()
 {
   lstMenu = null;
 }
 
 public static String getLang()
 {
   return lang;
 }
 
 public static void setLang(String language)
 {
   lang = language;
 }
 
 public static String getContextPath()
 {
   if (!Boolean.valueOf(Config.usePathStatic).booleanValue()) {
     try
     {
       ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
       String url = requestAttributes.getRequest().getRequestURL().toString();
       String projectName = requestAttributes.getRequest().getContextPath();
       int inx = url.indexOf(projectName);
       if (inx < 0) {
         inx = 0;
       }
       contextPath = url.substring(0, inx) + projectName;
     }
     catch (Exception ex)
     {
       contextPath = "localhost:8081/poly-web";
     }
   }
   return contextPath;
 }
 
 public static String hashid(String id)
 {
   return hash.encodeHex(id);
 }
}
