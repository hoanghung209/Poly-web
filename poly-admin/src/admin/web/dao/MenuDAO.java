package admin.web.dao;

import admin.web.common.HandleCache;
import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class MenuDAO
{
  @SuppressWarnings("unchecked")
public static List<Map<String, String>> getAll()
  {
    String key = "MenuDAO.getAll";
    Object obj = HandleCache.get(key);
    if (obj != null) {
    	return (List<Map<String, String>>) obj;
    }
    String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent,ord";
    String[] params = new String[0];
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    HandleCache.put(key, lstRs);
    return lstRs;
  }
  
  public static String getFieldById(String id, String field)
  {
    List<Map<String, String>> lstMenu = getAll();
    for (Map<String, String> map : lstMenu) {
      if (((String)map.get("id")).equals(id)) {
        return (String)map.get(field);
      }
    }
    return "";
  }
  
  public static Map<String, String> getById(String id)
  {
    List<Map<String, String>> lstMenu = getAll();
    for (Map<String, String> map : lstMenu) {
      if (((String)map.get("id")).equals(id)) {
        return map;
      }
    }
    return null;
  }
  
  public static String getMenuTree(String id, String language)
  {
    Map<String, String> menuCur = getById(id);
    String name = "";
    if (menuCur != null)
    {
      name = (String)menuCur.get("name" + language);
      Map<String, String> menuParent = getById((String)menuCur.get("parent"));
      name = (String)menuParent.get(new StringBuilder("name").append(language).toString()) + " -> " + name;
    }
    return name;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM menu {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM menu {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    HandleCache.removeContain("MenuDAO.");
    String sqlCommand = "INSERT INTO menu(name_vn,slug_vn,name_en,slug_en,ord,parent,position,url,anchor,inpage,banner,status,created_by,created_time,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW())";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("slug_vn"), (String)detail.get("name_en"), (String)detail.get("slug_en"), (String)detail.get("ord"), (String)detail.get("parent"), (String)detail.get("position"), (String)detail.get("url"), (String)detail.get("anchor"), (String)detail.get("inpage"), (String)detail.get("banner"), (String)detail.get("status"), (String)detail.get("created_by"), (String)detail.get("updated_by") };
    boolean rs = DbProxy.executeUpdate(sqlCommand, param);
    
    return rs;
  }
  
  public static boolean update(Map<String, String> detail)
  {
    HandleCache.removeContain("MenuDAO.");
    String sqlCommand = "UPDATE menu SET name_vn=?,slug_vn=?,name_en=?,slug_en=?,ord=?,parent=?,position=?,url=?,anchor=?,inpage=?,banner=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("slug_vn"), (String)detail.get("name_en"), (String)detail.get("slug_en"), (String)detail.get("ord"), (String)detail.get("parent"), (String)detail.get("position"), (String)detail.get("url"), (String)detail.get("anchor"), (String)detail.get("inpage"), (String)detail.get("banner"), (String)detail.get("status"), (String)detail.get("updated_by"), (String)detail.get("id") };
    boolean rs = DbProxy.executeUpdate(sqlCommand, param);
    
    return rs;
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    HandleCache.removeContain("MenuDAO.");
    String sqlCommand = "UPDATE menu SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    boolean rs = DbProxy.executeUpdate(sqlCommand, param);
    
    return rs;
  }
}
