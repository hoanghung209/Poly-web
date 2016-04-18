package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class UserDAO
{
  public static Map<String, String> signin(String user, String pass)
  {
    String sqlCommand = "SELECT * FROM user WHERE user=? AND password= md5(?) AND status = 0 AND role = 1";
    String[] params = { user, pass };
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    if (lstRs.size() > 0) {
      return lstRs.get(0);
    }
    return null;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM user {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = String.format("SELECT * FROM user", new Object[0]);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM user {WHERE}";
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
    String sqlCommand = "INSERT INTO user(user,password,fullname,role,status) VALUES(?,md5(?),?,?,?)";
    String[] param = { (String)detail.get("user"), (String)detail.get("password"), (String)detail.get("fullname"), (String)detail.get("role"), (String)detail.get("status") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE useruser SET user=?,password=md5(?),fullname=?,role=?,status=? WHERE id=?";
    String[] param = { (String)detail.get("user"), (String)detail.get("password"), (String)detail.get("fullname"), (String)detail.get("role"), (String)detail.get("status"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE user SET status = -1 WHERE id=?";
    String[] param = { id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean changePass(String id, String pass)
  {
    String sqlCommand = "UPDATE user SET password = md5(?) WHERE id=?";
    String[] param = { id, pass };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean check(String user)
  {
    String sqlCommand = "SELECT * FROM user WHERE user=?";
    String[] params = new String[0];
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    if (lstRs.size() > 0) {
      return true;
    }
    return false;
  }
}
