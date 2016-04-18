package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class RegisterDAO
{
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM register {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListAll(String sqlWhere, String sqlOrder)
  {
    String sqlCommand = String.format("SELECT * FROM register {WHERE} {ORDER}", new Object[0]);
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM register {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE register SET fullname=?,phone=?,email=?,address=?,status=?,is_deleted=?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { (String)detail.get("fullname"), (String)detail.get("phone"), (String)detail.get("email"), (String)detail.get("address"), (String)detail.get("status"), (String)detail.get("is_deleted"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE register SET is_deleted = 1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean action(String id, String action, String userId)
  {
    String sqlCommand = "UPDATE register SET status = ?,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { action, userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
