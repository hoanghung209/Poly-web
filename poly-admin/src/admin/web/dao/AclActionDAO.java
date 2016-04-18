package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class AclActionDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM `acl_action`";
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    String sqlCommand = "INSERT INTO acl_action(`name`, `description`, `status`, `created_by`, `created_time`, `last_updated_by`, `last_updated_time`) VALUES(?, ?, ?, ?, NOW(), ?, NOW())";
    String[] sqlParameters = { (String)detail.get("name"), (String)detail.get("description"), (String)detail.get("status"), (String)detail.get("created_by"), (String)detail.get("last_updated_by") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE acl_action SET `description` = ?, `status` = ?, `created_by` = ?, `last_updated_by` = ?, `last_updated_time` = NOW() WHERE `name` = ?";
    String[] sqlParameters = { (String)detail.get("description"), (String)detail.get("status"), (String)detail.get("created_by"), (String)detail.get("last_updated_by"), (String)detail.get("name") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean delete(String name)
  {
    String sqlCommand = "DELETE FROM acl_action WHERE `name` = ?";
    String[] sqlParameters = { name };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static Map<String, String> getById(String name)
  {
    String sqlCommand = "SELECT * FROM acl_action WHERE `name` = ?";
    String[] sqlParameters = { name };
    List<Map<String, String>> listMap = DbProxy.executeQuery(sqlCommand, sqlParameters);
    if (listMap.size() > 0) {
      return listMap.get(0);
    }
    return null;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM acl_action {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(name) FROM acl_action {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
}
