package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class ContentDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent,ord";
    String[] params = new String[0];
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    
    return lstRs;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM content {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM content {WHERE}";
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
    String sqlCommand = "INSERT INTO content(title_vn,title_en,content_vn,content_en,description_vn,description_en,image,img_pos,width,ord,type,menu_id,anchor,status,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,NOW())";
    String[] param = { (String)detail.get("title_vn"), (String)detail.get("title_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("description_vn"), (String)detail.get("description_en"), (String)detail.get("image"), (String)detail.get("img_pos"), (String)detail.get("width"), (String)detail.get("ord"), (String)detail.get("type"), (String)detail.get("menu_id"), (String)detail.get("anchor"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE content SET title_vn=?,title_en=?,content_vn=?,content_en=?,description_vn=?,description_en=?,image=?,img_pos=?,width=?,ord=?,type=?,menu_id=?,anchor=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("title_vn"), (String)detail.get("title_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("description_vn"), (String)detail.get("description_en"), (String)detail.get("image"), (String)detail.get("img_pos"), (String)detail.get("width"), (String)detail.get("ord"), (String)detail.get("type"), (String)detail.get("menu_id"), (String)detail.get("anchor"), (String)detail.get("status"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE content SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
