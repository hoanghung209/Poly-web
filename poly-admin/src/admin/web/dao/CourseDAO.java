package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class CourseDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM course WHERE status = 0 ORDER BY menu_id,ord";
    String[] params = new String[0];
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    
    return lstRs;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM course {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM course {WHERE}";
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
    String sqlCommand = "INSERT INTO course(name_vn,name_en,content_vn,content_en,ord,menu_id,status,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,NOW())";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("name_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("ord"), (String)detail.get("menu_id"), (String)detail.get("status"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE course SET name_vn=?,name_en=?,content_vn=?,content_en=?,ord=?,menu_id=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("name_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("ord"), (String)detail.get("menu_id"), (String)detail.get("status"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE course SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
