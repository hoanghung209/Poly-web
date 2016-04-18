package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class AclActionObjectDAO
{
  public static List<Map<String, String>> getListAllow(String allow, String objectType, String objectId)
  {
    String sqlCommand = "SELECT action_name as name FROM acl_action_object WHERE allow = ? AND object_type = ? AND object_id = ?";
    
    String[] sqlParameters = { allow, objectType, objectId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    String sqlCommand = "INSERT INTO acl_action_object(`action_name`, `object_id`, `object_type`, `allow`, `created_by`, `created_time`, `last_updated_by`, `last_updated_time`) VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW())";
    String[] sqlParameters = { (String)detail.get("action_name"), (String)detail.get("object_id"), (String)detail.get("object_type"), (String)detail.get("allow"), (String)detail.get("created_by"), (String)detail.get("last_updated_by") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE acl_action_object SET `allow` = ?, `created_by` = ?, `last_updated_by` = ?, `last_updated_time` = NOW() WHERE `action_name` = ? AND `object_id` = ? AND `object_type` = ?";
    String[] sqlParameters = { (String)detail.get("allow"), (String)detail.get("created_by"), (String)detail.get("last_updated_by"), (String)detail.get("action_name"), (String)detail.get("object_id"), (String)detail.get("object_type") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean delete(String actionName, String objectId, String objectType)
  {
    String sqlCommand = "DELETE FROM acl_action_object WHERE `action_name` = ? AND `object_id` = ? AND `object_type` = ?";
    String[] sqlParameters = { actionName, objectId, objectType };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static Map<String, String> getById(String actionName, String objectId, String objectType)
  {
    String sqlCommand = "SELECT * FROM acl_action_object WHERE `action_name` = ? AND `object_id` = ? AND `object_type` = ?";
    String[] sqlParameters = { actionName, objectId, objectType };
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
    
    String sqlCommand = String.format("SELECT * FROM acl_action_object {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere, String sqlOrder)
  {
    String sqlCommand = "SELECT COUNT(action_name) FROM acl_action_object {WHERE} {ORDER}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
}
