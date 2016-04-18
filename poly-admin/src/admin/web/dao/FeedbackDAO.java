package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class FeedbackDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = String.format("SELECT * FROM feedback WHERE status <> -1 ORDER BY created_time DESC", new Object[0]);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM feedback {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListAll(String sqlWhere, String sqlOrder)
  {
    String sqlCommand = String.format("SELECT * FROM feedback {WHERE} {ORDER} ", new Object[0]);
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM feedback {WHERE}";
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
    String sqlCommand = "INSERT INTO feedback(name,chuc,phone,content,status,avatar,is_show,created_time,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,NOW(),?,NOW())";
    String[] param = { (String)detail.get("name"), (String)detail.get("chuc"), (String)detail.get("phone"), (String)detail.get("content"), (String)detail.get("status"), (String)detail.get("avatar"), (String)detail.get("is_show"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE feedback SET name=?,chuc=?,phone=?,content=?,status=?,avatar=?,is_show=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("name"), (String)detail.get("chuc"), (String)detail.get("phone"), (String)detail.get("content"), (String)detail.get("status"), (String)detail.get("avatar"), (String)detail.get("is_show"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE feedback SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
