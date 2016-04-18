package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class FaqDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = "SELECT * FROM faq WHERE status = 0 ORDER BY parent,ord";
    String[] params = new String[0];
    List<Map<String, String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
    return lstRs;
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM faq {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM faq {WHERE}";
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
    String sqlCommand = "INSERT INTO faq(title,content,ord,status,language) VALUES(?,?,?,?,?)";
    String[] param = { (String)detail.get("title"), (String)detail.get("content"), (String)detail.get("ord"), (String)detail.get("status"), (String)detail.get("language") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE faq SET title=?,content=?,ord=?,status=?,language=? WHERE id=?";
    String[] param = { (String)detail.get("title"), (String)detail.get("content"), (String)detail.get("ord"), (String)detail.get("status"), (String)detail.get("language"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id)
  {
    String sqlCommand = "UPDATE faq SET status = -1 WHERE id=?";
    String[] param = { id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static int getOrder(String lang)
  {
    String sqlCommand = "SELECT MAX(ord) FROM faq WHERE language = ?";
    String[] param = { lang };
    String ord = DbProxy.executeScalar(sqlCommand, param);
    if (!StringUtils.isBlank(ord)) {
      return Integer.valueOf(ord).intValue();
    }
    return 0;
  }
}
