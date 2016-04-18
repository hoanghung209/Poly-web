package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.dbproxy.DbProxy;

public class AclUserGroupDAO
{
  public static List<Map<String, String>> getUserByGroup(String groupId)
  {
    String sqlCommand = "SELECT `acl_user`.* FROM `acl_user` INNER JOIN acl_user_group ON `acl_user`.id = acl_user_group.user_id WHERE `acl_user`.deleted = 0 AND acl_user_group.group_id = ?";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getFreeUserByGroup(String groupId)
  {
    String sqlCommand = "SELECT `acl_user`.* FROM `acl_user` INNER JOIN acl_user_group ON `acl_user`.id = acl_user_group.user_id WHERE `acl_user`.deleted = 0 AND acl_user_group.group_id <> ?";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getGroupByUser(String userId)
  {
    String sqlCommand = "SELECT `acl_group`.* FROM `group` INNER JOIN acl_user_group ON `acl_group`.id = acl_user_group.group_id WHERE `acl_group`.deleted = 0 AND acl_user_group.user_id = ?";
    
    String[] sqlParameters = { userId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getFreeGroupByUser(String userId)
  {
    String sqlCommand = "SELECT `acl_group`.* FROM `group` INNER JOIN acl_user_group ON `acl_group`.id = acl_user_group.group_id WHERE `acl_group`.deleted = 0 AND acl_user_group.user_id <> ?";
    
    String[] sqlParameters = { userId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static boolean insert(String userId, String groupId, String createdBy)
  {
    String sqlCommand = "INSERT INTO acl_user_group(`user_id`, `group_id`, `created_by`, `created_time`) VALUES(?, ?, ?, NOW())";
    String[] sqlParameters = { userId, groupId, createdBy };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean delete(String userId, String groupId)
  {
    String sqlCommand = "DELETE FROM acl_user_group WHERE user_id = ? AND group_id = ?";
    String[] sqlParameters = { userId, groupId };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
}
