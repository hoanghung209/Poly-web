package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class AclGroupDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE `deleted` = 0";
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListAfter(String groupId)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE `deleted` = 0 AND id IN (SELECT DISTINCT `receiver` FROM `news_workflow` WHERE `sender` = ?)";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListBefore(String groupId)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE `deleted` = 0 AND id IN (SELECT DISTINCT `sender` FROM `news_workflow` WHERE `receiver` = ?)";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListReceived(String userId)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE `deleted` = 0 AND id IN (SELECT `receiver` FROM `news_workflow` WHERE `sender` IN (SELECT `group_id` FROM `acl_user_group` WHERE `user_id` = ?))";
    
    String[] sqlParameters = { userId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListByUser(String userId)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE deleted = 0 AND id IN (SELECT DISTINCT group_id FROM acl_user_group WHERE user_id = ?)";
    
    String[] sqlParameters = { userId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListFreeByUser(String userId)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE deleted = 0 AND id NOT IN (SELECT DISTINCT group_id FROM acl_user_group WHERE user_id = ?)";
    
    String[] sqlParameters = { userId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static String insertIdentity(Map<String, String> detail)
  {
    if (insert(detail))
    {
      String sqlCommand = "SELECT MAX(`id`) AS id FROM `acl_group`";
      String[] sqlParameters = new String[0];
      return DbProxy.executeScalar(sqlCommand, sqlParameters);
    }
    return "0";
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    String sqlCommand = "INSERT INTO `acl_group`(`name`, `description`, `status`, `deleted`, `created_by`, `created_time`, `last_updated_by`, `last_updated_time`) VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW())";
    String[] sqlParameters = { (String)detail.get("name"), (String)detail.get("description"), (String)detail.get("status"), (String)detail.get("deleted"), (String)detail.get("created_by"), (String)detail.get("last_updated_by") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE `acl_group` SET `name` = ?, `description` = ?, `status` = ?, `deleted` = ?, `created_by` = ?, `last_updated_by` = ?, `last_updated_time` = NOW() WHERE `id` = ?";
    String[] sqlParameters = { (String)detail.get("name"), (String)detail.get("description"), (String)detail.get("status"), (String)detail.get("deleted"), (String)detail.get("created_by"), (String)detail.get("last_updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean delete(String id)
  {
    String sqlCommand = "DELETE FROM `acl_group` WHERE `id` = ?";
    String[] sqlParameters = { id };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean deleteLogic(String id, String modifiedBy)
  {
    String sqlCommand = "UPDATE `acl_group` SET deleted = 1, last_updated_by = ?, `last_updated_time` = NOW() WHERE `id` = ?";
    String[] sqlParameters = { modifiedBy, id };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static Map<String, String> getById(String id)
  {
    String sqlCommand = "SELECT * FROM `acl_group` WHERE `id` = ?";
    String[] sqlParameters = { id };
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
    
    String sqlCommand = String.format("SELECT * FROM `acl_group` {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(`id`) FROM `acl_group` {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
}
