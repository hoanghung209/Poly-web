package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class AclUserDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM `acl_user` WHERE deleted = 0";
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListByGroup(String groupId)
  {
    String sqlCommand = "SELECT * FROM `acl_user` WHERE deleted = 0 AND status = 1 AND id IN (SELECT DISTINCT user_id FROM acl_user_group WHERE group_id = ?)";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getListFreeByGroup(String groupId)
  {
    String sqlCommand = "SELECT * FROM `acl_user` WHERE deleted = 0 AND status = 1 AND id NOT IN (SELECT DISTINCT user_id FROM acl_user_group WHERE group_id = ?)";
    
    String[] sqlParameters = { groupId };
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static String insertIdentity(Map<String, String> detail)
  {
    if (insert(detail))
    {
      String sqlCommand = "SELECT MAX(id) AS id FROM acl_user";
      String[] sqlParameters = new String[0];
      return DbProxy.executeScalar(sqlCommand, sqlParameters);
    }
    return "0";
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    String sqlCommand = "INSERT INTO acl_user(`username`, `password`, `full_name`, `sex`, `birth_day`, `email`, `mobile`, `status`, `deleted`, `created_by`, `created_time`, `last_updated_by`, `last_updated_time`) VALUES(?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, NOW(), ?, NOW())";
    String[] sqlParameters = { (String)detail.get("username"), StringUtils.md5((String)detail.get("password")), (String)detail.get("full_name"), (String)detail.get("sex"), (String)detail.get("email"), (String)detail.get("mobile"), (String)detail.get("status"), (String)detail.get("deleted"), (String)detail.get("created_by"), (String)detail.get("last_updated_by") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE acl_user SET `username` = ?, `full_name` = ?, `sex` = ?, `email` = ?, `mobile` = ?, `status` = ?, `deleted` = ?, `created_by` = ?, `last_updated_by` = ?, `last_updated_time` = NOW() WHERE `id` = ?";
    String[] sqlParameters = { (String)detail.get("username"), (String)detail.get("full_name"), (String)detail.get("sex"), (String)detail.get("email"), (String)detail.get("mobile"), (String)detail.get("status"), (String)detail.get("deleted"), (String)detail.get("created_by"), (String)detail.get("last_updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean updatePassword(String id, String password, String modifiedBy)
  {
    String sqlCommand = "UPDATE acl_user SET `password` = ?, `last_updated_by` = ?, `last_updated_time` = NOW() WHERE `id` = ?";
    String[] sqlParameters = { StringUtils.md5(password), modifiedBy, id };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean delete(String id)
  {
    String sqlCommand = "DELETE FROM acl_user WHERE `id` = ?";
    String[] sqlParameters = { id };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static boolean deleteLogic(String id, String modifiedBy)
  {
    String sqlCommand = "UPDATE `acl_user` SET deleted = 1, last_updated_by = ?, `last_updated_time` = NOW() WHERE `id` = ?";
    String[] sqlParameters = { modifiedBy, id };
    return DbProxy.executeUpdate(sqlCommand, sqlParameters);
  }
  
  public static Map<String, String> getById(String id)
  {
    String sqlCommand = "SELECT * FROM acl_user WHERE `id` = ?";
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
    
    String sqlCommand = String.format("SELECT * FROM acl_user {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM acl_user {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
}
