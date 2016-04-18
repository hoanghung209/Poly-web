package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class NewsDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = String.format("SELECT * FROM news WHERE is_deleted = 0 ORDER BY updated_time DESC", new Object[0]);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM news {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM news {WHERE}";
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
    String sqlCommand = "INSERT INTO news(album_id,title_vn,title_en,content_vn,content_en,avatar,slug_vn,slug_en,is_show,is_deleted,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,0,?,NOW())";
    String[] param = { (String)detail.get("album_id"), (String)detail.get("title_vn"), (String)detail.get("title_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("avatar"), (String)detail.get("slug_vn"), (String)detail.get("slug_en"), (String)detail.get("is_show"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE news SET album_id=?,title_vn=?,title_en=?,content_vn=?,content_en=?,is_deleted=?,avatar=?,slug_vn=?,slug_en=?,is_show=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("album_id"), (String)detail.get("title_vn"), (String)detail.get("title_en"), (String)detail.get("content_vn"), (String)detail.get("content_en"), (String)detail.get("is_deleted"), (String)detail.get("avatar"), (String)detail.get("slug_vn"), (String)detail.get("slug_en"), (String)detail.get("is_show"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE news SET is_deleted = 1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
