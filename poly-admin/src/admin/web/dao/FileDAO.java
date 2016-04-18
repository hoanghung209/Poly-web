package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class FileDAO
{
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM file {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM file {WHERE}";
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
    String sqlCommand = "INSERT INTO file(name_vn,name_en,album_id,status,is_video,is_show,url,embed,title,alt,ord,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("name_en"), (String)detail.get("album_id"), (String)detail.get("status"), (String)detail.get("is_video"), (String)detail.get("is_show"), (String)detail.get("url"), (String)detail.get("embed"), (String)detail.get("title"), (String)detail.get("alt"), (String)detail.get("ord"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE file SET name_vn=?,name_en=?,album_id=?,status=?,is_video=?,is_show=?,url=?,embed=?,title=?,alt=?,ord=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("name_vn"), (String)detail.get("name_en"), (String)detail.get("album_id"), (String)detail.get("status"), (String)detail.get("is_video"), (String)detail.get("is_show"), (String)detail.get("url"), (String)detail.get("embed"), (String)detail.get("title"), (String)detail.get("alt"), (String)detail.get("ord"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE file SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
